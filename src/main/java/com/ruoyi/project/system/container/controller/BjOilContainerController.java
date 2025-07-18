package com.ruoyi.project.system.container.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.system.container.domain.BjOilContainer;
import com.ruoyi.project.system.container.service.IBjOilContainerService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 油弹集装箱状态Controller
 * 
 * @author ruoyi
 * @date 2025-07-17
 */
@Controller
@RequestMapping("/system/container")
public class BjOilContainerController extends BaseController
{
    private String prefix = "system/container";

    @Autowired
    private IBjOilContainerService bjOilContainerService;

    @RequiresPermissions("system:container:view")
    @GetMapping()
    public String container()
    {
        return prefix + "/container";
    }

    /**
     * 查询油弹集装箱状态列表
     */
    @RequiresPermissions("system:container:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BjOilContainer bjOilContainer)
    {
        startPage();
        List<BjOilContainer> list = bjOilContainerService.selectBjOilContainerList(bjOilContainer);
        return getDataTable(list);
    }

    /**
     * 导出油弹集装箱状态列表
     */
    @RequiresPermissions("system:container:export")
    @Log(title = "油弹集装箱状态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BjOilContainer bjOilContainer)
    {
        List<BjOilContainer> list = bjOilContainerService.selectBjOilContainerList(bjOilContainer);
        ExcelUtil<BjOilContainer> util = new ExcelUtil<BjOilContainer>(BjOilContainer.class);
        return util.exportExcel(list, "油弹集装箱状态数据");
    }

    /**
     * 新增油弹集装箱状态
     */
    @RequiresPermissions("system:container:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存油弹集装箱状态
     */
    @RequiresPermissions("system:container:add")
    @Log(title = "油弹集装箱状态", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BjOilContainer bjOilContainer)
    {
        return toAjax(bjOilContainerService.insertBjOilContainer(bjOilContainer));
    }

    /**
     * 修改油弹集装箱状态
     */
    @RequiresPermissions("system:container:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BjOilContainer bjOilContainer = bjOilContainerService.selectBjOilContainerById(id);
        mmap.put("bjOilContainer", bjOilContainer);
        return prefix + "/edit";
    }

    /**
     * 修改保存油弹集装箱状态
     */
    @RequiresPermissions("system:container:edit")
    @Log(title = "油弹集装箱状态", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BjOilContainer bjOilContainer)
    {
        return toAjax(bjOilContainerService.updateBjOilContainer(bjOilContainer));
    }

    /**
     * 删除油弹集装箱状态
     */
    @RequiresPermissions("system:container:remove")
    @Log(title = "油弹集装箱状态", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(bjOilContainerService.deleteBjOilContainerByIds(ids));
    }
}
