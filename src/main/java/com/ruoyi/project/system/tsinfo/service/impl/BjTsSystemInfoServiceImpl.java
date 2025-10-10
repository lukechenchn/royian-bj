package com.ruoyi.project.system.tsinfo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.tsinfo.mapper.BjTsSystemInfoMapper;
import com.ruoyi.project.system.tsinfo.domain.BjTsSystemInfo;
import com.ruoyi.project.system.tsinfo.service.IBjTsSystemInfoService;
import com.ruoyi.common.utils.text.Convert;

/**
 * ts系统Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-22
 */
@Service
public class BjTsSystemInfoServiceImpl implements IBjTsSystemInfoService 
{
    @Autowired
    private BjTsSystemInfoMapper bjTsSystemInfoMapper;

    /**
     * 查询ts系统
     * 
     * @param id ts系统主键
     * @return ts系统
     */
    @Override
    public BjTsSystemInfo selectBjTsSystemInfoById(Long id)
    {
        return bjTsSystemInfoMapper.selectBjTsSystemInfoById(id);
    }

    /**
     * 查询ts系统列表
     * 
     * @param bjTsSystemInfo ts系统
     * @return ts系统
     */
    @Override
    public List<BjTsSystemInfo> selectBjTsSystemInfoList(BjTsSystemInfo bjTsSystemInfo)
    {
        return bjTsSystemInfoMapper.selectBjTsSystemInfoList(bjTsSystemInfo);
    }

    /**
     * 新增ts系统
     * 
     * @param bjTsSystemInfo ts系统
     * @return 结果
     */
    @Override
    public int insertBjTsSystemInfo(BjTsSystemInfo bjTsSystemInfo)
    {
        return bjTsSystemInfoMapper.insertBjTsSystemInfo(bjTsSystemInfo);
    }

    /**
     * 修改ts系统
     * 
     * @param bjTsSystemInfo ts系统
     * @return 结果
     */
    @Override
    public int updateBjTsSystemInfo(BjTsSystemInfo bjTsSystemInfo)
    {
        return bjTsSystemInfoMapper.updateBjTsSystemInfo(bjTsSystemInfo);
    }

    /**
     * 批量删除ts系统
     * 
     * @param ids 需要删除的ts系统主键
     * @return 结果
     */
    @Override
    public int deleteBjTsSystemInfoByIds(String ids)
    {
        return bjTsSystemInfoMapper.deleteBjTsSystemInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除ts系统信息
     * 
     * @param id ts系统主键
     * @return 结果
     */
    @Override
    public int deleteBjTsSystemInfoById(Long id)
    {
        return bjTsSystemInfoMapper.deleteBjTsSystemInfoById(id);
    }
}
