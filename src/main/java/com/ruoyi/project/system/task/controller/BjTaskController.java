package com.ruoyi.project.system.task.controller;

import java.util.List;

import com.ruoyi.project.system.task.service.impl.BjTaskServiceImpl;
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
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.service.IBjTaskService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * bj任务Controller
 * 
 * @author ruoyi
 * @date 2025-07-14
 */
@Controller
@RequestMapping("/system/task")
public class BjTaskController extends BaseController
{
    private String prefix = "system/task";

    @Autowired
    private IBjTaskService bjTaskService;






    @RequiresPermissions("system:task:view")
    @GetMapping()
    public String task()
    {
        return prefix + "/task";
    }

    /**
     * 查询bj任务列表
     */
    @RequiresPermissions("system:task:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BjTask bjTask)
    {
        startPage();
        List<BjTask> list = bjTaskService.selectBjTaskList(bjTask);
        return getDataTable(list);
    }

    /**
     * 导出bj任务列表
     */
    @RequiresPermissions("system:task:export")
    @Log(title = "bj任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BjTask bjTask)
    {
        List<BjTask> list = bjTaskService.selectBjTaskList(bjTask);
        ExcelUtil<BjTask> util = new ExcelUtil<BjTask>(BjTask.class);
        return util.exportExcel(list, "bj任务数据");
    }

    /**
     * 新增bj任务
     */
    @RequiresPermissions("system:task:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存bj任务
     */
    @RequiresPermissions("system:task:add")
    @Log(title = "bj任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BjTask bjTask)
    {
        return toAjax(bjTaskService.insertBjTask(bjTask));
    }

    /**
     * 修改bj任务
     */
    @RequiresPermissions("system:task:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BjTask bjTask = bjTaskService.selectBjTaskById(id);
        mmap.put("bjTask", bjTask);
        return prefix + "/edit";
    }

    /**
     * 修改保存bj任务
     */
    @RequiresPermissions("system:task:edit")
    @Log(title = "bj任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BjTask bjTask)
    {
        return toAjax(bjTaskService.updateBjTask(bjTask));
    }

    /**
     * 删除bj任务
     */
    @RequiresPermissions("system:task:remove")
    @Log(title = "bj任务", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(bjTaskService.deleteBjTaskByIds(ids));
    }
}
