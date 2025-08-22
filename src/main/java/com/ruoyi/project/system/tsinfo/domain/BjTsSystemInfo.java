package com.ruoyi.project.system.tsinfo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * ts系统对象 bj_ts_system_info
 * 
 * @author ruoyi
 * @date 2025-08-22
 */
public class BjTsSystemInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** TS系统就绪状态 */
    @Excel(name = "TS系统就绪状态")
    private String status;

    /** 可TS次数 */
    @Excel(name = "可TS次数")
    private Integer num;

    /** 储能电池电量 */
    @Excel(name = "储能电池电量")
    private String battery;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setNum(Integer num) 
    {
        this.num = num;
    }

    public Integer getNum() 
    {
        return num;
    }

    public void setBattery(String battery) 
    {
        this.battery = battery;
    }

    public String getBattery() 
    {
        return battery;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("status", getStatus())
            .append("num", getNum())
            .append("battery", getBattery())
            .toString();
    }
}
