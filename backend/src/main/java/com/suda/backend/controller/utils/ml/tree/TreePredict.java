// -*- coding: utf-8 -*-
package com.suda.backend.controller.utils.ml.tree;

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
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Service
public class TreePredict {

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
	public void predict() throws Exception{

		predictRunning = true;
		predictdata=new Vector<String>();
		
		Instances train = DataSource.read("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Train.arff");
		train.setClassIndex(train.numAttributes() - 1);
		
		Instances race = new Instances(train, 0);
		LoaderTree tree=new LoaderTree();
		
		
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
			
			for(int i=0; i<tmp.length; i++){
				//System.out.println(i);
				try {
					add = Double.parseDouble(tmp[i]);
					inst.setValue(race.attribute(i), add);
				} catch (Exception e) {
					inst.setValue(race.attribute(i), tmp[i]);
				}
			}
			inst.setDataset(race);
			try {
				double res = tree.loadTree().classifyInstance(inst);
				record[5]++;
				switch((int)res){
				
				case 0:
					setPdd("back.");
					record[1]++;
					break;
				case 1:
					setPdd("teardrop.");
					record[1]++;
					break;
				case 2:
					setPdd("loadmodule.");
					record[4]++;
					break;
				case 3:
					setPdd("neptune.");
					record[1]++;
					break;
				case 4:
					setPdd("rootkit.");
					record[4]++;
					break;
				case 5:
					setPdd("phf.");
					record[3]++;
					break;
				case 6:
					setPdd("satan.");
					record[2]++;
					break;
				case 7:
					setPdd("buffer_overflow.");
					record[4]++;
					break;
				case 8:
					setPdd("ftp_write.");
					record[3]++;
					break;
				case 9:
					setPdd("land.");
					record[1]++;
					break;
				case 10:
					setPdd("spy.");
					record[3]++;
					break;
				case 11:
					setPdd("ipsweep.");
					record[2]++;
					break;
				case 12:
					setPdd("multihop.");
					record[3]++;
					break;
				case 13:
					setPdd("smurf.");
					record[1]++;
					break;
				case 14:
					setPdd("pod.");
					record[1]++;

					break;
				case 15:
					setPdd("perl.");
					record[4]++;
					break;
				case 16:
					setPdd("warezclient.");
					record[3]++;
					break;
				case 17:
					setPdd("nmap.");
					record[2]++;
					break;
				case 18:
					setPdd("imap.");
					record[3]++;
					break;
				case 19:
					setPdd("warezmaster.");
					record[3]++;
					break;
				case 20:
					setPdd("portsweep.");
					record[2]++;
					break;
				case 21:
					setPdd("normal.");
					record[0]++;
					break;
				case 22:
					setPdd("guess_passwd.");
					record[3]++;
					break;
				case 23:
					setPdd("mscan.");
					record[2]++;
					break;
				case 24:setPdd("apache2.");	record[4]++;break;
				case 25:setPdd("mailbomb.");record[1]++;break;
				case 26:setPdd("processtable.");record[1]++;break;
				case 27:setPdd("udpstorm.");record[1]++;break;
				case 28:setPdd("httptunnel.");record[1]++;break;
				case 29:setPdd("ps.");record[1]++;break;
				case 30:setPdd("sqlattack.");	record[4]++;break;
				case 31:setPdd("xterm.");record[1]++;break;
				case 32:setPdd("named.");record[3]++;break;
				case 33:setPdd("sendmail.");record[3]++;break;
				case 34:setPdd("snmpgetattack.");record[3]++;break;
				case 35:setPdd("snmpguess.");record[3]++;break;
				case 36:setPdd("worm.");record[3]++;break;
				case 37:setPdd("xlock.");record[3]++;break;
				case 38:setPdd("xsnoop.");record[3]++;break;
				case 39:
					setPdd("saint.");
					record[2]++;
					break;
				
				}
//				temp=getPdd();
//				predictdata.add(temp);				//System.out.println("预测值"+getPdd());
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


