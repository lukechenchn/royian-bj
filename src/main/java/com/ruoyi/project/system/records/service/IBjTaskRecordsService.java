package com.ruoyi.project.system.records.service;

import java.util.List;
import com.ruoyi.project.system.records.domain.BjTaskRecords;

/**
 * 任务执行记录Service接口
 * 
 * @author ruoyi
 * @date 2025-07-16
 */
public interface IBjTaskRecordsService 
{
    /**
     * 查询任务执行记录
     * 
     * @param id 任务执行记录主键
     * @return 任务执行记录
     */
    public BjTaskRecords selectBjTaskRecordsById(Long id);

    /**
     * 查询任务执行记录列表
     * 
     * @param bjTaskRecords 任务执行记录
     * @return 任务执行记录集合
     */
    public List<BjTaskRecords> selectBjTaskRecordsList(BjTaskRecords bjTaskRecords);

    /**
     * 新增任务执行记录
     * 
     * @param bjTaskRecords 任务执行记录
     * @return 结果
     */
    public int insertBjTaskRecords(BjTaskRecords bjTaskRecords);

    /**
     * 修改任务执行记录
     * 
     * @param bjTaskRecords 任务执行记录
     * @return 结果
     */
    public int updateBjTaskRecords(BjTaskRecords bjTaskRecords);

    /**
     * 批量删除任务执行记录
     * 
     * @param ids 需要删除的任务执行记录主键集合
     * @return 结果
     */
    public int deleteBjTaskRecordsByIds(String ids);

    /**
     * 删除任务执行记录信息
     * 
     * @param id 任务执行记录主键
     * @return 结果
     */
    public int deleteBjTaskRecordsById(Long id);
}
