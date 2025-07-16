package com.ruoyi.project.system.records.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 任务执行记录对象 bj_task_records
 * 
 * @author ruoyi
 * @date 2025-07-16
 */
public class BjTaskRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 任务号 */
    @Excel(name = "任务号")
    private String taskNo;

    /** 任务信号 */
    @Excel(name = "任务信号")
    private String sign;

    /** 短任务号 */
    @Excel(name = "短任务号")
    private String signNo;

    /** 执行记录 */
    @Excel(name = "执行记录")
    private String record;

    /** 备注1 */
    @Excel(name = "备注1")
    private String remark1;

    /** 备注2 */
    @Excel(name = "备注2")
    private String remark2;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Long isDeleted;

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

    public void setRecord(String record) 
    {
        this.record = record;
    }

    public String getRecord() 
    {
        return record;
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

    public void setIsDeleted(Long isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskNo", getTaskNo())
            .append("sign", getSign())
            .append("signNo", getSignNo())
            .append("record", getRecord())
            .append("remark1", getRemark1())
            .append("remark2", getRemark2())
            .append("isDeleted", getIsDeleted())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
