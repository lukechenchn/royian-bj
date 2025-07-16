package com.ruoyi.project.system.status.controller;

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
import com.ruoyi.project.system.status.domain.BjAgvStatus;
import com.ruoyi.project.system.status.service.IBjAgvStatusService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * AGV状态Controller
 * 
 * @author ruoyi
 * @date 2025-07-16
 */
@Controller
@RequestMapping("/system/status")
public class BjAgvStatusController extends BaseController
{
    private String prefix = "system/status";

    @Autowired
    private IBjAgvStatusService bjAgvStatusService;

    @RequiresPermissions("system:status:view")
    @GetMapping()
    public String status()
    {
        return prefix + "/status";
    }

    /**
     * 查询AGV状态列表
     */
    @RequiresPermissions("system:status:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BjAgvStatus bjAgvStatus)
    {
        startPage();
        List<BjAgvStatus> list = bjAgvStatusService.selectBjAgvStatusList(bjAgvStatus);
        return getDataTable(list);
    }

    /**
     * 导出AGV状态列表
     */
    @RequiresPermissions("system:status:export")
    @Log(title = "AGV状态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BjAgvStatus bjAgvStatus)
    {
        List<BjAgvStatus> list = bjAgvStatusService.selectBjAgvStatusList(bjAgvStatus);
        ExcelUtil<BjAgvStatus> util = new ExcelUtil<BjAgvStatus>(BjAgvStatus.class);
        return util.exportExcel(list, "AGV状态数据");
    }

    /**
     * 新增AGV状态
     */
    @RequiresPermissions("system:status:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存AGV状态
     */
    @RequiresPermissions("system:status:add")
    @Log(title = "AGV状态", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BjAgvStatus bjAgvStatus)
    {
        return toAjax(bjAgvStatusService.insertBjAgvStatus(bjAgvStatus));
    }

    /**
     * 修改AGV状态
     */
    @RequiresPermissions("system:status:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BjAgvStatus bjAgvStatus = bjAgvStatusService.selectBjAgvStatusById(id);
        mmap.put("bjAgvStatus", bjAgvStatus);
        return prefix + "/edit";
    }

    /**
     * 修改保存AGV状态
     */
    @RequiresPermissions("system:status:edit")
    @Log(title = "AGV状态", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BjAgvStatus bjAgvStatus)
    {
        return toAjax(bjAgvStatusService.updateBjAgvStatus(bjAgvStatus));
    }

    /**
     * 删除AGV状态
     */
    @RequiresPermissions("system:status:remove")
    @Log(title = "AGV状态", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(bjAgvStatusService.deleteBjAgvStatusByIds(ids));
    }
}
