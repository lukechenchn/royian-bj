// BatchPlcUtil.java
package com.ruoyi.project.system.xfvisual.api.plc.hslcom.test.batch;

import HslCommunication.Core.Types.OperateResult;
import HslCommunication.Core.Types.OperateResultExOne;
import HslCommunication.Profinet.Siemens.SiemensS7Net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批量PLC操作工具类
 */
public class BatchPlcUtil {

    /**
     * 点位信息类
     */
    public static class PlcPoint {
        private String address;      // PLC地址
        private PointType type;      // 数据类型
        private Object value;        // 值（用于写入）

        public PlcPoint(String address, PointType type) {
            this.address = address;
            this.type = type;
        }

        public PlcPoint(String address, PointType type, Object value) {
            this.address = address;
            this.type = type;
            this.value = value;
        }

        // Getters and Setters
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public PointType getType() { return type; }
        public void setType(PointType type) { this.type = type; }
        public Object getValue() { return value; }
        public void setValue(Object value) { this.value = value; }
    }

    /**
     * 点位数据类型枚举
     */
    public enum PointType {
        BOOL,       // 布尔类型
        INT16,      // 16位整数
        INT32,      // 32位整数
        FLOAT,      // 浮点数
        DOUBLE,     // 双精度浮点数
        STRING      // 字符串
    }

    /**
     * 批量读取PLC点位数据
     *
     * @param siemensS7Net PLC连接对象
     * @param points       点位列表
     * @return 包含各点位数据的Map
     */
    public static Map<String, Object> batchRead(SiemensS7Net siemensS7Net, List<PlcPoint> points) {
        Map<String, Object> resultMap = new HashMap<>();

        for (PlcPoint point : points) {
            try {
                switch (point.getType()) {
                    case BOOL:
                        OperateResultExOne<Boolean> boolResult = siemensS7Net.ReadBool(point.getAddress());
                        if (boolResult.IsSuccess) {
                            resultMap.put(point.getAddress(), boolResult.Content);
                        } else {
                            resultMap.put(point.getAddress(), "ERROR: " + boolResult.Message);
                        }
                        break;
                    case INT16:
                        OperateResultExOne<Short> int16Result = siemensS7Net.ReadInt16(point.getAddress());
                        if (int16Result.IsSuccess) {
                            resultMap.put(point.getAddress(), int16Result.Content);
                        } else {
                            resultMap.put(point.getAddress(), "ERROR: " + int16Result.Message);
                        }
                        break;
                    case INT32:
                        OperateResultExOne<Integer> int32Result = siemensS7Net.ReadInt32(point.getAddress());
                        if (int32Result.IsSuccess) {
                            resultMap.put(point.getAddress(), int32Result.Content);
                        } else {
                            resultMap.put(point.getAddress(), "ERROR: " + int32Result.Message);
                        }
                        break;
                    case FLOAT:
                        OperateResultExOne<Float> floatResult = siemensS7Net.ReadFloat(point.getAddress());
                        if (floatResult.IsSuccess) {
                            resultMap.put(point.getAddress(), floatResult.Content);
                        } else {
                            resultMap.put(point.getAddress(), "ERROR: " + floatResult.Message);
                        }
                        break;
                    case DOUBLE:
                        OperateResultExOne<Double> doubleResult = siemensS7Net.ReadDouble(point.getAddress());
                        if (doubleResult.IsSuccess) {
                            resultMap.put(point.getAddress(), doubleResult.Content);
                        } else {
                            resultMap.put(point.getAddress(), "ERROR: " + doubleResult.Message);
                        }
                        break;
                    case STRING:
                        OperateResultExOne<String> stringResult = siemensS7Net.ReadString(point.getAddress(), (short) 100); // 默认长度100
                        if (stringResult.IsSuccess) {
                            resultMap.put(point.getAddress(), stringResult.Content);
                        } else {
                            resultMap.put(point.getAddress(), "ERROR: " + stringResult.Message);
                        }
                        break;
                }
            } catch (Exception e) {
                resultMap.put(point.getAddress(), "EXCEPTION: " + e.getMessage());
            }
        }

        return resultMap;
    }

    /**
     * 批量写入PLC点位数据
     *
     * @param siemensS7Net PLC连接对象
     * @param points       要写入的点位列表
     * @return 写入结果列表，true表示成功，false表示失败
     */
    public static List<Boolean> batchWrite(SiemensS7Net siemensS7Net, List<PlcPoint> points) {
        List<Boolean> result = new ArrayList<>();

        for (PlcPoint point : points) {
            boolean writeSuccess = false;
            try {
                OperateResult writeResult = null;
                
                switch (point.getType()) {
                    case BOOL:
                        writeResult = siemensS7Net.Write(point.getAddress(), (Boolean) point.getValue());
                        break;
                    case INT16:
                        writeResult = siemensS7Net.Write(point.getAddress(), ((Number) point.getValue()).shortValue());
                        break;
                    case INT32:
                        writeResult = siemensS7Net.Write(point.getAddress(), ((Number) point.getValue()).intValue());
                        break;
                    case FLOAT:
                        writeResult = siemensS7Net.Write(point.getAddress(), ((Number) point.getValue()).floatValue());
                        break;
                    case DOUBLE:
                        writeResult = siemensS7Net.Write(point.getAddress(), ((Number) point.getValue()).doubleValue());
                        break;
                    case STRING:
                        writeResult = siemensS7Net.Write(point.getAddress(), (String) point.getValue());
                        break;
                }

                if (writeResult != null && writeResult.IsSuccess) {
                    writeSuccess = true;
                }
            } catch (Exception e) {
                writeSuccess = false;
            }
            
            result.add(writeSuccess);
        }

        return result;
    }
}
