package com.ruoyi.project.system.status.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * AGV状态对象 bj_agv_status
 * 
 * @author ruoyi
 * @date 2025-07-16
 */
public class BjAgvStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** agv号 */
    @Excel(name = "agv号")
    private String agvNo;

    /** 当前任务号 */
    @Excel(name = "当前任务号")
    private String taskNo;

    /** 短任务号 */
    @Excel(name = "短任务号")
    private String signNo;

    /** 当前任务状态 */
    @Excel(name = "当前任务状态")
    private String taskStatus;

    /** agv状态 */
    @Excel(name = "agv状态")
    private String agvStatus;

    /** agv电量 */
    @Excel(name = "agv电量")
    private String batteryLevel;

    /** agv位置 */
    @Excel(name = "agv位置")
    private String currentPosition;

    /** 无人机号 */
    @Excel(name = "无人机号")
    private String uavNo;

    /** 无人机状态 */
    @Excel(name = "无人机状态")
    private String uavStatus;

    /** 油量 */
    @Excel(name = "油量")
    private String oilQuantity;

    /** 油类型 */
    @Excel(name = "油类型")
    private String oilType;

    /** 挂弹型号1 */
    @Excel(name = "挂弹型号1")
    private String model1;

    /** 挂弹数量1 */
    @Excel(name = "挂弹数量1")
    private String quantity1;

    /** 挂弹型号2 */
    @Excel(name = "挂弹型号2")
    private String model2;

    /** 挂弹数量2 */
    @Excel(name = "挂弹数量2")
    private String quantity2;

    /** 挂弹型号3 */
    @Excel(name = "挂弹型号3")
    private String model3;

    /** 挂弹数量3 */
    @Excel(name = "挂弹数量3")
    private String quantity3;

    /** 挂弹型号4 */
    @Excel(name = "挂弹型号4")
    private String model4;

    /** 挂弹数量4 */
    @Excel(name = "挂弹数量4")
    private String quantity4;

    /** 挂单型号5 */
    @Excel(name = "挂单型号5")
    private String model5;

    /** 挂弹数量5 */
    @Excel(name = "挂弹数量5")
    private String quantity5;

    /** 集装箱号 */
    @Excel(name = "集装箱号")
    private String containerNo;

    /** 集装箱状态 */
    @Excel(name = "集装箱状态")
    private String containerStatus;

    /** 备注1 */
    @Excel(name = "备注1")
    private String remark1;

    /** 备注2 */
    @Excel(name = "备注2")
    private String remark2;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setAgvNo(String agvNo) 
    {
        this.agvNo = agvNo;
    }

    public String getAgvNo() 
    {
        return agvNo;
    }

    public void setTaskNo(String taskNo) 
    {
        this.taskNo = taskNo;
    }

    public String getTaskNo() 
    {
        return taskNo;
    }

    public void setSignNo(String signNo) 
    {
        this.signNo = signNo;
    }

    public String getSignNo() 
    {
        return signNo;
    }

    public void setTaskStatus(String taskStatus) 
    {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus() 
    {
        return taskStatus;
    }

    public void setAgvStatus(String agvStatus) 
    {
        this.agvStatus = agvStatus;
    }

    public String getAgvStatus() 
    {
        return agvStatus;
    }

    public void setBatteryLevel(String batteryLevel) 
    {
        this.batteryLevel = batteryLevel;
    }

    public String getBatteryLevel() 
    {
        return batteryLevel;
    }

    public void setCurrentPosition(String currentPosition) 
    {
        this.currentPosition = currentPosition;
    }

    public String getCurrentPosition() 
    {
        return currentPosition;
    }

    public void setUavNo(String uavNo) 
    {
        this.uavNo = uavNo;
    }

    public String getUavNo() 
    {
        return uavNo;
    }

    public void setUavStatus(String uavStatus) 
    {
        this.uavStatus = uavStatus;
    }

    public String getUavStatus() 
    {
        return uavStatus;
    }

    public void setOilQuantity(String oilQuantity) 
    {
        this.oilQuantity = oilQuantity;
    }

    public String getOilQuantity() 
    {
        return oilQuantity;
    }

    public void setOilType(String oilType) 
    {
        this.oilType = oilType;
    }

    public String getOilType() 
    {
        return oilType;
    }

    public void setModel1(String model1) 
    {
        this.model1 = model1;
    }

    public String getModel1() 
    {
        return model1;
    }

    public void setQuantity1(String quantity1) 
    {
        this.quantity1 = quantity1;
    }

    public String getQuantity1() 
    {
        return quantity1;
    }

    public void setModel2(String model2) 
    {
        this.model2 = model2;
    }

    public String getModel2() 
    {
        return model2;
    }

    public void setQuantity2(String quantity2) 
    {
        this.quantity2 = quantity2;
    }

    public String getQuantity2() 
    {
        return quantity2;
    }

    public void setModel3(String model3) 
    {
        this.model3 = model3;
    }

    public String getModel3() 
    {
        return model3;
    }

    public void setQuantity3(String quantity3) 
    {
        this.quantity3 = quantity3;
    }

    public String getQuantity3() 
    {
        return quantity3;
    }

    public void setModel4(String model4) 
    {
        this.model4 = model4;
    }

    public String getModel4() 
    {
        return model4;
    }

    public void setQuantity4(String quantity4) 
    {
        this.quantity4 = quantity4;
    }

    public String getQuantity4() 
    {
        return quantity4;
    }

    public void setModel5(String model5) 
    {
        this.model5 = model5;
    }

    public String getModel5() 
    {
        return model5;
    }

    public void setQuantity5(String quantity5) 
    {
        this.quantity5 = quantity5;
    }

    public String getQuantity5() 
    {
        return quantity5;
    }

    public void setContainerNo(String containerNo) 
    {
        this.containerNo = containerNo;
    }

    public String getContainerNo() 
    {
        return containerNo;
    }

    public void setContainerStatus(String containerStatus) 
    {
        this.containerStatus = containerStatus;
    }

    public String getContainerStatus() 
    {
        return containerStatus;
    }

    public void setRemark1(String remark1) 
    {
        this.remark1 = remark1;
    }

    public String getRemark1() 
    {
        return remark1;
    }

    public void setRemark2(String remark2) 
    {
        this.remark2 = remark2;
    }

    public String getRemark2() 
    {
        return remark2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("agvNo", getAgvNo())
            .append("taskNo", getTaskNo())
            .append("signNo", getSignNo())
            .append("taskStatus", getTaskStatus())
            .append("agvStatus", getAgvStatus())
            .append("batteryLevel", getBatteryLevel())
            .append("currentPosition", getCurrentPosition())
            .append("uavNo", getUavNo())
            .append("uavStatus", getUavStatus())
            .append("oilQuantity", getOilQuantity())
            .append("oilType", getOilType())
            .append("model1", getModel1())
            .append("quantity1", getQuantity1())
            .append("model2", getModel2())
            .append("quantity2", getQuantity2())
            .append("model3", getModel3())
            .append("quantity3", getQuantity3())
            .append("model4", getModel4())
            .append("quantity4", getQuantity4())
            .append("model5", getModel5())
            .append("quantity5", getQuantity5())
            .append("containerNo", getContainerNo())
            .append("containerStatus", getContainerStatus())
            .append("remark1", getRemark1())
            .append("remark2", getRemark2())
            .toString();
    }
}
