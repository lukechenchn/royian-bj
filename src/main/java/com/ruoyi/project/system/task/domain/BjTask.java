package com.ruoyi.project.system.task.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * bj任务对象 bj_task
 *
 * @author ruoyi
 * @date 2025-07-15
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
                .append("taskNo", getTaskNo())
                .append("agvNo", getAgvNo())
                .append("uavNo", getUavNo())
                .append("containerNo", getContainerNo())
                .append("sign", getSign())
                .append("signNo", getSignNo())
                .append("taskStatus", getTaskStatus())
                .append("remark1", getRemark1())
                .append("remark2", getRemark2())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
