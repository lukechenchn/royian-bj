package com.ruoyi.project.system.status.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.status.mapper.BjAgvStatusMapper;
import com.ruoyi.project.system.status.domain.BjAgvStatus;
import com.ruoyi.project.system.status.service.IBjAgvStatusService;
import com.ruoyi.common.utils.text.Convert;

/**
 * AGV状态Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-16
 */
@Service
public class BjAgvStatusServiceImpl implements IBjAgvStatusService 
{
    @Autowired
    private BjAgvStatusMapper bjAgvStatusMapper;

    /**
     * 查询AGV状态
     * 
     * @param id AGV状态主键
     * @return AGV状态
     */
    @Override
    public BjAgvStatus selectBjAgvStatusById(Long id)
    {
        return bjAgvStatusMapper.selectBjAgvStatusById(id);
    }

    /**
     * 查询AGV状态列表
     * 
     * @param bjAgvStatus AGV状态
     * @return AGV状态
     */
    @Override
    public List<BjAgvStatus> selectBjAgvStatusList(BjAgvStatus bjAgvStatus)
    {
        return bjAgvStatusMapper.selectBjAgvStatusList(bjAgvStatus);
    }

    /**
     * 新增AGV状态
     * 
     * @param bjAgvStatus AGV状态
     * @return 结果
     */
    @Override
    public int insertBjAgvStatus(BjAgvStatus bjAgvStatus)
    {
        return bjAgvStatusMapper.insertBjAgvStatus(bjAgvStatus);
    }

    /**
     * 修改AGV状态
     * 
     * @param bjAgvStatus AGV状态
     * @return 结果
     */
    @Override
    public int updateBjAgvStatus(BjAgvStatus bjAgvStatus)
    {
        return bjAgvStatusMapper.updateBjAgvStatus(bjAgvStatus);
    }

    /**
     * 批量删除AGV状态
     * 
     * @param ids 需要删除的AGV状态主键
     * @return 结果
     */
    @Override
    public int deleteBjAgvStatusByIds(String ids)
    {
        return bjAgvStatusMapper.deleteBjAgvStatusByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除AGV状态信息
     * 
     * @param id AGV状态主键
     * @return 结果
     */
    @Override
    public int deleteBjAgvStatusById(Long id)
    {
        return bjAgvStatusMapper.deleteBjAgvStatusById(id);
    }
}
