package com.ruoyi.project.system.xfvisual.service;

import cn.hutool.core.lang.Console;
import com.ruoyi.project.system.info.domain.BjZlSystemInfo;
import com.ruoyi.project.system.status.domain.BjAgvStatus;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.mapper.BjTaskMapper;
import com.ruoyi.project.system.xfvisual.mapper.ApiTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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



    private static final String[] MISSILE_NAMES = {
            "AR-1",   // quantity1对应导弹名称
            "LT-2",   // quantity2对应导弹名称
            "PL-16",   // quantity3对应导弹名称
            "4导弹",   // quantity4对应导弹名称
            "5导弹"    // quantity5对应导弹名称
    };

    public List<Map<String, Object>> getTotalStatus(String agvNo) {
        // 调用Mapper查询指定AGV的总状态信息

//        return bjTaskMapper.selectTotalStatus(agvNo);
        // 1. 查询基础数据（包含quantity1到quantity5）
        List<Map<String, Object>> statusList = bjTaskMapper.selectTotalStatus(agvNo);

        // 2. 处理每条记录的quantity_status拼接
        Console.log(statusList);

        for (Map<String, Object> status : statusList) {
            StringBuilder quantityStatus = new StringBuilder();
            boolean hasMissile = false;

            // 处理quantity1-5拼接，不改变原始字段的空值状态
            for (int i = 1; i <= 5; i++) {
                String key = "quantity" + i;
                Object valueObj = status.get(key); // 即使为null也正常获取

                // 仅处理非空且有效的值，空值直接跳过
                if (valueObj != null) {
                    try {
                        int quantity = Integer.parseInt(valueObj.toString().trim());
                        if (quantity > 0) {
                            hasMissile = true;
                            String desc = quantity + "枚" + MISSILE_NAMES[i-1];
                            if (quantityStatus.length() > 0) {
                                quantityStatus.append("，");
                            }
                            quantityStatus.append(desc);
                        }
                    } catch (NumberFormatException e) {
                        // 非数字格式也视为无效值，不参与拼接
                        continue;
                    }
                }
            }

            // 设置拼接后的挂弹状态
            status.put("quantity_status", hasMissile ? quantityStatus.toString() : "未挂弹");

            // 只移除quantity1-5这5个字段，其他所有字段（包括空值）全部保留
            for (int i = 1; i <= 5; i++) {
                status.remove("quantity" + i);
            }
        }

//        for (Map<String, Object> status : statusList) {
//            StringBuilder quantityStatus = new StringBuilder();
//            boolean hasMissile = false; // 标记是否有非零的导弹数量
//
//            // 循环处理quantity1到quantity5
//            for (int i = 1; i <= 5; i++) {
//                String key = "quantity" + i;
//                Object valueObj = status.get(key);
//
//                // 检查值是否有效（非空且大于0）
//                if (valueObj != null) {
//                    try {
//                        int quantity = Integer.parseInt(valueObj.toString().trim());
//                        if (quantity > 0) {
//                            hasMissile = true; // 标记存在有效导弹数量
//                            // 拼接格式：XX枚XXX导弹
//                            String missileDesc = quantity + "枚" + MISSILE_NAMES[i-1];
//
//                            // 如果已有内容，先加逗号分隔
//                            if (quantityStatus.length() > 0) {
//                                quantityStatus.append("，");
//                            }
//                            quantityStatus.append(missileDesc);
//                        }
//                    } catch (NumberFormatException e) {
//                        // 处理数字转换异常（如非数字格式）
//                        continue;
//                    }
//                }
//            }
//
//            // 3. 处理全零情况，否则使用拼接结果
//            String finalStatus = hasMissile ? quantityStatus.toString() : "未挂弹";
//            status.put("quantity_status", finalStatus);
//
//            // 移除quantity1到quantity5这5个字段，避免返回
//            for (int i = 1; i <= 5; i++) {
//                status.remove("quantity" + i);
//            }
//        }

        return statusList;
    }


    public void deleteTasks() {
        bjTaskMapper.deleteTasks();
    }

    public Map getTargetTaskByAgvNo(String agvNo) {
        // 直接调用Mapper执行SQL，SQL已保证优先级逻辑
        return bjTaskMapper.selectTargetTaskByAgvNo(agvNo);
    }

    public void updateRemark1OfAgv(String agvNo, int i) {
        bjTaskMapper.updateRemark1OfAgv(agvNo, i);
    }

    public void updateOilContainerOilByTask(BigDecimal oilNum) {
        bjTaskMapper.updateOilContainerOilByTask(oilNum);
    }

    public void updateOilContainerD1ByTask(int dNum) {
        bjTaskMapper.updateOilContainerD1ByTask(dNum);
    }

    public void updateOilContainerD2ByTask(int dNum) {
        bjTaskMapper.updateOilContainerD2ByTask(dNum);
    }

    public void updateOilContainerD3ByTask(int dNum) {
        bjTaskMapper.updateOilContainerD3ByTask(dNum);
    }
}
