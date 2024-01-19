// -*- coding: utf-8 -*-
package com.suda.backend.controller.utils.ml.test;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class TestBP {

	public static void main(String[] args) throws Exception {

		Instances train = DataSource.read("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Train.arff");
		train.setClassIndex(train.numAttributes() - 1);
		Instances test = DataSource.read("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Test.arff");
		test.setClassIndex(test.numAttributes() - 1);

		MultilayerPerceptron mp=new MultilayerPerceptron();


		mp.setGUI(false);
		mp.setAutoBuild(true);
		mp.setDebug(false);
		mp.setDecay(false);
		mp.setHiddenLayers("a");
		mp.setLearningRate(0.3);
		mp.setMomentum(0.8);
		mp.setNormalizeAttributes(true);
		mp.setNormalizeNumericClass(true);
		mp.setReset(false);
		mp.setSeed(0);
		mp.setTrainingTime(300);
		mp.setValidationSetSize(20);
		mp.setValidationThreshold(50);
		mp.setNominalToBinaryFilter(true);

		mp.buildClassifier(train);
		SerializationHelper.write("C:\\Users\\17914\\Desktop\\graduationProject\\model\\MultilayerPerceptron.model", mp);

		Evaluation eval=new Evaluation(train);
		eval.evaluateModel(mp, test);

		System.out.println(eval.meanAbsoluteError());
		System.out.println(eval.rootMeanSquaredError());
		System.out.println(mp.toString());


	}

}
