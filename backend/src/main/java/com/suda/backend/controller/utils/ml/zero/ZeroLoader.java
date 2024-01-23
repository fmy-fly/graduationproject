package com.suda.backend.controller.utils.ml.zero;

import weka.classifiers.rules.ZeroR;

public class ZeroLoader {

    public static void main(String[] args) {
        ZeroLoader loader = new ZeroLoader();
        System.out.println(loader.loadZeroRModel());
        System.gc();
    }

    public ZeroR loadZeroRModel() {
        ZeroSerialization zeroRSerialization = new ZeroSerialization();
        return zeroRSerialization.reloadPersistModel("C:\\Users\\17914\\Desktop\\graduationProject\\model\\ZeroR.model");
    }
}