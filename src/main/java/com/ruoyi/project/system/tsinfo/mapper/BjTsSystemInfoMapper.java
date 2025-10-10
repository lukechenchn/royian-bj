package com.ruoyi.project.system.tsinfo.mapper;

import java.util.List;
import com.ruoyi.project.system.tsinfo.domain.BjTsSystemInfo;

/**
 * ts系统Mapper接口
 * 
 * @author ruoyi
 * @date 2025-08-22
 */
public interface BjTsSystemInfoMapper 
{
    /**
     * 查询ts系统
     * 
     * @param id ts系统主键
     * @return ts系统
     */
    public BjTsSystemInfo selectBjTsSystemInfoById(Long id);

    /**
     * 查询ts系统列表
     * 
     * @param bjTsSystemInfo ts系统
     * @return ts系统集合
     */
    public List<BjTsSystemInfo> selectBjTsSystemInfoList(BjTsSystemInfo bjTsSystemInfo);

    /**
     * 新增ts系统
     * 
     * @param bjTsSystemInfo ts系统
     * @return 结果
     */
    public int insertBjTsSystemInfo(BjTsSystemInfo bjTsSystemInfo);

    /**
     * 修改ts系统
     * 
     * @param bjTsSystemInfo ts系统
     * @return 结果
     */
    public int updateBjTsSystemInfo(BjTsSystemInfo bjTsSystemInfo);

    /**
     * 删除ts系统
     * 
     * @param id ts系统主键
     * @return 结果
     */
    public int deleteBjTsSystemInfoById(Long id);

    /**
     * 批量删除ts系统
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBjTsSystemInfoByIds(String[] ids);
}
