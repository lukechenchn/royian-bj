package com.ruoyi.project.system.xfvisual.api;


import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.service.impl.BjTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.util.BjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Anonymous
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 允许跨域请求
public class UpdateInfoController {

    @Autowired
    private BjTaskServiceImpl taskService;

    @Autowired
    TaskAndAgvStatusController tcontroller;



//    提供接口给航保修改AR眼镜的任务的状态修改
    @PostMapping("/updateTask")
    public Map deleteTasks(@RequestBody Map<String, String> data) {
        Map map = new HashMap();
        if (data.get("task_no") == null || data.get("result") == null) {
        }
        String taskNo = data.get("task_no");
        //获取任务详细信息
        BjTask task = taskService.selectBjTaskByTaskNo(taskNo);
        if(task != null){
            String taskStatus = data.get("task_status");
            //1.将任务状态更新
            taskService.updateTaskInfo(taskNo,taskStatus);
            if(taskStatus.equals("2")){
//检查上个任务是否为执行完成状态
                try {
                boolean canExecute =  tcontroller.canExecuteCurrentTask(task.getAgvNo(),task.getSignNo());
//                暂时放开任务执行状态检查
                    canExecute = true;
                if(canExecute){
                }else{
                    map.put("msg","请检查上个任务状态");
                    map.put("code",500);
                    return map;
                }
                }catch (Exception e){
                    map.put("msg","请检查上个任务状态");
                    map.put("code",500);
                    return map;
                }

//            将该任务的已反馈状态置为1
                taskService.finishTask(taskNo);


                //2.调用接口反馈已经完成的信息,
//        任务号
//                task_no
//        任务执行状态
//                task_status
//        开始时间
//                start_time
//        结束时间
//                finish_time

                List<Map> list= new ArrayList<>();
                Map<String, Object> feedbackData = new HashMap<>();
                feedbackData.put("task_no", taskNo); // 任务号
                feedbackData.put("task_status", "2"); // 任务执行状态: 已完成
                feedbackData.put("start_time", task.getCreateTime()); // 可以从数据库或其他逻辑中获取实际开始时间
                feedbackData.put("finish_time", new Date()); // 结束时间，当前时间
                list.add(feedbackData);


// 发送 POST 请求
//        String result2 = HttpRequest.post("url")
//                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                .form(feedbackData)//表单内容
//                .timeout(20000)//超时，毫秒
//                .execute().body();
//        Console.log(result2);
        }
            map.put("msg",taskNo+"任务信息已更新");
            map.put("code",200);
        }else{
            map.put("msg",taskNo+"任务不存在");
            map.put("code",500);
        }
        // 返回响应...
        return map;
    }






}
