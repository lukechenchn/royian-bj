package com.ruoyi.project.system.container.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 油弹集装箱状态对象 bj_oil_container
 * 
 * @author ruoyi
 * @date 2025-07-17
 */
public class BjOilContainer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 编号 */
    @Excel(name = "编号")
    private Long no;

    /** 油弹集装箱开关状态 */
    @Excel(name = "油弹集装箱开关状态")
    private String oilContainer;

    /** 油种类1 */
    @Excel(name = "油种类1")
    private String oilType1;

    /** 油量1 */
    @Excel(name = "油量1")
    private String oilQuantity1;

    /** 油种类2 */
    @Excel(name = "油种类2")
    private String oilType2;

    /** 油量2 */
    @Excel(name = "油量2")
    private String oilQuantity2;

    /** 油种类3 */
    @Excel(name = "油种类3")
    private String oilType3;

    /** 油量3 */
    @Excel(name = "油量3")
    private String oilQuantity3;

    /** 弹种类1 */
    @Excel(name = "弹种类1")
    private String model1;

    /** 弹量1 */
    @Excel(name = "弹量1")
    private String quantity1;

    /** 弹种类2 */
    @Excel(name = "弹种类2")
    private String model2;

    /** 弹量2 */
    @Excel(name = "弹量2")
    private String quantuty2;

    /** 弹种类3 */
    @Excel(name = "弹种类3")
    private String model3;

    /** 弹量3 */
    @Excel(name = "弹量3")
    private String quantity3;

    /** 弹种类4 */
    @Excel(name = "弹种类4")
    private String model4;

    /** 弹量4 */
    @Excel(name = "弹量4")
    private String quantity4;

    /** 弹种类5 */
    @Excel(name = "弹种类5")
    private String model5;

    /** 弹量5 */
    @Excel(name = "弹量5")
    private String quantity5;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setNo(Long no) 
    {
        this.no = no;
    }

    public Long getNo() 
    {
        return no;
    }

    public void setOilContainer(String oilContainer) 
    {
        this.oilContainer = oilContainer;
    }

    public String getOilContainer() 
    {
        return oilContainer;
    }

    public void setOilType1(String oilType1) 
    {
        this.oilType1 = oilType1;
    }

    public String getOilType1() 
    {
        return oilType1;
    }

    public void setOilQuantity1(String oilQuantity1) 
    {
        this.oilQuantity1 = oilQuantity1;
    }

    public String getOilQuantity1() 
    {
        return oilQuantity1;
    }

    public void setOilType2(String oilType2) 
    {
        this.oilType2 = oilType2;
    }

    public String getOilType2() 
    {
        return oilType2;
    }

    public void setOilQuantity2(String oilQuantity2) 
    {
        this.oilQuantity2 = oilQuantity2;
    }

    public String getOilQuantity2() 
    {
        return oilQuantity2;
    }

    public void setOilType3(String oilType3) 
    {
        this.oilType3 = oilType3;
    }

    public String getOilType3() 
    {
        return oilType3;
    }

    public void setOilQuantity3(String oilQuantity3) 
    {
        this.oilQuantity3 = oilQuantity3;
    }

    public String getOilQuantity3() 
    {
        return oilQuantity3;
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

    public void setQuantuty2(String quantuty2) 
    {
        this.quantuty2 = quantuty2;
    }

    public String getQuantuty2() 
    {
        return quantuty2;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("no", getNo())
            .append("oilContainer", getOilContainer())
            .append("oilType1", getOilType1())
            .append("oilQuantity1", getOilQuantity1())
            .append("oilType2", getOilType2())
            .append("oilQuantity2", getOilQuantity2())
            .append("oilType3", getOilType3())
            .append("oilQuantity3", getOilQuantity3())
            .append("model1", getModel1())
            .append("quantity1", getQuantity1())
            .append("model2", getModel2())
            .append("quantuty2", getQuantuty2())
            .append("model3", getModel3())
            .append("quantity3", getQuantity3())
            .append("model4", getModel4())
            .append("quantity4", getQuantity4())
            .append("model5", getModel5())
            .append("quantity5", getQuantity5())
            .toString();
    }
}
