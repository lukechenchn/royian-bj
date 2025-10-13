package com.ruoyi.project.system.xfvisual.api.plc.hslcom.domain;

/**
 * PLC点位模型，描述一个PLC点位的信息
 */
public class PlcPoint {
    private String tableName; // 所属点表名称
    private String pointId; // 点位唯一标识
    private String plcAddress; // PLC地址，如"I0.0"、"DB1.DBW0"
    private String dataType; // 数据类型，如"BOOL"、"INT16"、"FLOAT"
    private Object value; // 点位值
    private long lastUpdateTime; // 最后更新时间戳

    public PlcPoint(String tableName, String pointId, String plcAddress, String dataType) {
        this.tableName = tableName;
        this.pointId = pointId;
        this.plcAddress = plcAddress;
        this.dataType = dataType;
    }

    // getter和setter方法
    public String getTableName() {
        return tableName;
    }

    public String getPointId() {
        return pointId;
    }

    public String getPlcAddress() {
        return plcAddress;
    }

    public String getDataType() {
        return dataType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }
}
    