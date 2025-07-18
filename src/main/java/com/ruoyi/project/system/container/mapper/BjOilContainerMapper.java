package com.ruoyi.project.system.container.mapper;

import java.util.List;
import com.ruoyi.project.system.container.domain.BjOilContainer;

/**
 * 油弹集装箱状态Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-17
 */
public interface BjOilContainerMapper 
{
    /**
     * 查询油弹集装箱状态
     * 
     * @param id 油弹集装箱状态主键
     * @return 油弹集装箱状态
     */
    public BjOilContainer selectBjOilContainerById(Long id);

    /**
     * 查询油弹集装箱状态列表
     * 
     * @param bjOilContainer 油弹集装箱状态
     * @return 油弹集装箱状态集合
     */
    public List<BjOilContainer> selectBjOilContainerList(BjOilContainer bjOilContainer);

    /**
     * 新增油弹集装箱状态
     * 
     * @param bjOilContainer 油弹集装箱状态
     * @return 结果
     */
    public int insertBjOilContainer(BjOilContainer bjOilContainer);

    /**
     * 修改油弹集装箱状态
     * 
     * @param bjOilContainer 油弹集装箱状态
     * @return 结果
     */
    public int updateBjOilContainer(BjOilContainer bjOilContainer);

    /**
     * 删除油弹集装箱状态
     * 
     * @param id 油弹集装箱状态主键
     * @return 结果
     */
    public int deleteBjOilContainerById(Long id);

    /**
     * 批量删除油弹集装箱状态
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBjOilContainerByIds(String[] ids);


    public int receiveRefuelAmmoInfo(BjOilContainer bjOilContainer);


}
