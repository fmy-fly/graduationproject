package com.suda.backend.controller.utils.ml;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class temp {

    public static void main(String[] args) {
        // 输入的ARFF文件路径
        String arffFilePath = "C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Train.arff";

        // 输出的TXT文件路径
        String txtFilePath = "C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\temp1.txt";

        try {
            // 从ARFF文件中加载数据
            ArffLoader arffLoader = new ArffLoader();
            arffLoader.setFile(new java.io.File(arffFilePath));
            Instances data = arffLoader.getDataSet();

            // 创建TXT文件并写入数据
            BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath));

            // 写入属性名称（特征名）
            for (int i = 0; i < data.numAttributes(); i++) {
                writer.write(data.attribute(i).name() + "\t");
            }
            writer.newLine();

            // 写入每个实例的值
            for (int i = 0; i < data.numInstances(); i++) {
                for (int j = 0; j < data.numAttributes(); j++) {
                    writer.write(data.instance(i).value(j) + "\t");
                }
                writer.newLine();
            }

            // 关闭文件写入器
            writer.close();

            System.out.println("ARFF to TXT conversion successful.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}