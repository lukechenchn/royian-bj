package com.ruoyi.project.system.tsinfo.controller;

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
import com.ruoyi.project.system.tsinfo.domain.BjTsSystemInfo;
import com.ruoyi.project.system.tsinfo.service.IBjTsSystemInfoService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * ts系统Controller
 * 
 * @author ruoyi
 * @date 2025-08-22
 */
@Controller
@RequestMapping("/system/tsinfo")
public class BjTsSystemInfoController extends BaseController
{
    private String prefix = "system/tsinfo";

    @Autowired
    private IBjTsSystemInfoService bjTsSystemInfoService;

    @RequiresPermissions("system:tsinfo:view")
    @GetMapping()
    public String tsinfo()
    {
        return prefix + "/tsinfo";
    }

    /**
     * 查询ts系统列表
     */
    @RequiresPermissions("system:tsinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BjTsSystemInfo bjTsSystemInfo)
    {
        startPage();
        List<BjTsSystemInfo> list = bjTsSystemInfoService.selectBjTsSystemInfoList(bjTsSystemInfo);
        return getDataTable(list);
    }

    /**
     * 导出ts系统列表
     */
    @RequiresPermissions("system:tsinfo:export")
    @Log(title = "ts系统", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BjTsSystemInfo bjTsSystemInfo)
    {
        List<BjTsSystemInfo> list = bjTsSystemInfoService.selectBjTsSystemInfoList(bjTsSystemInfo);
        ExcelUtil<BjTsSystemInfo> util = new ExcelUtil<BjTsSystemInfo>(BjTsSystemInfo.class);
        return util.exportExcel(list, "ts系统数据");
    }

    /**
     * 新增ts系统
     */
    @RequiresPermissions("system:tsinfo:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存ts系统
     */
    @RequiresPermissions("system:tsinfo:add")
    @Log(title = "ts系统", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BjTsSystemInfo bjTsSystemInfo)
    {
        return toAjax(bjTsSystemInfoService.insertBjTsSystemInfo(bjTsSystemInfo));
    }

    /**
     * 修改ts系统
     */
    @RequiresPermissions("system:tsinfo:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BjTsSystemInfo bjTsSystemInfo = bjTsSystemInfoService.selectBjTsSystemInfoById(id);
        mmap.put("bjTsSystemInfo", bjTsSystemInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存ts系统
     */
    @RequiresPermissions("system:tsinfo:edit")
    @Log(title = "ts系统", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BjTsSystemInfo bjTsSystemInfo)
    {
        return toAjax(bjTsSystemInfoService.updateBjTsSystemInfo(bjTsSystemInfo));
    }

    /**
     * 删除ts系统
     */
    @RequiresPermissions("system:tsinfo:remove")
    @Log(title = "ts系统", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(bjTsSystemInfoService.deleteBjTsSystemInfoByIds(ids));
    }
}
