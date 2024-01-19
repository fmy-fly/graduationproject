// -*- coding: utf-8 -*-
package com.suda.backend.controller.utils.ml.tree;

import weka.classifiers.trees.J48;

import java.io.*;

public class treeSerialization {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//Serialization a = new Serialization();		
		//a.persistModel(tree, "F:/KDDCUP1999/J48.model");
	}
	public void persistModel(J48 model, String ModelPath) {
		 ObjectOutputStream oos = null;
	        try {
	            oos = new ObjectOutputStream(
	            new FileOutputStream(ModelPath));
	            oos.writeObject(model);
	            oos.flush();
	            oos.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	@SuppressWarnings("resource")
	public J48 reloadPersistModel(String ModelPath) {
        ObjectInputStream ois = null;
        try {
        	ois = new ObjectInputStream(new FileInputStream(new File(ModelPath)));
            J48 model = (J48) ois.readObject();
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
