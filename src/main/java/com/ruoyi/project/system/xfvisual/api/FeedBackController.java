package com.ruoyi.project.system.xfvisual.api;

import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.project.system.status.domain.BjAgvStatus;
import com.ruoyi.project.system.status.service.IBjAgvStatusService;
import com.ruoyi.project.system.status.service.impl.BjAgvStatusServiceImpl;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.service.impl.BjTaskServiceImpl;
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
public class FeedBackController {
    @Autowired
    private BjTaskServiceImpl taskService;

    @Autowired
    private BjAgvStatusServiceImpl agvService;

    @Autowired
    TaskAndAgvStatusController tcontroller;


    //反馈任务信息
    @PostMapping("/taskInfo")
    public List feedTaskInfo(@RequestBody Map<String, String> data) {
        BjTask bjTask = new BjTask();
        if(data.get("task_no") != null){
            bjTask.setTaskNo(data.get("task_no"));
        }
        //任务状态不等于2
        bjTask.setIsDeleted(0L);
        List<BjTask> tasks =taskService.feedTaskInfo(bjTask);

        return tasks.stream()
                .map(task -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("task_no", task.getTaskNo());
                    result.put("task_status", task.getTaskStatus());
                    result.put("start_time", task.getCreateTime());
                    result.put("finish_time",task.getFinishTime());
                    result.put("result", task.getTaskNo()+"."+task.getTaskStatus());
                    return result;
                })
                .collect(Collectors.toList());
    }


    //反馈AGV信息
    @PostMapping("/agvInfo")
    public List feedagvInfo(@RequestBody Map<String, String> data) {
        BjAgvStatus agvInfo = new BjAgvStatus();

        if(data.get("agv_no") != null){
            agvInfo.setAgvNo(data.get("agv_no"));
        }

        List<BjAgvStatus> agvs =
                agvService.feedAgvInfo(agvInfo);

        return agvs.stream()
                .map(task -> {
                    Map<String, Object> result = new HashMap<>();
//                    result.put("task_no", task.getTaskNo());

//                    agv_status
//                            battery_level
//                    current_position
//                            oil_quantity
//                    oil_type
//                            model1
//                    quantity1
//                            model2
//                    quantity2
//                            model3
//                    quantity3
//                            model4
//                    quantity4
//                            model5
//                    quantity5
                    result.put("agv_no", task.getAgvNo());
                    result.put("agv_status", task.getAgvStatus());
                    result.put("battery_level", task.getBatteryLevel());
                    result.put("current_position", task.getCurrentPosition());
                    result.put("oil_quantity", task.getOilQuantity());
                    result.put("quantity1", task.getQuantity1());
                    result.put("quantity2", task.getQuantity2());
                    result.put("quantity3", task.getQuantity3());
                    result.put("quantity4", task.getQuantity4());
                    result.put("quantity5", task.getQuantity5());
                    result.put("container_status", task.getContainerStatus());
                    return result;
                })
                .collect(Collectors.toList());
    }



    
}
