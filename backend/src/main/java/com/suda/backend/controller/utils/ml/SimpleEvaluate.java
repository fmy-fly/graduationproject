// -*- coding: utf-8 -*-
package com.suda.backend.controller.utils.ml;

import com.suda.backend.controller.utils.ml.tree.LoaderTree;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class SimpleEvaluate {

	public static void main(String[] args) {
		
	}
	
	public String simpleEvalute() throws Exception {
		Instances train = DataSource.read("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Train.arff");
		train.setClassIndex(train.numAttributes() - 1);
		Instances test = DataSource.read("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\TrainAndTest\\Test.arff");
		test.setClassIndex(test.numAttributes() - 1);
		LoaderTree tree=new LoaderTree();
		Evaluation eval = new Evaluation(train);
		eval.evaluateModel(tree.loadTree(), test);
		return (eval.toSummaryString()+"/n"+eval.toClassDetailsString());
	}

}
