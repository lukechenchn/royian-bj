package com.ruoyi.project.system.xfvisual.mapper;

import com.ruoyi.project.system.status.domain.BjAgvStatus;
import com.ruoyi.project.system.task.domain.BjTask;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ApiTaskMapper {


    public BjTask selectBjTaskById(Long id);


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


    public List<BjAgvStatus> selectBjAgvStatusListFeed(BjAgvStatus bjAgvStatus);




    public int updateBjAgvWithOilStatus(BjAgvStatus bjAgvStatus);



    /**
     * 查询无人机类型统计数据，使用Map存储结果
     * @return 包含各类型无人机数量的Map列表
     */

    public List<HashMap<String, Object>> getDroneTypeCount();

    /**
     * 查询remark1为0、1的数量及总数
     * @return 包含count_0、count_1和total_count的Map
     */
    HashMap<String, Object> getRemark1Count();


    public HashMap<String, Object> selectOilContainerInfo();

    public int updateOilContainer(Map<String, Object> params);

    @MapKey("agv_no") // 替换为实际作为key的字段名，例如 "agvNo" 或 "type"
    List<Map<String, Object>> selectAgvStatusByType(Map<String, Object> params);

    @MapKey("agv_type")
    List<Map<String, Object>> selectAgvTypeCount(String agvType);

    @MapKey("agv_no")
    List<Map<String, Object>> selectTotalStatus(String agvNo);

    void deleteTasks();
}
