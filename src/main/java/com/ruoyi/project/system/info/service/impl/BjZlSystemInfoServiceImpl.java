package com.ruoyi.project.system.info.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.info.mapper.BjZlSystemInfoMapper;
import com.ruoyi.project.system.info.domain.BjZlSystemInfo;
import com.ruoyi.project.system.info.service.IBjZlSystemInfoService;
import com.ruoyi.common.utils.text.Convert;

/**
 * zl系统Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-22
 */
@Service
public class BjZlSystemInfoServiceImpl implements IBjZlSystemInfoService 
{
    @Autowired
    private BjZlSystemInfoMapper bjZlSystemInfoMapper;

    /**
     * 查询zl系统
     * 
     * @param id zl系统主键
     * @return zl系统
     */
    @Override
    public BjZlSystemInfo selectBjZlSystemInfoById(Long id)
    {
        return bjZlSystemInfoMapper.selectBjZlSystemInfoById(id);
    }

    /**
     * 查询zl系统列表
     * 
     * @param bjZlSystemInfo zl系统
     * @return zl系统
     */
    @Override
    public List<BjZlSystemInfo> selectBjZlSystemInfoList(BjZlSystemInfo bjZlSystemInfo)
    {
        return bjZlSystemInfoMapper.selectBjZlSystemInfoList(bjZlSystemInfo);
    }

    /**
     * 新增zl系统
     * 
     * @param bjZlSystemInfo zl系统
     * @return 结果
     */
    @Override
    public int insertBjZlSystemInfo(BjZlSystemInfo bjZlSystemInfo)
    {
        return bjZlSystemInfoMapper.insertBjZlSystemInfo(bjZlSystemInfo);
    }

    /**
     * 修改zl系统
     * 
     * @param bjZlSystemInfo zl系统
     * @return 结果
     */
    @Override
    public int updateBjZlSystemInfo(BjZlSystemInfo bjZlSystemInfo)
    {
        return bjZlSystemInfoMapper.updateBjZlSystemInfo(bjZlSystemInfo);
    }

    /**
     * 批量删除zl系统
     * 
     * @param ids 需要删除的zl系统主键
     * @return 结果
     */
    @Override
    public int deleteBjZlSystemInfoByIds(String ids)
    {
        return bjZlSystemInfoMapper.deleteBjZlSystemInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除zl系统信息
     * 
     * @param id zl系统主键
     * @return 结果
     */
    @Override
    public int deleteBjZlSystemInfoById(Long id)
    {
        return bjZlSystemInfoMapper.deleteBjZlSystemInfoById(id);
    }
}
