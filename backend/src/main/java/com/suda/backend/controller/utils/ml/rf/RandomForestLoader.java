package com.suda.backend.controller.utils.ml.rf;

import weka.classifiers.trees.RandomForest;

public class RandomForestLoader {

    public static void main(String[] args) {
        RandomForestLoader loader = new RandomForestLoader();
        System.out.println(loader.loadRandomForestModel());
        System.gc();
    }

    public RandomForest loadRandomForestModel() {
        RandomForestSerialization rfSerialization = new RandomForestSerialization();
        return rfSerialization.reloadPersistModel("C:\\Users\\17914\\Desktop\\graduationProject\\model\\RandomForest.model");
    }
}
