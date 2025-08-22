package com.ruoyi.project.system.info.service;

import java.util.List;
import com.ruoyi.project.system.info.domain.BjZlSystemInfo;

/**
 * zl系统Service接口
 * 
 * @author ruoyi
 * @date 2025-08-22
 */
public interface IBjZlSystemInfoService 
{
    /**
     * 查询zl系统
     * 
     * @param id zl系统主键
     * @return zl系统
     */
    public BjZlSystemInfo selectBjZlSystemInfoById(Long id);

    /**
     * 查询zl系统列表
     * 
     * @param bjZlSystemInfo zl系统
     * @return zl系统集合
     */
    public List<BjZlSystemInfo> selectBjZlSystemInfoList(BjZlSystemInfo bjZlSystemInfo);

    /**
     * 新增zl系统
     * 
     * @param bjZlSystemInfo zl系统
     * @return 结果
     */
    public int insertBjZlSystemInfo(BjZlSystemInfo bjZlSystemInfo);

    /**
     * 修改zl系统
     * 
     * @param bjZlSystemInfo zl系统
     * @return 结果
     */
    public int updateBjZlSystemInfo(BjZlSystemInfo bjZlSystemInfo);

    /**
     * 批量删除zl系统
     * 
     * @param ids 需要删除的zl系统主键集合
     * @return 结果
     */
    public int deleteBjZlSystemInfoByIds(String ids);

    /**
     * 删除zl系统信息
     * 
     * @param id zl系统主键
     * @return 结果
     */
    public int deleteBjZlSystemInfoById(Long id);
}
