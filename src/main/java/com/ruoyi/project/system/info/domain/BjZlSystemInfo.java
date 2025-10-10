package com.ruoyi.project.system.info.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * zl系统对象 bj_zl_system_info
 * 
 * @author ruoyi
 * @date 2025-08-22
 */
public class BjZlSystemInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** ZL系统就绪状态 */
    @Excel(name = "ZL系统就绪状态")
    private String status;

    /** ZL系统WRJ质量 */
    @Excel(name = "ZL系统WRJ质量")
    private Long weight;

    /** ZL系统WRJ落地速度 */
    @Excel(name = "ZL系统WRJ落地速度")
    private Long speed;

    /** ZL系统安全使用次数 */
    @Excel(name = "ZL系统安全使用次数")
    private Long num;

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

    public void setWeight(Long weight) 
    {
        this.weight = weight;
    }

    public Long getWeight() 
    {
        return weight;
    }

    public void setSpeed(Long speed) 
    {
        this.speed = speed;
    }

    public Long getSpeed() 
    {
        return speed;
    }

    public void setNum(Long num) 
    {
        this.num = num;
    }

    public Long getNum() 
    {
        return num;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("status", getStatus())
            .append("weight", getWeight())
            .append("speed", getSpeed())
            .append("num", getNum())
            .toString();
    }
}
