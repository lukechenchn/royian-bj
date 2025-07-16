package com.ruoyi.project.system.records.controller;

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
import com.ruoyi.project.system.records.domain.BjTaskRecords;
import com.ruoyi.project.system.records.service.IBjTaskRecordsService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 任务执行记录Controller
 * 
 * @author ruoyi
 * @date 2025-07-16
 */
@Controller
@RequestMapping("/system/records")
public class BjTaskRecordsController extends BaseController
{
    private String prefix = "system/records";

    @Autowired
    private IBjTaskRecordsService bjTaskRecordsService;

    @RequiresPermissions("system:records:view")
    @GetMapping()
    public String records()
    {
        return prefix + "/records";
    }

    /**
     * 查询任务执行记录列表
     */
    @RequiresPermissions("system:records:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BjTaskRecords bjTaskRecords)
    {
        startPage();
        List<BjTaskRecords> list = bjTaskRecordsService.selectBjTaskRecordsList(bjTaskRecords);
        return getDataTable(list);
    }

    /**
     * 导出任务执行记录列表
     */
    @RequiresPermissions("system:records:export")
    @Log(title = "任务执行记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BjTaskRecords bjTaskRecords)
    {
        List<BjTaskRecords> list = bjTaskRecordsService.selectBjTaskRecordsList(bjTaskRecords);
        ExcelUtil<BjTaskRecords> util = new ExcelUtil<BjTaskRecords>(BjTaskRecords.class);
        return util.exportExcel(list, "任务执行记录数据");
    }

    /**
     * 新增任务执行记录
     */
    @RequiresPermissions("system:records:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存任务执行记录
     */
    @RequiresPermissions("system:records:add")
    @Log(title = "任务执行记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BjTaskRecords bjTaskRecords)
    {
        return toAjax(bjTaskRecordsService.insertBjTaskRecords(bjTaskRecords));
    }

    /**
     * 修改任务执行记录
     */
    @RequiresPermissions("system:records:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BjTaskRecords bjTaskRecords = bjTaskRecordsService.selectBjTaskRecordsById(id);
        mmap.put("bjTaskRecords", bjTaskRecords);
        return prefix + "/edit";
    }

    /**
     * 修改保存任务执行记录
     */
    @RequiresPermissions("system:records:edit")
    @Log(title = "任务执行记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BjTaskRecords bjTaskRecords)
    {
        return toAjax(bjTaskRecordsService.updateBjTaskRecords(bjTaskRecords));
    }

    /**
     * 删除任务执行记录
     */
    @RequiresPermissions("system:records:remove")
    @Log(title = "任务执行记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(bjTaskRecordsService.deleteBjTaskRecordsByIds(ids));
    }
}
