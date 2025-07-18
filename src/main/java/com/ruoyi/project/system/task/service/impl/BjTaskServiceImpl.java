package com.ruoyi.project.system.task.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.task.mapper.BjTaskMapper;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.service.IBjTaskService;
import com.ruoyi.common.utils.text.Convert;

/**
 * bj任务Service业务层处理
 *
 * @author ruoyi
 * @date 2025-07-15
 */
@Service
public class BjTaskServiceImpl implements IBjTaskService
{
    @Autowired
    private BjTaskMapper bjTaskMapper;


    public BjTask test(Long id)
    {
        return bjTaskMapper.selectBjTaskById(id);
    }


    /**
     * 查询bj任务
     *
     * @param id bj任务主键
     * @return bj任务
     */
    @Override
    public BjTask selectBjTaskById(Long id)
    {
        return bjTaskMapper.selectBjTaskById(id);
    }

    /**
     * 查询bj任务列表
     *
     * @param bjTask bj任务
     * @return bj任务
     */
    @Override
    public List<BjTask> selectBjTaskList(BjTask bjTask)
    {
        return bjTaskMapper.selectBjTaskList(bjTask);
    }

    /**
     * 新增bj任务
     *
     * @param bjTask bj任务
     * @return 结果
     */
    @Override
    public int insertBjTask(BjTask bjTask)
    {
        bjTask.setCreateTime(DateUtils.getNowDate());
        return bjTaskMapper.insertBjTask(bjTask);
    }

    /**
     * 修改bj任务
     *
     * @param bjTask bj任务
     * @return 结果
     */
    @Override
    public int updateBjTask(BjTask bjTask)
    {
        bjTask.setUpdateTime(DateUtils.getNowDate());
        return bjTaskMapper.updateBjTask(bjTask);
    }

    /**
     * 批量删除bj任务
     *
     * @param ids 需要删除的bj任务主键
     * @return 结果
     */
    @Override
    public int deleteBjTaskByIds(String ids)
    {
        return bjTaskMapper.deleteBjTaskByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除bj任务信息
     *
     * @param id bj任务主键
     * @return 结果
     */
    @Override
    public int deleteBjTaskById(Long id)
    {
        return bjTaskMapper.deleteBjTaskById(id);
    }

    public void finishTask(String taskNo) {
        bjTaskMapper.finishTask(taskNo);
    }

    public BjTask selectBjTaskByTaskNo(String taskNo) {
        BjTask task = bjTaskMapper.selectBjTaskByTaskNo(taskNo);
        return task;
    }

    public int selectCountByTaskNo(String taskNo) {
        return bjTaskMapper.selectCountByTaskNo(taskNo);
    }

    public void updateTaskInfo(String taskNo, String taskStatus) {
        bjTaskMapper.updateTaskInfo(taskNo, taskStatus);
    }

    public int isTaskCompleted(String agvNo, String previousTaskNo) {
        return bjTaskMapper.isTaskCompleted(agvNo, previousTaskNo);
    }


    public List<BjTask> feedTaskInfo(BjTask bjTask)
    {
        return bjTaskMapper.selectBjTaskListFeed(bjTask);
    }
}
