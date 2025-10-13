package com.ruoyi.project.system.xfvisual.api.plc.hslcom;


import HslCommunication.Core.Types.OperateResult;
import HslCommunication.Profinet.Siemens.SiemensPLCS;
import HslCommunication.Profinet.Siemens.SiemensS7Net;

/**
 * PLC工具类，用于创建和管理与西门子S7系列PLC的连接
 */
public class PlcUtil {

    /**
     * 创建并连接到西门子S7 PLC
     *
     * @param plcType PLC型号（如：SiemensPLCS.S1200, SiemensPLCS.S1500等）
     * @param ip      PLC的IP地址
     * @param port    端口号，默认为102
     * @return 连接成功的SiemensS7Net实例，如果连接失败则返回null
     */
    public static SiemensS7Net connectToPlc(SiemensPLCS plcType, String ip, int port) {
        // 创建PLC连接对象
        SiemensS7Net siemensS7Net = new SiemensS7Net(plcType, ip);

        // 设置端口号
        siemensS7Net.setPort(port);

        // 连接PLC
        OperateResult connectResult = siemensS7Net.ConnectServer();
        if (!connectResult.IsSuccess) {
            System.out.println("连接PLC失败：" + connectResult.Message);
            return null;
        }

        System.out.println("连接PLC成功！");
        return siemensS7Net;
    }

    /**
     * 创建并连接到西门子S7 PLC（默认端口102）
     *
     * @param plcType PLC型号
     * @param ip      PLC的IP地址
     * @return 连接成功的SiemensS7Net实例，如果连接失败则返回null
     */
    public static SiemensS7Net connectToPlc(SiemensPLCS plcType, String ip) {
        return connectToPlc(plcType, ip, 102);
    }

    /**
     * 断开与PLC的连接
     *
     * @param siemensS7Net 已连接的SiemensS7Net实例
     */
    public static void disconnectFromPlc(SiemensS7Net siemensS7Net) {
        if (siemensS7Net != null) {
            siemensS7Net.ConnectClose();
            System.out.println("已断开与PLC的连接");
        }
    }
}
