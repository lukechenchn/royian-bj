package com.ruoyi.project.system.container.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.container.mapper.BjOilContainerMapper;
import com.ruoyi.project.system.container.domain.BjOilContainer;
import com.ruoyi.project.system.container.service.IBjOilContainerService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 油弹集装箱状态Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-17
 */
@Service
public class BjOilContainerServiceImpl implements IBjOilContainerService 
{
    @Autowired
    private BjOilContainerMapper bjOilContainerMapper;

    /**
     * 查询油弹集装箱状态
     * 
     * @param id 油弹集装箱状态主键
     * @return 油弹集装箱状态
     */
    @Override
    public BjOilContainer selectBjOilContainerById(Long id)
    {
        return bjOilContainerMapper.selectBjOilContainerById(id);
    }

    /**
     * 查询油弹集装箱状态列表
     * 
     * @param bjOilContainer 油弹集装箱状态
     * @return 油弹集装箱状态
     */
    @Override
    public List<BjOilContainer> selectBjOilContainerList(BjOilContainer bjOilContainer)
    {
        return bjOilContainerMapper.selectBjOilContainerList(bjOilContainer);
    }

    /**
     * 新增油弹集装箱状态
     * 
     * @param bjOilContainer 油弹集装箱状态
     * @return 结果
     */
    @Override
    public int insertBjOilContainer(BjOilContainer bjOilContainer)
    {
        return bjOilContainerMapper.insertBjOilContainer(bjOilContainer);
    }

    /**
     * 修改油弹集装箱状态
     * 
     * @param bjOilContainer 油弹集装箱状态
     * @return 结果
     */
    @Override
    public int updateBjOilContainer(BjOilContainer bjOilContainer)
    {
        return bjOilContainerMapper.updateBjOilContainer(bjOilContainer);
    }

    /**
     * 批量删除油弹集装箱状态
     * 
     * @param ids 需要删除的油弹集装箱状态主键
     * @return 结果
     */
    @Override
    public int deleteBjOilContainerByIds(String ids)
    {
        return bjOilContainerMapper.deleteBjOilContainerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除油弹集装箱状态信息
     * 
     * @param id 油弹集装箱状态主键
     * @return 结果
     */
    @Override
    public int deleteBjOilContainerById(Long id)
    {
        return bjOilContainerMapper.deleteBjOilContainerById(id);
    }

    public int receiveRefuelAmmoInfo(BjOilContainer bjOilContainer)
    {
        return bjOilContainerMapper.receiveRefuelAmmoInfo(bjOilContainer);
    }
}
