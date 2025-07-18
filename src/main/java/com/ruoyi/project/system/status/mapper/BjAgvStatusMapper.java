package com.ruoyi.project.system.status.mapper;

import java.util.List;
import com.ruoyi.project.system.status.domain.BjAgvStatus;

/**
 * AGV状态Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-16
 */
public interface BjAgvStatusMapper 
{
    /**
     * 查询AGV状态
     * 
     * @param id AGV状态主键
     * @return AGV状态
     */
    public BjAgvStatus selectBjAgvStatusById(Long id);

    /**
     * 查询AGV状态列表
     * 
     * @param bjAgvStatus AGV状态
     * @return AGV状态集合
     */
    public List<BjAgvStatus> selectBjAgvStatusList(BjAgvStatus bjAgvStatus);

    /**
     * 新增AGV状态
     * 
     * @param bjAgvStatus AGV状态
     * @return 结果
     */
    public int insertBjAgvStatus(BjAgvStatus bjAgvStatus);

    /**
     * 修改AGV状态
     * 
     * @param bjAgvStatus AGV状态
     * @return 结果
     */
    public int updateBjAgvStatus(BjAgvStatus bjAgvStatus);

    /**
     * 删除AGV状态
     * 
     * @param id AGV状态主键
     * @return 结果
     */
    public int deleteBjAgvStatusById(Long id);

    /**
     * 批量删除AGV状态
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBjAgvStatusByIds(String[] ids);


    public List<BjAgvStatus> selectBjAgvStatusListFeed(BjAgvStatus bjAgvStatus);




    public int updateBjAgvWithOilStatus(BjAgvStatus bjAgvStatus);
}
