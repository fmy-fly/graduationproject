// -*- coding: utf-8 -*-
package com.suda.backend.controller.utils.ml.Nb;

import com.suda.backend.controller.utils.ml.tree.LoaderTree;
import com.suda.backend.controller.utils.ml.tree.TreePredict;
import lombok.Getter;
import lombok.Setter;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Vector;

public class NbPredict {

    private double add;
    @Setter
    @Getter
    private String pdd;
    @Setter
    @Getter
    private String temp;
    @Setter
    @Getter
    private Instance inst;


    private Vector<String> predictData;
    public Vector<String> getPredictdata() {
        return predictData;
    }
    public void setPredictData(Vector<String> predictData) {
        this.predictData = predictData;
    }

    public NbPredict() {
        try {
            predict();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void predict() throws Exception{
        predictData=new Vector<String>();

        Instances train = DataSource.read("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Train.arff");
        train.setClassIndex(train.numAttributes() - 1);

        Instances race = new Instances(train, 0);
        NaiveBayesLoader naiveBayesLoader=new NaiveBayesLoader();


        FileReader reader = new FileReader("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\test.txt");
        BufferedReader br = new BufferedReader(reader);

        String string = null;
        while ((string = br.readLine()) != null) {

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
                double res = naiveBayesLoader.loadNaiveBayesModel().classifyInstance(inst);

                switch((int)res){

                    case 0:setPdd("back.");break;
                    case 1:setPdd("teardrop.");break;
                    case 2:setPdd("loadmodule.");break;
                    case 3:setPdd("neptune.");break;
                    case 4:setPdd("rootkit.");break;
                    case 5:setPdd("phf.");break;
                    case 6:setPdd("satan.");break;
                    case 7:setPdd("buffer_overflow.");break;
                    case 8:setPdd("ftp_write.");break;
                    case 9:setPdd("land.");break;
                    case 10:setPdd("spy.");break;
                    case 11:setPdd("ipsweep.");break;
                    case 12:setPdd("multihop.");break;
                    case 13:setPdd("smurf.");break;
                    case 14:setPdd("pod.");break;
                    case 15:setPdd("perl.");break;
                    case 16:setPdd("warezclient.");break;
                    case 17:setPdd("nmap.");break;
                    case 18:setPdd("imap.");break;
                    case 19:setPdd("warezmaster.");break;
                    case 20:setPdd("portsweep.");break;
                    case 21:setPdd("normal.");break;
                    case 22:setPdd("guess_passwd.");break;
                    case 23:setPdd("mscan.");break;
                    case 24:setPdd("apache2.");break;
                    case 25:setPdd("mailbomb.");break;
                    case 26:setPdd("processtable.");break;
                    case 27:setPdd("udpstorm.");break;
                    case 28:setPdd("httptunnel.");break;
                    case 29:setPdd("ps.");break;
                    case 30:setPdd("sqlattack.");break;
                    case 31:setPdd("xterm.");break;
                    case 32:setPdd("named.");break;
                    case 33:setPdd("sendmail.");break;
                    case 34:setPdd("snmpgetattack.");break;
                    case 35:setPdd("snmpguess.");break;
                    case 36:setPdd("worm.");break;
                    case 37:setPdd("xlock.");break;
                    case 38:setPdd("xsnoop.");break;
                    case 39:setPdd("saint.");break;

                }
                temp=getPdd();
                predictData.add(temp);				//System.out.println("预测值"+getPdd());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        br.close();
        reader.close();

    }
    public static void main(String[] args) {
        NbPredict PredictData=new NbPredict();
        String str5 ="\n";
        try {
            PredictData.predict();
            for (Iterator<String> it = PredictData.getPredictdata().iterator(); it.hasNext(); ) {
                str5 += it.next();
                str5 += "\n";
            }
            System.out.println(str5);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
