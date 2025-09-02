package com.ruoyi.project.system.xfvisual.api;


import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.project.system.info.domain.BjZlSystemInfo;
import com.ruoyi.project.system.info.service.IBjZlSystemInfoService;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.tsinfo.domain.BjTsSystemInfo;
import com.ruoyi.project.system.tsinfo.service.IBjTsSystemInfoService;
import com.ruoyi.project.system.xfvisual.service.ApiTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Anonymous
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 允许跨域请求
public class FeedBack2Controller {

    @Autowired
    private ApiTaskServiceImpl taskService;

    /**最终接口6：获取机库信息*/
    @GetMapping("/hangarInfo")
    public Map hangarInfo() {
//        {
//            overall_dispatch_rate: number,  // 整体出动率
//                    total_drones: number,           // 总无人机数
//                total_dispatched: number,       // 总出动数
//                total_remaining: number,        // 总剩余数
//                "drone_types": [
//            {
//                "drone_type": "AAA",
//                    "total_count": number ,
//            },
//            {
//                "drone_type": "BBB",
//                    "total_count":  number,
//            },
//            {
//                "drone_type": "CCC",
//                    "total_count":  number,
//            }
//        }
//    }
        Map<String, Object> result = new HashMap<>();
        String total_drones = taskService.getRemark1Count().get("total_drones").toString();
        String total_dispatched = taskService.getRemark1Count().get("total_dispatched").toString();
        String total_remaining = taskService.getRemark1Count().get("total_remaining").toString();
        int totalDrones = Integer.parseInt(total_drones);
        int totalDispatched = Integer.parseInt(total_dispatched);
        int totalRemaining = Integer.parseInt(total_remaining);
        // 总出动数
        result.put("total_drones", totalDrones);
        // 总无人机数（去重统计uavNo）
        result.put("total_dispatched", totalDispatched);
        // 总剩余数（未完成的任务）
        result.put("total_remaining", totalRemaining);
        // 整体出动率（百分比，保留两位小数）
        double overallDispatchRate = totalDrones > 0 ?
                Math.round((double) totalDispatched / totalDrones * 10000) / 100.0 : 0.0;
        result.put("overall_dispatch_rate", overallDispatchRate);
        // 无人机类型统计
        result.put("drone_types", taskService.getDroneTypeCount());
        return result;
        //todo  根据任务更新无人机出动状态
    }



    /**最终接口7 查看油弹箱信息*/
//    {
//        "oil_quantity1": "10L",
//            "oil_quantity2": "10L",
//            "oil_quantity3": "10L",
//            "quantity1": "10",
//            "quantity2": "20",
//            "quantity3": "30",
//    }

    @GetMapping("/receiveRefuelAmmoInfo")
    public Map receiveRefuelAmmoInfo() {
        Map<String, Object> result = new HashMap<>();

        // 从服务层获取油弹箱信息
        Map<String, Object> oilContainerInfo = taskService.getOilContainerInfo();

        // 提取油数量信息，若为null则设置默认值
        result.put("oil_quantity1",
                oilContainerInfo.get("oil_quantity1") != null ? oilContainerInfo.get("oil_quantity1") : "0L");
        result.put("oil_quantity2",
                oilContainerInfo.get("oil_quantity2") != null ? oilContainerInfo.get("oil_quantity2") : "0L");
        result.put("oil_quantity3",
                oilContainerInfo.get("oil_quantity3") != null ? oilContainerInfo.get("oil_quantity3") : "0L");

// 提取弹药数量信息，若为null则设置默认值
        result.put("quantity1",
                oilContainerInfo.get("quantity1") != null ? oilContainerInfo.get("quantity1") : "0");
        result.put("quantity2",
                oilContainerInfo.get("quantity2") != null ? oilContainerInfo.get("quantity2") : "0");
        result.put("quantity3",
                oilContainerInfo.get("quantity3") != null ? oilContainerInfo.get("quantity3") : "0");
        return result;
    }



    /**最终接口8：修改油弹箱信息*/

    @PostMapping("/upDataRefuelAmmoInfo")
    public Map<String, Object> updateRefuelAmmoInfo(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 调用服务层执行更新操作
            int rows = taskService.updateOilContainerInfo(params);

            // 根据更新结果返回相应信息
            if (rows > 0) {
                result.put("code", 200);
                result.put("msg", "油弹信息已更新");
            } else {
                result.put("code", 201);
                result.put("msg", "未找到可更新的油弹信息");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "更新失败：" + e.getMessage());
        }

        return result;
    }



    /**最终接口9：总状态反馈（无人机状态+agv状态）*/
//    @GetMapping("/statusFeedback")
//    public List<Map<String, Object>> getStatusFeedback(@RequestParam("agv_no") String agvNo) {
//        // 调用服务层查询状态信息
//        return taskService.getTotalStatus(agvNo);
//    }
    /**最终接口9：总状态反馈（无人机状态+agv状态）*/
    @GetMapping("/statusFeedback")
    public List<Map<String, Object>> getStatusFeedback(@RequestParam(required = false) String agv_no) {
        List<Map<String, Object>> result = new ArrayList<>();

        if (agv_no != null && !agv_no.isEmpty()) {
            // 如果提供了agv_no参数，查询指定AGV的状态
            return taskService.getTotalStatus(agv_no);
        } else {
            // 如果没有提供agv_no参数，循环查询01到13的AGV状态
            for (int i = 1; i <= 13; i++) {
                String agvNo = String.format("%02d", i); // 格式化为两位数，如01, 02, ..., 13
                List<Map<String, Object>> agvStatus = taskService.getTotalStatus(agvNo);
                result.addAll(agvStatus);
            }
            return result;
        }
    }



    /**最终接口10：ABC总数量展示*/
    @GetMapping("/totalNum")
    public Map<String, Object> getAgvTotalNum(
            @RequestParam(required = false) String agv_type) {

        // 调用服务层查询数据，直接返回Map集合（会自动序列化为所需JSON）

        return taskService.getAgvTotalNum(agv_type);
    }



    /**写个接口，清空所有任务*/
    @GetMapping("/deleteTasks")
    public Map deleteTasks() {
    taskService.deleteTasks();

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "清空完成");
        return response;
    }


    /**写个定时任务，如果存在085任务已经完成，那么就删除创建时间小于这个任务的当前这个agv的所有任务
     *   延用原来的定时任务实现  这里先放着，后续可以删除*/

