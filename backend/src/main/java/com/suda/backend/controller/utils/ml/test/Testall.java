package com.suda.backend.controller.utils.ml.test;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.trees.RandomForest;  // Import RandomForest classifier
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

import java.io.File;

public class Testall {

        public static void main(String[] args) throws Exception {
                Classifier classifier1;
                Classifier classifier2;
                Classifier classifier3;
//                Classifier classifier4;
//                Classifier classifier5;
                Classifier classifier6;  // Add RandomForest classifier

                File inputFile = new File("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Train.arff");
                ArffLoader atf = new ArffLoader();
                atf.setFile(inputFile);
                Instances instancesTrain = atf.getDataSet();

                inputFile = new File("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Test.arff");
                atf.setFile(inputFile);
                Instances instancesTest = atf.getDataSet();

                instancesTest.setClassIndex(instancesTest.numAttributes() - 1);
                instancesTrain.setClassIndex(instancesTrain.numAttributes() - 1);

                // 朴素贝叶斯算法
                classifier1 = (Classifier) Class.forName("weka.classifiers.bayes.NaiveBayes").newInstance();
                // 决策树
                classifier2 = (Classifier) Class.forName("weka.classifiers.trees.J48").newInstance();
                // Zero
                classifier3 = (Classifier) Class.forName("weka.classifiers.rules.ZeroR").newInstance();
                // LibSVM
//                classifier4 = new LibSVM();
                // k-NN
//                classifier5 = (Classifier) Class.forName("weka.classifiers.lazy.IBk").newInstance();
                // RandomForest
                classifier6 = new RandomForest();

                // 设置LibSVM参数
                // 训练模型
                classifier1.buildClassifier(instancesTrain);
                classifier2.buildClassifier(instancesTrain);
                classifier3.buildClassifier(instancesTrain);
//                classifier4.buildClassifier(instancesTrain);
//                classifier5.buildClassifier(instancesTrain);
                classifier6.buildClassifier(instancesTrain);

                // 保存模型
                SerializationHelper.write("C:\\Users\\17914\\Desktop\\graduationProject\\model\\NaiveBayes.model", classifier1);
                SerializationHelper.write("C:\\Users\\17914\\Desktop\\graduationProject\\model\\J48_test.model", classifier2);
                SerializationHelper.write("C:\\Users\\17914\\Desktop\\graduationProject\\model\\ZeroR.model", classifier3);
//                SerializationHelper.write("C:\\Users\\17914\\Desktop\\graduationProject\\model\\LibSVM.model", classifier4);
//                SerializationHelper.write("C:\\Users\\17914\\Desktop\\graduationProject\\model\\kNN.model", classifier5);
                SerializationHelper.write("C:\\Users\\17914\\Desktop\\graduationProject\\model\\RandomForest.model", classifier6);

                // 评估模型
                Evaluation eval = new Evaluation(instancesTrain);

                eval.evaluateModel(classifier1, instancesTest);
                System.out.println("朴素贝叶斯算法评估结果" + "\n" + eval.toSummaryString() + "\n" + eval.toClassDetailsString());
                eval.evaluateModel(classifier2, instancesTest);
                System.out.println("决策树算法评估结果" + "\n" + eval.toSummaryString() + "\n" + eval.toClassDetailsString());
                eval.evaluateModel(classifier3, instancesTest);
                System.out.println("Zero算法评估结果" + "\n" + eval.toSummaryString() + "\n" + eval.toClassDetailsString());
//                eval.evaluateModel(classifier4, instancesTest);
//                System.out.println("LibSVM算法评估结果" + "\n" + eval.toSummaryString() + "\n" + eval.toClassDetailsString());
//                eval.evaluateModel(classifier5, instancesTest);
//                System.out.println("k-NN算法评估结果" + "\n" + eval.toSummaryString() + "\n" + eval.toClassDetailsString());
                eval.evaluateModel(classifier6, instancesTest);
                System.out.println("RandomForest算法评估结果" + "\n" + eval.toSummaryString() + "\n" + eval.toClassDetailsString());
        }
}