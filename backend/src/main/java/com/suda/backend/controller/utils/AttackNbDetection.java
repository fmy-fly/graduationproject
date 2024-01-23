package com.suda.backend.controller.utils;

import com.suda.backend.controller.utils.ml.nb.NbPredict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class AttackNbDetection {

    @Autowired
    private NbPredict nbPredict;
    @GetMapping("/api/ml/start/nb")
    public String startNbCapture() throws Exception {
        nbPredict.predict();
        return "Packet capture started!";
    }
    @GetMapping("/api/ml/stop/nb")
    public String stopNbCapture() {
        nbPredict.stopPredict();
        return "Packet capture stopped!";
    }
}
