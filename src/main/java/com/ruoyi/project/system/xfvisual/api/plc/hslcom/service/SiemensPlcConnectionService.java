package com.ruoyi.project.system.xfvisual.api.plc.hslcom.service;

import HslCommunication.Core.Types.OperateResult;
import HslCommunication.Profinet.Siemens.SiemensPLCS;
import HslCommunication.Profinet.Siemens.SiemensS7Net;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class SiemensPlcConnectionService {

    private SiemensS7Net siemensPLC;

    @PostConstruct
    public void init() {
        // 初始化西门子PLC连接，使用HSL库
        connectPlc();
    }

    private void connectPlc() {
        // 先关闭现有连接（如果存在）
        if (siemensPLC != null) {
            siemensPLC.ConnectClose();
        }

        // 重新创建连接
        siemensPLC = new SiemensS7Net(SiemensPLCS.S1200, "192.168.0.1");
        siemensPLC.setPort(102); // 设置端口号，默认102

        // 连接PLC
        OperateResult connectResult = siemensPLC.ConnectServer();
        if (!connectResult.IsSuccess) {
            System.out.println("PLC连接失败: " + connectResult.Message);
        } else {
            System.out.println("PLC连接成功");
        }
    }

    public SiemensS7Net getPlc() {
        return siemensPLC;
    }

    @PreDestroy
    public void destroy() {
        // 应用关闭时释放PLC连接
        if (siemensPLC != null) {
            siemensPLC.ConnectClose();
        }
    }

    // 每4小时执行一次（4小时 = 14400000毫秒）
    // 每12小时执行一次（12小时 = 43200000毫秒）
//    @Scheduled(fixedRate = 43200000)
//    public void restartPlcConnection() {
//        System.out.println("开始重启PLC连接...");
//        connectPlc();
//    }
}
