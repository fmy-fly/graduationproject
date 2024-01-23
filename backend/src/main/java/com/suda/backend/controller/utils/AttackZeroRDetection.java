package com.suda.backend.controller.utils;

import com.suda.backend.controller.utils.ml.nb.NbPredict;
import com.suda.backend.controller.utils.ml.zero.ZeroPredict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AttackZeroRDetection {

    @Autowired
    private ZeroPredict zeroPredict;
    @GetMapping("/api/ml/start/zero")
    public String startNbCapture() throws Exception {
        zeroPredict.predict();
        return "Packet capture started!";
    }
    @GetMapping("/api/ml/stop/zero")
    public String stopNbCapture() {
        zeroPredict.stopPredict();
        return "Packet capture stopped!";
    }
}
