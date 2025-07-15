package com.ruoyi.project.system.task.service;

import java.util.List;
import com.ruoyi.project.system.task.domain.BjTask;

/**
 * bj任务Service接口
 *
 * @author ruoyi
 * @date 2025-07-15
 */
public interface IBjTaskService
{
    /**
     * 查询bj任务
     *
     * @param id bj任务主键
     * @return bj任务
     */
    public BjTask selectBjTaskById(Long id);

    /**
     * 查询bj任务列表
     *
     * @param bjTask bj任务
     * @return bj任务集合
     */
    public List<BjTask> selectBjTaskList(BjTask bjTask);

    /**
     * 新增bj任务
     *
     * @param bjTask bj任务
     * @return 结果
     */
    public int insertBjTask(BjTask bjTask);

    /**
     * 修改bj任务
     *
     * @param bjTask bj任务
     * @return 结果
     */
    public int updateBjTask(BjTask bjTask);

    /**
     * 批量删除bj任务
     *
     * @param ids 需要删除的bj任务主键集合
     * @return 结果
     */
    public int deleteBjTaskByIds(String ids);

    /**
     * 删除bj任务信息
     *
     * @param id bj任务主键
     * @return 结果
     */
    public int deleteBjTaskById(Long id);
}
