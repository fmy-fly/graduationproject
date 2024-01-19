// -*- coding: utf-8 -*-
package com.suda.backend.controller.utils.ml.tree;

import weka.classifiers.trees.J48;

public class LoaderTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoaderTree mal=new LoaderTree();
		System.out.println(mal.loadTree());
		System.gc();
	}
	
	public J48 loadTree() {
		treeSerialization test=new treeSerialization();
		J48 model=(J48) test.reloadPersistModel("C:\\Users\\17914\\Desktop\\ML-ATIC-master\\ML-ATIC-master\\data\\Model\\test.model");
		return model;
	}
	
}
