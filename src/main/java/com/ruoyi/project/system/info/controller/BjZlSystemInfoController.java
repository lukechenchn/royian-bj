package com.ruoyi.project.system.info.controller;

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
import com.ruoyi.project.system.info.domain.BjZlSystemInfo;
import com.ruoyi.project.system.info.service.IBjZlSystemInfoService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * zl系统Controller
 * 
 * @author ruoyi
 * @date 2025-08-22
 */
@Controller
@RequestMapping("/system/info")
public class BjZlSystemInfoController extends BaseController
{
    private String prefix = "system/info";

    @Autowired
    private IBjZlSystemInfoService bjZlSystemInfoService;

    @RequiresPermissions("system:info:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

    /**
     * 查询zl系统列表
     */
    @RequiresPermissions("system:info:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BjZlSystemInfo bjZlSystemInfo)
    {
        startPage();
        List<BjZlSystemInfo> list = bjZlSystemInfoService.selectBjZlSystemInfoList(bjZlSystemInfo);
        return getDataTable(list);
    }

    /**
     * 导出zl系统列表
     */
    @RequiresPermissions("system:info:export")
    @Log(title = "zl系统", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BjZlSystemInfo bjZlSystemInfo)
    {
        List<BjZlSystemInfo> list = bjZlSystemInfoService.selectBjZlSystemInfoList(bjZlSystemInfo);
        ExcelUtil<BjZlSystemInfo> util = new ExcelUtil<BjZlSystemInfo>(BjZlSystemInfo.class);
        return util.exportExcel(list, "zl系统数据");
    }

    /**
     * 新增zl系统
     */
    @RequiresPermissions("system:info:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存zl系统
     */
    @RequiresPermissions("system:info:add")
    @Log(title = "zl系统", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BjZlSystemInfo bjZlSystemInfo)
    {
        return toAjax(bjZlSystemInfoService.insertBjZlSystemInfo(bjZlSystemInfo));
    }

    /**
     * 修改zl系统
     */
    @RequiresPermissions("system:info:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BjZlSystemInfo bjZlSystemInfo = bjZlSystemInfoService.selectBjZlSystemInfoById(id);
        mmap.put("bjZlSystemInfo", bjZlSystemInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存zl系统
     */
    @RequiresPermissions("system:info:edit")
    @Log(title = "zl系统", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BjZlSystemInfo bjZlSystemInfo)
    {
        return toAjax(bjZlSystemInfoService.updateBjZlSystemInfo(bjZlSystemInfo));
    }

    /**
     * 删除zl系统
     */
    @RequiresPermissions("system:info:remove")
    @Log(title = "zl系统", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(bjZlSystemInfoService.deleteBjZlSystemInfoByIds(ids));
    }
}
