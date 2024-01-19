package com.suda.backend.utils;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.springframework.stereotype.Service;
import com.suda.backend.consumer.WebSocketServer;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class PacketCaptureService {

    private PcapNetworkInterface networkInterface;
    private PcapHandle handle;
    private volatile boolean captureRunning;  // 添加一个标志变量
    private  int packageCnt;  // 添加一个标志变量
    private final Timer timer = new Timer();
    public void startPacketCapture(String networkInterfaceName) throws PcapNativeException {
        System.setProperty("jna.library.path", "src/main/resources/win32-x86-64");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int packetCnt = getPacketCnt();
                System.out.println("Sending packet count to the frontend: " + packetCnt);
            }
        }, 0, 5000);  // 第一个参数是初始延迟，第二个参数是间隔时间（单位：毫秒）
        try {
            // 打开网络设备
            networkInterface = Pcaps.getDevByName(networkInterfaceName);

            // 打开捕获会话
            if (this.networkInterface != null) {
                handle = networkInterface.openLive(
                        65536,
                        PcapNetworkInterface.PromiscuousMode.PROMISCUOUS,
                        10
                );

                // 设置过滤器（可选）
                handle.setFilter("tcp", BpfProgram.BpfCompileMode.OPTIMIZE);

                // 开始捕获数据包
                captureRunning = true;  // 设置标志为 true，表示捕获正在运行

                handle.loop(-1, new PacketListener() {
                    @Override
                    public void gotPacket(Packet packet) {
                        // 处理捕获到的数据包
                        packageCnt++;
                        System.out.println(packageCnt);
                        System.out.println("Captured packet: " + packet);
                    }
                });

            } else {
                // 处理 networkInterface 为 null 的情况，可能需要初始化或抛出异常
                throw new Exception("networkInterface 为 null");
            }

        } catch (PcapNativeException | NotOpenException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 在 finally 块中关闭 handle
            closeHandle();
        }
    }

    public void stopPacketCapture() {
        captureRunning = false;
        timer.cancel();
        // 关闭捕获会话
        closeHandle();
    }

    private void closeHandle() {
        if (handle != null && handle.isOpen()) {
            try {
                handle.breakLoop();  // 通过 breakLoop 方法终止循环
                handle.close();
            } catch (NotOpenException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized int getPacketCnt() {
        int cnt = packageCnt;
        WebSocketServer.broadcastMessage(cnt);
        packageCnt = 0;
        return cnt;
    }
}
