package com.ruoyi.project.system.xfvisual.api;


import cn.hutool.core.lang.Console;
import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.framework.task.RyTask;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.mapper.BjTaskMapper;
import com.ruoyi.project.system.task.service.impl.BjTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.mapper.ApiTaskMapper;
import com.ruoyi.project.system.xfvisual.service.ApiTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.util.BjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@Anonymous
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 允许跨域请求
public class UpdateInfoController {

    @Autowired
    private BjTaskServiceImpl taskService;



    @Autowired
    private ApiTaskServiceImpl apiTaskService;

    @Autowired
    TaskAndAgvStatusController tcontroller;


    @Autowired
    private RyTask ryTask;



/**最终接口2：任务状态修改*/
/**提供接口给航保修改AR眼镜的任务的状态修改*/
    @PostMapping("/updateTask")
    public Map updateTask(@RequestBody Map<String, String> data) {
        //返回值
        Map map = new HashMap();
        if (data.get("task_no") == null || data.get("result") == null) {
        }
        String taskNo = data.get("task_no");

        String taskId =data.get("task_id");

        //获取任务详细信息
        /** 这个不用了，因为任务号会有重复*/
        List<BjTask> task = taskService.selectBjTaskByTaskNo(taskNo);
        task = new ArrayList<>();
        BjTask bjTask = taskService.selectBjTaskById(Long.parseLong(taskId.trim()));
        if (bjTask == null){
            map.put("msg","任务不存在");
            map.put("code",500);
            return map;
        }
        task.add(bjTask);
        String taskStatus = data.get("task_status");
        if(taskStatus.equals("1")){
            taskService.updateTaskInfoById(taskId,taskStatus);

            //特殊处理：085 wrj飞行中
            if(task.get(0).getSignNo().equals("085")){
                //更新wrj工作状态为飞行中
                apiTaskService.updateWorkStatus(task.get(0).getAgvNo(), "3");
            }
        }

        if(taskStatus.equals("2")){
//检查上个任务是否为执行完成状态  判断当前任务是否可执行
            try {
                boolean canExecute =  tcontroller.canExecuteCurrentTask(task.get(0).getAgvNo(),task.get(0).getSignNo());
                // 暂时放开任务执行状态检查
                canExecute = true;
                //将任务状态更新
                taskService.updateTaskInfoById(taskId,taskStatus);

                String wrjNo = task.get(0).getUavNo();
                String oilType = data.get("oil_type");
                String oilNumStr = data.get("oil_num");
                String dType = data.get("d_type");
                String dNumStr = data.get("d_num");

                /** 判断任务 045挂弹，执行完后减去油弹库的数量*/
                /**处理油数量逻辑*/
                if(task.get(0).getSignNo().equals("045")){
                    /**前端传过来*/
                    if (dType != null && !dType.isEmpty()) {
                        if (dNumStr != null && !dNumStr.isEmpty()) {
                            int dNum =   Integer.parseInt(dNumStr);
                            if (dType.equals("AR-1")) {
                                //更新挂弹库总数
                                apiTaskService.updateOilContainerD1ByTask(dNum);
                                //无人机表跟新挂弹总数
                                apiTaskService.updateWrjDNum1ByTask(wrjNo,dNum+"");
                            } else if (dType.equals("LT-2")) {
                                //更新挂弹库总数
                                apiTaskService.updateOilContainerD2ByTask(dNum);
                                //无人机表跟新挂弹总数
                                apiTaskService.updateWrjDNum2ByTask(wrjNo,dNum+"");
                            } else if (dType.equals("PL-16")) {
                                //更新挂弹库总数
                                apiTaskService.updateOilContainerD3ByTask(dNum);
                                //无人机表跟新挂弹总数
                                apiTaskService.updateWrjDNum3ByTask(wrjNo,dNum+"");
                            }
                        }
                    }
                }


                /**处理油数量逻辑 这块逻辑拿到040*/
                Console.log(task.get(0).getSignNo().equals("040"),"task.get(0).getSignNo().equals(\"040\")");
                if(task.get(0).getSignNo().equals("040")) {
//                        String oilType = task.get(0).getOilType();
                    if (oilType != null && !oilType.isEmpty()) {
//                            String oilNumStr = task.get(0).getOilNum();
                        BigDecimal oilNum = null;
                        if (oilNumStr != null && !oilNumStr.isEmpty()) {
                            // 先转换为BigDecimal，避免精度丢失
                            BigDecimal originalNum = new BigDecimal(oilNumStr);
                            // 保留两位小数（四舍五入）
                            oilNum = originalNum.setScale(2, BigDecimal.ROUND_HALF_UP);
                            //更新油弹库总数
                            apiTaskService.updateOilContainerOilByTask(oilNum);
                            //无人机更新油数量
                            apiTaskService.updateWrjOilByTask(wrjNo,oilNum+"");
                        }
                    }
                }

                //“010-组装无人机”，无人机信息-任务状态-装配状态随010任务状态更新--陆工 这个在AGV反馈的接口里处理，已完成


                //“015-电检”，无人机信息-电检信息-电检状态，随015任务状态更新--陆工
                if(task.get(0).getSignNo().equals("015")) {
                    apiTaskService.updateAgvStateAboutDj(task.get(0).getAgvNo(),"电检正常");
                }

                //085  直接删除   可以不需要定时任务   定时任务暂时不关，防止有问题 :)
                if(task.get(0).getSignNo().equals("085")) {
                    deleteTasksByAgv(wrjNo);
                }



                if(canExecute){
                }else{
                    map.put("msg","请检查上个任务状态");
                    map.put("code",500);
                    return map;
                }
            }catch (Exception e){
                map.put("msg",e.getMessage());
                map.put("code",500);
                return map;
            }


        }
        map.put("msg",taskNo+"任务信息已更新");
        map.put("code",200);

        return map;
    }


