package com.suda.backend.controller.utils.ml.Nb;

// -*- coding: utf-8 -*-


import weka.classifiers.bayes.NaiveBayes;

import java.io.*;

public class NaiveBayesSerialization {

    public static void main(String[] args) throws Exception {
        // TODO: You can use this space for any testing or demonstration if needed
    }

    public void persistModel(NaiveBayes model, String modelPath) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(modelPath));
            oos.writeObject(model);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
    public NaiveBayes reloadPersistModel(String modelPath) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(modelPath)));
            NaiveBayes model = (NaiveBayes) ois.readObject();
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
