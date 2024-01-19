package com.suda.backend.controller.utils;

import com.suda.backend.utils.PacketCaptureService;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class AttackDetection {

    @Autowired
    private PacketCaptureService packetCaptureService;

    @GetMapping("/api/ml/start/tree")
    public String startCapture() throws PcapNativeException {

        for (PcapNetworkInterface dev : Pcaps.findAllDevs()) {
            System.out.println("Network Interface: " + dev.getName());
        }
        packetCaptureService.startPacketCapture("\\Device\\NPF_{65A43943-51FB-423E-B100-C2C67D4B3442}");
        return "Packet capture started!";
    }

    @GetMapping("/api/ml/stop")
    public String stopCapture() {
        packetCaptureService.stopPacketCapture();
        System.out.println("Packet capture stopped");
        return "Packet capture stopped!";
    }
}
