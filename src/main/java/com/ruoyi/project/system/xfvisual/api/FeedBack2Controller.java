package com.ruoyi.project.system.xfvisual.api;


import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.xfvisual.service.ApiTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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



    /**最终接口10：ABC总数量展示*/
    @GetMapping("/totalNum")
    public Map<String, Object> getAgvTotalNum(
            @RequestParam(required = false) String agv_type) {

        // 调用服务层查询数据，直接返回Map集合（会自动序列化为所需JSON）
        return taskService.getAgvTotalNum(agv_type);
    }
}
