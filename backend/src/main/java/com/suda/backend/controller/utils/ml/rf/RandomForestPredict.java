// -*- coding: utf-8 -*-
package com.suda.backend.controller.utils.ml.rf;  // 注意修改包名为合适的名称

import com.suda.backend.consumer.WebSocketServer;
import com.suda.backend.service.data.AddDataService;
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
    private AddDataService addDataService;
    private double add;
    private String pdd;
    private String temp;
    private Instance inst;
    private int packageCnt;  // 添加一个标志变量
    private Timer timer = new Timer();
    private int[] record = new int[6];
    // normal\dos\probing\R2l\U2R
    private volatile boolean predictRunning;  // 添加一个标志变量
    private Vector<String> predictdata;

    public Vector<String> getPredictdata() {
        return predictdata;
    }

    public void stopPredict() {
        this.predictRunning = false;
        timer.cancel();
    }

    public void getCnt() {
        System.out.println("广播" + record[5]);
        WebSocketServer.broadcastAttack(record);
        System.out.println("数据库" + record[5]);
        addDataService.add(record);
        Arrays.fill(record, 0);
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