    @Autowired
    private BjTaskMapper bjTaskMapper;


    private void deleteTasksByAgv(String agvNo) {
        try {
            bjTaskMapper.deleteTasksByAgvNo(agvNo);

            //状态变成未电检
            apiTaskService.updateAgvStateAboutDj(agvNo,"未电检");

            //清空弹为0  wrj变成未出库  agv状态变成0  装配状态变成未装配
            apiTaskService.initWrjStatus(agvNo);

        } catch (Exception e) {
            System.err.println("删除 AGV " + agvNo + " 的任务失败：" + e.getMessage());
        }
    }





//    public Map deleteTasks(@RequestBody Map<String, String> data) {
//        Map map = new HashMap();
//        if (data.get("task_no") == null || data.get("result") == null) {
//        }
//        String taskNo = data.get("task_no");
//        //获取任务详细信息
//        List<BjTask> task = taskService.selectBjTaskByTaskNo(taskNo);
//        if(task.size()>0){
//
//            String taskStatus = data.get("task_status");
//            //1.将任务状态更新
//            taskService.updateTaskInfo(taskNo,taskStatus);
//            if(taskStatus.equals("2")){
////检查上个任务是否为执行完成状态  判断当前任务是否可执行
//                try {
//                boolean canExecute =  tcontroller.canExecuteCurrentTask(task.get(0).getAgvNo(),task.get(0).getSignNo());
////                暂时放开任务执行状态检查
//                    canExecute = true;
//
//
//                    String wrjNo = task.get(0).getUavNo();
//                    String oilType = data.get("oil_type");//type没用到
//                    String oilNumStr = data.get("oil_num");
//                    String dType = data.get("d_type");
//                    String dNumStr = data.get("d_num");
//
//
//
//                    if (Objects.isNull(oilNumStr) || oilNumStr.trim().isEmpty()) {
////                        throw new IllegalArgumentException("参数 oil_num 不能为空");
//                    }
//                    if (Objects.isNull(dType) || dType.trim().isEmpty()) {
////                        throw new IllegalArgumentException("参数 d_tpye 不能为空");
//                    }
//                    if (Objects.isNull(dNumStr) || dNumStr.trim().isEmpty()) {
////                        throw new IllegalArgumentException("参数 d_num 不能为空");
//                    }
//
//
//                    /** 判断任务 045挂弹，执行完后减去油弹库的数量*/
//                    /**处理油数量逻辑*/
//                    if(task.get(0).getSignNo().equals("045")){
//                        /**前端传过来*/
//
////                    String dType = task.get(0).getdType();
//                    if (dType != null && !dType.isEmpty()) {
////                        String dNumStr = task.get(0).getdNum();
//                        if (dNumStr != null && !dNumStr.isEmpty()) {
//                            int dNum =   Integer.parseInt(dNumStr);
//                            if (dType.equals("AR-1")) {
//                                //更新挂弹库总数
//                                apiTaskService.updateOilContainerD1ByTask(dNum);
//                                //无人机表跟新挂弹总数
//                                apiTaskService.updateWrjDNum1ByTask(wrjNo,dNum+"");
//                            } else if (dType.equals("LT-2")) {
//                                //更新挂弹库总数
//                                apiTaskService.updateOilContainerD2ByTask(dNum);
//                                //无人机表跟新挂弹总数
//                                apiTaskService.updateWrjDNum2ByTask(wrjNo,dNum+"");
//                            } else if (dType.equals("PL-16")) {
//                                //更新挂弹库总数
//                                apiTaskService.updateOilContainerD3ByTask(dNum);
//                                //无人机表跟新挂弹总数
//                                apiTaskService.updateWrjDNum3ByTask(wrjNo,dNum+"");
//                            }
//                        }
//                    }
//                    }
//
//
//                    /**处理油数量逻辑 这块逻辑拿到040*/
//                    if(task.get(0).getSignNo().equals("040")) {
////                        String oilType = task.get(0).getOilType();
//                        if (oilType != null && !oilType.isEmpty()) {
////                            String oilNumStr = task.get(0).getOilNum();
//                            BigDecimal oilNum = null;
//                            if (oilNumStr != null && !oilNumStr.isEmpty()) {
//                                // 先转换为BigDecimal，避免精度丢失
//                                BigDecimal originalNum = new BigDecimal(oilNumStr);
//                                // 保留两位小数（四舍五入）
//                                oilNum = originalNum.setScale(2, BigDecimal.ROUND_HALF_UP);
//                                //更新油弹库总数
//                                apiTaskService.updateOilContainerOilByTask(oilNum);
//
//                                //无人机更新油数量
//                                apiTaskService.updateWrjOilByTask(wrjNo,oilNum+"");
//                            }
//                        }
//                    }
//
//                    //“010-组装无人机”，无人机信息-任务状态-装配状态随010任务状态更新--陆工
//                    if(task.get(0).getSignNo().equals("010")) {
//
//
//                    }
//
//
//
//
//                if(canExecute){
//                }else{
//                    map.put("msg","请检查上个任务状态");
//                    map.put("code",500);
//                    return map;
//                }
//                }catch (Exception e){
//                    map.put("msg",e.getMessage());
//                    map.put("code",500);
//                    return map;
//                }
//
////            将该任务的已反馈状态置为1
//                taskService.finishTask(taskNo);
//
//
////2.调用接口反馈已经完成的信息,  这块不用调用了  先放着
////        任务号
////                task_no
////        任务执行状态
////                task_status
////        开始时间
////                start_time
////        结束时间
////                finish_time
//
//                List<Map> list= new ArrayList<>();
//                Map<String, Object> feedbackData = new HashMap<>();
//                feedbackData.put("task_no", taskNo); // 任务号
//                feedbackData.put("task_status", "2"); // 任务执行状态: 已完成
//                feedbackData.put("start_time", task.get(0).getCreateTime()); // 可以从数据库或其他逻辑中获取实际开始时间
//                feedbackData.put("finish_time", new Date()); // 结束时间，当前时间
//                list.add(feedbackData);
//
//// 发送 POST 请求
////        String result2 = HttpRequest.post("url")
////                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
////                .form(feedbackData)//表单内容
////                .timeout(20000)//超时，毫秒
////                .execute().body();
////        Console.log(result2);
//        }
//            map.put("msg",taskNo+"任务信息已更新");
//            map.put("code",200);
//        }else{
//            map.put("msg",taskNo+"任务不存在");
//            map.put("code",500);
//        }
//        // 返回响应...
//        return map;
//    }






}
