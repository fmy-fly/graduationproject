package com.suda.backend.controller.utils.ml.zero;

import weka.classifiers.rules.ZeroR;

import java.io.*;

public class ZeroSerialization {

    public static void main(String[] args) throws Exception {
        // TODO: You can use this space for any testing or demonstration if needed
    }

    public void persistModel(ZeroR model, String modelPath) {
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
    public ZeroR reloadPersistModel(String modelPath) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(modelPath)));
            ZeroR model = (ZeroR) ois.readObject();
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}