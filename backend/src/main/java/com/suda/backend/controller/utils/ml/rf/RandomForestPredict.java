// -*- coding: utf-8 -*-
package com.suda.backend.controller.utils.ml.rf;  // 注意修改包名为合适的名称

import com.suda.backend.consumer.WebSocketServer;
import com.suda.backend.pojo.Defence;
import com.suda.backend.pojo.Node;
import com.suda.backend.service.data.AddDataService;
import com.suda.backend.service.defence.GetListService;
import com.suda.backend.service.email.SendMailsService;
import com.suda.backend.service.node.AddHoneyPotService;
import com.suda.backend.service.node.CloseNodeService;
import com.suda.backend.service.node.GetListNodeService;
import com.suda.backend.service.operate.AddOperateService;
import com.suda.backend.service.setting.GetListSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.trees.RandomForest;  // 导入RandomForest类
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Service
public class RandomForestPredict {  // 修改类名为合适的名称
    @Autowired
    private AddOperateService addOperateService;
    @Autowired
    private AddDataService addDataService;
    @Autowired
    private SendMailsService sendMailsService;
    @Autowired
    private GetListSettingService getListSettingService;
    @Autowired
    private CloseNodeService closenodeService;
    @Autowired
    private GetListNodeService getListNodeService;
    @Autowired
    private GetListService getListService;
    @Autowired
    private AddHoneyPotService addHoneyPotService;
    private double add;
    private String pdd;
    private  Defence[] defences ;
    private String temp;
    private Instance inst;
    private  int packageCnt;  // 添加一个标志变量
    private  Timer timer = new Timer();
    private int [] record = new int [6];
    //normal\dos\probing\R2l\U2R
    private int Threshold ;
    private int nodeThreshold;
    private int alert ;
    private volatile boolean predictRunning;  // 添加一个标志变量
    private int cnt = 0;
    private  boolean is_honeyPot;
    private boolean is_closeNode;
    private boolean is_sendMessage;
    private int nodelength;
    private Node[] nodes;
    private Vector<String> predictdata;
    private Random random = new Random();

    // 生成一个在1到指定数之间的随机数

    public Vector<String> getPredictdata() {
        return predictdata;
    }


    public void stopPredict(){
        this.predictRunning = false;
        timer.cancel();
    }
    public void getCnt() {
        System.out.println("广播" + record[5]);
        WebSocketServer.broadcastAttack(record);
        System.out.println("数据库" + record[5]);
        addDataService.add(record);
        Arrays.fill(record, 0);
        cnt++;
        if (cnt % Threshold == 0 && is_sendMessage) {
//            sendMailsService.sendMails();
            if (alert == 2) {
                WebSocketServer.broadcastPopUp();
                HashMap<String,String> map = new HashMap<>();
                map.put("type","弹窗");
                map.put("description","弹窗警告");
                addOperateService.add(map);


                WebSocketServer.broadcastPopUp();
                System.out.println("发送警报");
            } else {
                sendMailsService.sendMails();
                System.out.println("发送邮件");
            }
        }
        if (is_closeNode && cnt % nodeThreshold == 0) {
            HashMap<String, String> map = new HashMap<>();
            int randomNumber = random.nextInt(nodelength);
            while (nodes[randomNumber].getType() == 1) {
                randomNumber = random.nextInt(nodelength);
            }
            System.out.println("关闭节点" + randomNumber);
            map.put("id", String.valueOf(nodes[randomNumber].getId()));
            closenodeService.close(map);
            if (is_honeyPot) {
                addHoneyPotService.addPot();
            }
        }
    }
        public void predict() throws Exception {
        predictRunning = true;
        predictdata = new Vector<String>();

        Instances train = DataSource.read("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Train.arff");
        train.setClassIndex(train.numAttributes() - 1);

        Instances race = new Instances(train, 0);
        RandomForestLoader randomForestLoader = new RandomForestLoader();  // 使用RandomForestLoader加载RandomForest模型

        FileReader reader = new FileReader("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\20test.txt");
        BufferedReader br = new BufferedReader(reader);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getCnt();
                System.out.println("一个周期结束");
            }
        }, 0, 5000);  // 第一个参数是初始延迟，第二个参数是间隔时间（单位：毫秒）
        String string = null;
        while ((string = br.readLine()) != null && predictRunning) {

            String[] tmp = string.split(",");

            Instance inst = new DenseInstance(tmp.length);

            for (int i = 0; i < tmp.length; i++) {
                try {
                    add = Double.parseDouble(tmp[i]);
                    inst.setValue(race.attribute(i), add);
                } catch (Exception e) {
                    inst.setValue(race.attribute(i), tmp[i]);
                }
            }
            inst.setDataset(race);
            try {
                double res = randomForestLoader.loadRandomForestModel().classifyInstance(inst);  // 使用RandomForest模型进行预测
                record[5]++;
                // 在这里添加处理预测结果的代码，与朴素贝叶斯的部分类似

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("停止监测");
        br.close();
        reader.close();
    }

    public String getPdd() {
        return pdd;
    }

    public void setPdd(String pdd) {
        this.pdd = pdd;
    }

    public Instance getInst() {
        return inst;
    }

    public void setInst(Instance inst) {
        this.inst = inst;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
