package com.ruoyi.project.system.task.mapper;

import java.util.List;
import com.ruoyi.project.system.task.domain.BjTask;
import org.apache.ibatis.annotations.Param;

/**
 * bj任务Mapper接口
 *
 * @author ruoyi
 * @date 2025-07-15
 */
public interface BjTaskMapper
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
     * 删除bj任务
     *
     * @param id bj任务主键
     * @return 结果
     */
    public int deleteBjTaskById(Long id);

    /**
     * 批量删除bj任务
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBjTaskByIds(String[] ids);





    // 检查是否存在状态为2的任务080
    int countCompletedTask080(@Param("agvNo") String agvNo);

    // 删除指定AGV的所有任务
    void deleteTasksByAgvNo(@Param("agvNo") String agvNo);

    void finishTask(String taskNo);

    BjTask selectBjTaskByTaskNo(@Param("taskNo")String taskNo);

    int selectCountByTaskNo(@Param("taskNo")String taskNo);

    void updateTaskInfo(@Param("taskNo")String taskNo, @Param("taskStatus")String taskStatus);

    int isTaskCompleted(@Param("agvNo")String agvNo, @Param("signNo")String previousTaskNo);


    public List<BjTask> selectBjTaskListFeed(BjTask bjTask);
}
