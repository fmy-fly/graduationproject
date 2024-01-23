package com.suda.backend.controller.utils.ml.zero;



import com.suda.backend.consumer.WebSocketServer;
import com.suda.backend.service.data.AddDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

@Service
public class ZeroPredict {

    @Autowired
    private AddDataService addDataService;
    private double add;
    private String pdd;
    private String temp;
    private Instance inst;
    private int packageCnt;
    private Timer timer = new Timer();
    private int[] record = new int[6];
    private volatile boolean predictRunning;
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
        predictdata = new Vector<>();

        Instances train = DataSource.read("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Train.arff");
        train.setClassIndex(train.numAttributes() - 1);

        Instances race = new Instances(train, 0);
        ZeroLoader zeroRLoader = new ZeroLoader();

        FileReader reader = new FileReader("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\20test.txt");
        BufferedReader br = new BufferedReader(reader);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getCnt();
                System.out.println("一个周期结束");
            }
        }, 0, 5000);

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
                double res = zeroRLoader.loadZeroRModel().classifyInstance(inst);
                record[5]++;

                switch ((int) res) {
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
                    case 24:
                        setPdd("apache2.");
                        record[4]++;
                        break;
                    case 25:
                        setPdd("mailbomb.");
                        record[1]++;
                        break;
                    case 26:
                        setPdd("processtable.");
                        record[1]++;
                        break;
                    case 27:
                        setPdd("udpstorm.");
                        record[1]++;
                        break;
                    case 28:
                        setPdd("httptunnel.");
                        record[1]++;
                        break;
                    case 29:
                        setPdd("ps.");
                        record[1]++;
                        break;
                    case 30:
                        setPdd("sqlattack.");
                        record[4]++;
                        break;
                    case 31:
                        setPdd("xterm.");
                        record[1]++;
                        break;
                    case 32:
                        setPdd("named.");
                        record[3]++;
                        break;
                    case 33:
                        setPdd("sendmail.");
                        record[3]++;
                        break;
                    case 34:
                        setPdd("snmpgetattack.");
                        record[3]++;
                        break;
                    case 35:
                        setPdd("snmpguess.");
                        record[3]++;
                        break;
                    case 36:
                        setPdd("worm.");
                        record[3]++;
                        break;
                    case 37:
                        setPdd("xlock.");
                        record[3]++;
                        break;
                    case 38:
                        setPdd("xsnoop.");
                        record[3]++;
                        break;
                    case 39:
                        setPdd("saint.");
                        record[2]++;
                        break;
                }
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
