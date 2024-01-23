package com.suda.backend.controller.utils;

import com.suda.backend.controller.utils.ml.tree.TreePredict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class AttackTreeDetection {

    @Autowired
    private TreePredict treePredict;
    @GetMapping("/api/ml/start/tree")
    public String startTreeCapture() throws Exception {

        treePredict.predict();
        return "Packet capture started!";
    }

    @GetMapping("/api/ml/stop/tree")
    public String stopTreeCapture() {
        treePredict.stopPredict();
        return "Packet capture stopped!";
    }

}
