package com.suda.backend.controller.utils;

import com.suda.backend.controller.utils.ml.rf.RandomForestLoader;
import com.suda.backend.controller.utils.ml.rf.RandomForestPredict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class AttackRfDetection {

    @Autowired
    private RandomForestPredict randomForestPredict;
    @GetMapping("/api/ml/start/rf")
    public String startRfCapture() throws Exception {
        randomForestPredict.predict();
        return "Packet capture started!";
    }
    @GetMapping("/api/ml/stop/rf")
    public String stopRfCapture() {
        randomForestPredict.stopPredict();
        return "Packet capture stopped!";
    }
}