//    try {
//        // 查找所有已完成的085任务
//        BjTask queryTask = new BjTask();
//        queryTask.setSignNo("085");
//        queryTask.setTaskStatus("2"); // 假设"2"表示任务已完成
//
//        List<BjTask> completed085Tasks = taskService.selectBjTaskList(queryTask);
//
//        // 遍历每个已完成的085任务
//        for (BjTask completedTask : completed085Tasks) {
//            String agvNo = completedTask.getAgvNo();
//            // 获取该任务的创建时间
//            Date completedTaskCreateTime = completedTask.getCreateTime();
//
//            if (agvNo != null && completedTaskCreateTime != null) {
//                // 删除该AGV创建时间早于completedTaskCreateTime的所有任务
//                taskService.deleteTasksByAgvAndCreateTimeBefore(agvNo, completedTaskCreateTime);
//            }
//        }
//    } catch (Exception e) {
//        // 记录错误日志
//        System.err.println("定时任务执行失败: " + e.getMessage());
//        e.printStackTrace();
//    }

//    // 在对应的Mapper接口中添加方法
//    int deleteTasksByAgvAndCreateTimeBefore(@Param("agvNo") String agvNo, @Param("createTime") Date createTime);


//            <!-- 删除指定AGV创建时间早于指定时间的所有任务 -->
//<delete id="deleteTasksByAgvAndCreateTimeBefore">
//    DELETE FROM bj_task
//    WHERE agv_no = #{agvNo}
//    AND create_time &lt; #{createTime}
//</delete>

    @Autowired
    private IBjZlSystemInfoService bjZlSystemInfoService;
    /**ZL系统查询*/
    @GetMapping("/zlQuery")
    public Map zlQuery() {
        BjZlSystemInfo info =     bjZlSystemInfoService.selectBjZlSystemInfoById(1L);
        Map<String, Object> result = new HashMap<>();
        if (info != null) {
            result.put("status", info.getStatus());
            result.put("speed", info.getSpeed());
            result.put("num", info.getNum());
            result.put("weight", info.getWeight());
        }
        return result;
    }

    /**ZL系统保存*/
    @PostMapping("/zlUpdate")
    public Map zlUpdate(@RequestBody Map<String, String> data) {
        BjZlSystemInfo bjZlSystemInfo = new BjZlSystemInfo();
        bjZlSystemInfo.setId(1L);
        bjZlSystemInfo.setStatus(data.get("status"));
        bjZlSystemInfo.setWeight(Long.valueOf(data.get("weight")));
        bjZlSystemInfo.setSpeed(Long.valueOf(data.get("speed")));
        bjZlSystemInfo.setNum(Long.valueOf(data.get("num")));
        bjZlSystemInfoService.updateBjZlSystemInfo(bjZlSystemInfo);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "ZL系统信接收成功");
        return  response;
    }


    /**TS系统查询*/
    @Autowired
    private IBjTsSystemInfoService bjTsSystemInfoService ;
    @GetMapping("/tsQuery")
    public Map tsQuery() {
        BjTsSystemInfo info =   bjTsSystemInfoService.selectBjTsSystemInfoById(1L);
        Map<String, Object> result = new HashMap<>();
        if (info != null) {
            result.put("status", info.getStatus());
            result.put("battery", info.getBattery());
            result.put("num", info.getNum());
        }
        return result;
    }

    /**TS系统保存*/
    @PostMapping("/tsUpdate")
    public Map tsUpdate(@RequestBody Map<String, String> data) {
        BjTsSystemInfo bjTsSystemInfo = new BjTsSystemInfo();
        bjTsSystemInfo.setId(1L);
        bjTsSystemInfo.setStatus(data.get("status"));
        bjTsSystemInfo.setBattery(data.get("battery"));
        bjTsSystemInfo.setNum(Integer.parseInt(data.get("num")));
        bjTsSystemInfoService.updateBjTsSystemInfo(bjTsSystemInfo);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "TS系统信接收成功");
        return  response;
    }



    /** 20250902  新增开发*/

    //    taskInfo
    //    任务看版列表，加油、挂dan任务需要接口返回具体型号、数量等信息。


    /*
    返回agv sub type 用来区分无人机小类
    其中需要在数据库中维护大类：AAA、BBB、CCC对应的汉字名称
    小类：aaa、bbb、ccc汉字名称。
    汉字信息和AAA、BBB一起返回给前端，现在是真实数据，客户现场维护真实数据。
    **/



    /***
     补充逻辑
     1.出库逻辑。完成所有任务之后入库
     2.加油挂弹数量逻辑   实时更新数量
     3.AGV触发任务，状态改为执行中
     * */


}
