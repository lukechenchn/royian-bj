// BatchPlcExample.java
package com.ruoyi.project.system.xfvisual.api.plc.hslcom.test.batch;


import HslCommunication.Profinet.Siemens.SiemensPLCS;
import HslCommunication.Profinet.Siemens.SiemensS7Net;
import com.ruoyi.project.system.xfvisual.api.plc.hslcom.PlcUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BatchPlcExample {
    public static void main(String[] args) {
        // 使用工具类连接PLC
        SiemensS7Net siemensS7Net = PlcUtil.connectToPlc(SiemensPLCS.S1200, "192.168.0.1");
        
        if (siemensS7Net == null) {
            return;
        }

        try {
            // 创建点位列表
            List<BatchPlcUtil.PlcPoint> readPoints = new ArrayList<>();
            readPoints.add(new BatchPlcUtil.PlcPoint("Q0.0", BatchPlcUtil.PointType.BOOL));
            readPoints.add(new BatchPlcUtil.PlcPoint("DB1.DBW0", BatchPlcUtil.PointType.INT16));
            readPoints.add(new BatchPlcUtil.PlcPoint("DB1.DBD4", BatchPlcUtil.PointType.FLOAT));
            readPoints.add(new BatchPlcUtil.PlcPoint("DB1.DBX8.0", BatchPlcUtil.PointType.BOOL));

            // 批量读取
            System.out.println("=== 批量读取 ===");
            Map<String, Object> readResults = BatchPlcUtil.batchRead(siemensS7Net, readPoints);
            for (Map.Entry<String, Object> entry : readResults.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // 创建写入点位列表
            List<BatchPlcUtil.PlcPoint> writePoints = new ArrayList<>();
            writePoints.add(new BatchPlcUtil.PlcPoint("Q0.1", BatchPlcUtil.PointType.BOOL, true));
            writePoints.add(new BatchPlcUtil.PlcPoint("DB1.DBW2", BatchPlcUtil.PointType.INT16, (short) 5678));
            writePoints.add(new BatchPlcUtil.PlcPoint("DB1.DBD8", BatchPlcUtil.PointType.FLOAT, 2.71f));
            
            // 批量写入
            System.out.println("\n=== 批量写入 ===");
            List<Boolean> writeResults = BatchPlcUtil.batchWrite(siemensS7Net, writePoints);
            for (int i = 0; i < writePoints.size(); i++) {
                System.out.println(writePoints.get(i).getAddress() + ": " + 
                    (writeResults.get(i) ? "写入成功" : "写入失败"));
            }

        } finally {
            // 使用工具类断开连接
            PlcUtil.disconnectFromPlc(siemensS7Net);
        }
    }
}
