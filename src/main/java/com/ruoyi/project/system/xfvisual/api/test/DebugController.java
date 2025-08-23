package com.ruoyi.project.system.xfvisual.api.test;



import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.service.impl.BjTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.util.BjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cn.hutool.core.lang.Console;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;

import java.util.*;

@RestController
@Anonymous
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 允许跨域请求
public class DebugController {


    @Autowired
    private BjTaskServiceImpl taskService;



    //调试接口:完成某项任务,并发起反馈
    @PostMapping("/finishTask")
    public List deleteTasks(@RequestBody Map<String, String> data) {
        if (data.get("task_no") == null || data.get("result") == null) {
        }
        String taskNo = data.get("task_no");
        Map<String, String> fields = BjUtil.splitTaskNo(taskNo);
        String agvNo = fields.get("agv_no");
        String taskNoOnly = fields.get("task_no");

        //1.将任务状态置为2已完成 且 将任务的已反馈状态置为1 且 finish_time置为当前时间
        taskService.finishTask(taskNo);

        //2.调用接口反馈已经完成的信息
//        任务号
//                task_no
//        任务执行状态
//                task_status
//        开始时间
//                start_time
//        结束时间
//                finish_time

        List<BjTask> task = taskService.selectBjTaskByTaskNo(taskNo);
        List<Map> list= new ArrayList<>();
        Map<String, Object> feedbackData = new HashMap<>();
        feedbackData.put("task_no", taskNo); // 任务号
        feedbackData.put("task_status", "2"); // 任务执行状态: 已完成
        feedbackData.put("start_time", task.get(0).getCreateTime()); // 可以从数据库或其他逻辑中获取实际开始时间
        feedbackData.put("finish_time", new Date()); // 结束时间，当前时间
        list.add(feedbackData);

// 发送 POST 请求

//        String result2 = HttpRequest.post("url")
//                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                .form(feedbackData)//表单内容
//                .timeout(20000)//超时，毫秒
//                .execute().body();
//        Console.log(result2);

        //3.将该任务的已反馈状态置为1 (在第一步完成)

        // 返回响应...

        return list;
    }

}
