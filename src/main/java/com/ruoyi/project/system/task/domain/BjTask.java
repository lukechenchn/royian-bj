package com.ruoyi.project.system.task.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * bj任务对象 bj_task
 *
 * @author ruoyi
 * @date 2025-08-22
 */
public class BjTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 任务号 */
    @Excel(name = "任务号")
    private String taskNo;

    /** agv号 */
    @Excel(name = "agv号")
    private String agvNo;

    /** 无人机号 */
    @Excel(name = "无人机号")
    private String uavNo;

    /** 集装箱号 */
    @Excel(name = "集装箱号")
    private String containerNo;

    /** 任务信号 */
    @Excel(name = "任务信号")
    private String sign;

    /** 短任务号 */
    @Excel(name = "短任务号")
    private String signNo;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private Long taskStatus;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Long isDeleted;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

    /** 是否已反馈 */
    @Excel(name = "是否已反馈")
    private Long isFeedback;

    /** 备注1 */
    @Excel(name = "备注1")
    private String remark1;

    /** 备注2 */
    @Excel(name = "备注2")
    private String remark2;

    /** 油类型 */
    @Excel(name = "油类型")
    private String oilType;

    /** 油数量 */
    @Excel(name = "油数量")
    private String oilNum;

    /** 弹类型 */
    @Excel(name = "弹类型")
    private String dType;

    /** 弹数量 */
    @Excel(name = "弹数量")
    private String dNum;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setTaskNo(String taskNo)
    {
        this.taskNo = taskNo;
    }

    public String getTaskNo()
    {
        return taskNo;
    }

    public void setAgvNo(String agvNo)
    {
        this.agvNo = agvNo;
    }

    public String getAgvNo()
    {
        return agvNo;
    }

    public void setUavNo(String uavNo)
    {
        this.uavNo = uavNo;
    }

    public String getUavNo()
    {
        return uavNo;
    }

    public void setContainerNo(String containerNo)
    {
        this.containerNo = containerNo;
    }

    public String getContainerNo()
    {
        return containerNo;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSignNo(String signNo)
    {
        this.signNo = signNo;
    }

    public String getSignNo()
    {
        return signNo;
    }

    public void setTaskStatus(Long taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public Long getTaskStatus()
    {
        return taskStatus;
    }

    public void setIsDeleted(Long isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted()
    {
        return isDeleted;
    }

    public void setFinishTime(Date finishTime)
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime()
    {
        return finishTime;
    }

    public void setIsFeedback(Long isFeedback)
    {
        this.isFeedback = isFeedback;
    }

    public Long getIsFeedback()
    {
        return isFeedback;
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

    public void setOilType(String oilType)
    {
        this.oilType = oilType;
    }

    public String getOilType()
    {
        return oilType;
    }

    public void setOilNum(String oilNum)
    {
        this.oilNum = oilNum;
    }

    public String getOilNum()
    {
        return oilNum;
    }

    public void setdType(String dType)
    {
        this.dType = dType;
    }

    public String getdType()
    {
        return dType;
    }

    public void setdNum(String dNum)
    {
        this.dNum = dNum;
    }

    public String getdNum()
    {
        return dNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("taskNo", getTaskNo())
                .append("agvNo", getAgvNo())
                .append("uavNo", getUavNo())
                .append("containerNo", getContainerNo())
                .append("sign", getSign())
                .append("signNo", getSignNo())
                .append("taskStatus", getTaskStatus())
                .append("isDeleted", getIsDeleted())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("finishTime", getFinishTime())
                .append("isFeedback", getIsFeedback())
                .append("remark1", getRemark1())
                .append("remark2", getRemark2())
                .append("oilType", getOilType())
                .append("oilNum", getOilNum())
                .append("dType", getdType())
                .append("dNum", getdNum())
                .toString();
    }
}
