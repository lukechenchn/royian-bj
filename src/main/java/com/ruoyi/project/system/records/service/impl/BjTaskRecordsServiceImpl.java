package com.ruoyi.project.system.records.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.records.mapper.BjTaskRecordsMapper;
import com.ruoyi.project.system.records.domain.BjTaskRecords;
import com.ruoyi.project.system.records.service.IBjTaskRecordsService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 任务执行记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-16
 */
@Service
public class BjTaskRecordsServiceImpl implements IBjTaskRecordsService 
{
    @Autowired
    private BjTaskRecordsMapper bjTaskRecordsMapper;

    /**
     * 查询任务执行记录
     * 
     * @param id 任务执行记录主键
     * @return 任务执行记录
     */
    @Override
    public BjTaskRecords selectBjTaskRecordsById(Long id)
    {
        return bjTaskRecordsMapper.selectBjTaskRecordsById(id);
    }

    /**
     * 查询任务执行记录列表
     * 
     * @param bjTaskRecords 任务执行记录
     * @return 任务执行记录
     */
    @Override
    public List<BjTaskRecords> selectBjTaskRecordsList(BjTaskRecords bjTaskRecords)
    {
        return bjTaskRecordsMapper.selectBjTaskRecordsList(bjTaskRecords);
    }

    /**
     * 新增任务执行记录
     * 
     * @param bjTaskRecords 任务执行记录
     * @return 结果
     */
    @Override
    public int insertBjTaskRecords(BjTaskRecords bjTaskRecords)
    {
        bjTaskRecords.setCreateTime(DateUtils.getNowDate());
        return bjTaskRecordsMapper.insertBjTaskRecords(bjTaskRecords);
    }

    /**
     * 修改任务执行记录
     * 
     * @param bjTaskRecords 任务执行记录
     * @return 结果
     */
    @Override
    public int updateBjTaskRecords(BjTaskRecords bjTaskRecords)
    {
        bjTaskRecords.setUpdateTime(DateUtils.getNowDate());
        return bjTaskRecordsMapper.updateBjTaskRecords(bjTaskRecords);
    }

    /**
     * 批量删除任务执行记录
     * 
     * @param ids 需要删除的任务执行记录主键
     * @return 结果
     */
    @Override
    public int deleteBjTaskRecordsByIds(String ids)
    {
        return bjTaskRecordsMapper.deleteBjTaskRecordsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除任务执行记录信息
     * 
     * @param id 任务执行记录主键
     * @return 结果
     */
    @Override
    public int deleteBjTaskRecordsById(Long id)
    {
        return bjTaskRecordsMapper.deleteBjTaskRecordsById(id);
    }
}
