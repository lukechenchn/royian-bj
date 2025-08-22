package com.ruoyi.project.system.xfvisual.service;

import com.ruoyi.project.system.status.domain.BjAgvStatus;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.mapper.BjTaskMapper;
import com.ruoyi.project.system.xfvisual.mapper.ApiTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ApiTaskServiceImpl {
    @Autowired
    private ApiTaskMapper bjTaskMapper;

    public BjTask test(Long id)
    {
        return bjTaskMapper.selectBjTaskById(id);
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


    public List<BjAgvStatus> feedAgvInfo(BjAgvStatus agvInfo) {
        return bjTaskMapper.selectBjAgvStatusListFeed(agvInfo);
    }


    public int updateBjAgvWithOilStatus(BjAgvStatus bjAgvStatus)
    {
        return bjTaskMapper.updateBjAgvWithOilStatus(bjAgvStatus);
    }



    public List<HashMap<String, Object>> getDroneTypeCount() {
        return bjTaskMapper.getDroneTypeCount();
    }

    public Map<String, Object> getRemark1Count() {
        return bjTaskMapper.getRemark1Count();
    }


    public Map<String, Object> getOilContainerInfo() {
        // 查询bj_oil_container表中的油弹箱信息
        return bjTaskMapper.selectOilContainerInfo();
    }

    public int updateOilContainerInfo(Map<String, Object> params) {
        // 执行更新操作，返回受影响的行数
        return bjTaskMapper.updateOilContainer(params);
    }




    /**
     * 查询AGV状态列表
     * @param agvType 可选参数，AGV类型
     * @return 状态列表
     */
//    public List<Map<String, Object>> getAgvTotalNum(String agvType) {
    public Map<String, Object> getAgvTotalNum(String agvType) {

        // 封装参数
        Map<String, Object> params = new java.util.HashMap<>();
        params.put("agvType", agvType);  // 与Mapper.xml中的#{agvType}对应
//
//        return bjTaskMapper.selectAgvStatusByType(params);
        // 创建返回结果Map
        Map<String, Object> result = new HashMap<>();

        // 查询详细列表
//        List<Map<String, Object>> statusList = bjTaskMapper.selectAgvStatusByType(agvType);
        List<Map<String, Object>> statusList = bjTaskMapper.selectAgvStatusByType(params);
        // 查询类型总数统计
        List<Map<String, Object>> typeCountList = bjTaskMapper.selectAgvTypeCount(agvType);

        // 组装返回结果
        result.put("status_list", statusList);
        result.put("type_count", typeCountList);

        return result;
    }
}
