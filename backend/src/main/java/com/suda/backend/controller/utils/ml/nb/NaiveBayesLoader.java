package com.suda.backend.controller.utils.ml.nb;
// -*- coding: utf-8 -*-


import weka.classifiers.bayes.NaiveBayes;

public class NaiveBayesLoader {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        NaiveBayesLoader loader = new NaiveBayesLoader();
        System.out.println(loader.loadNaiveBayesModel());
        System.gc();
    }

    public NaiveBayes loadNaiveBayesModel() {
        NaiveBayesSerialization nbSerialization = new NaiveBayesSerialization();
        return nbSerialization.reloadPersistModel("C:\\Users\\17914\\Desktop\\graduationProject\\model\\NaiveBayes.model");
    }
}
