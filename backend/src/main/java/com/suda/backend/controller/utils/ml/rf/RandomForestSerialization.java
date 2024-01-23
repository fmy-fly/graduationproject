package com.suda.backend.controller.utils.ml.rf;

import weka.classifiers.trees.RandomForest;

import java.io.*;

public class RandomForestSerialization {

    public static void main(String[] args) throws Exception {
        // TODO: You can use this space for any testing or demonstration if needed
    }

    public void persistModel(RandomForest model, String modelPath) {
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
    public RandomForest reloadPersistModel(String modelPath) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(modelPath)));
            RandomForest model = (RandomForest) ois.readObject();
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
