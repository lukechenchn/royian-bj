package com.ruoyi.framework.task;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.mapper.BjTaskMapper;
import com.ruoyi.project.system.xfvisual.service.ApiTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.util.BjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{

    @Autowired
    private BjTaskMapper bjTaskMapper;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }




    /** bj定时任务,处理085任务完成后使用*/
    public void ryNoParamsBj()
    {
        //检查bj_task表中任务的执行状态,更新为已完成  (可先手动修改数据库)
        deleteTasks();


        System.out.println("bj定时任务：deleteTasks");
    }




    public void deleteTasks() {
        //agv一共有01、02、03、04、05、06、07、08、09、10这十个,
        // 检查bj_task表中是否存在某个AGV的任务是080且已经执行完成(状态为2),且未被删除,如果存在则执行删除当前AGV的所有任务
        String[] agvs = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10","11","12","13"};
        for (String agv : agvs) {
            if (checkIfTaskExists(agv)) {
                deleteTasksByAgv(agv);

                //状态变成未电检
//                taskService.updateAgvStateAboutDj(agv,"未电检");

                //清空弹为0
//                taskService.initWrjStatus(agvNo);
//                taskService.initWrjStatus(agv);
            }
        }
        System.out.println("bj定时任务");
    }

    // 检查是否存在已完成的 080 任务   20250824改成085
    private boolean checkIfTaskExists(String agvNo) {
        return bjTaskMapper.countCompletedTask080(agvNo) > 0;
    }

    // 删除该 AGV 的所有任务
    private void deleteTasksByAgv(String agvNo) {
        try {
            bjTaskMapper.deleteTasksByAgvNo(agvNo);
            System.out.println("AGV: " + agvNo + " 的所有任务已删除");

            //状态变成未电检
            taskService.updateAgvStateAboutDj(agvNo,"未电检");

            //清空弹为0
            taskService.initWrjStatus(agvNo);


        } catch (Exception e) {
            System.err.println("删除 AGV " + agvNo + " 的任务失败：" + e.getMessage());
        }
    }





    /*AGV状态查询*/
    @Autowired
    private ApiTaskServiceImpl taskService;
    public void agvStatusQuery()
    {
        String url = "http://192.168.2.2:8086/api/HD/QueryAGVsystem";
        JSONObject paramMap = new JSONObject();
        paramMap.put("Task", "AGVInfo");

        String result2 = null;
        try {
            result2 = BjUtil.postJson(url,paramMap.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Console.log(result2);

        // 核心：将JSON字符串解析为JSONObject（Hutool的Map增强实现）
        JSONObject jsonObject = JSONUtil.parseObj(result2);
        jsonObject.get("data");

        // 1. 获取顶层字段
        String msg = jsonObject.getStr("Msg");
        String code = jsonObject.getStr("Code");
        String result = jsonObject.getStr("Result");

        System.out.println("=== 系统状态信息 ===");
        System.out.println("消息：" + msg);
        System.out.println("状态码：" + code);
        System.out.println("结果标识：" + result);

        // 2. 获取data数组（AGV列表）
        JSONArray dataArray = jsonObject.getJSONArray("data");

        // 转换为List<Map>方便遍历
        List<Map> agvList = dataArray.toList(Map.class);

        for (Map<String, Object> agv : agvList) {
            // 获取AGV基本信息
            String vehicleName = (String) agv.get("vehicleName");
            Object angleObj = agv.get("Angle");
            String angle = angleObj != null ? angleObj.toString() : "0";

            Integer energyLevel = (Integer) agv.get("energyLevel");
            Integer faultCode = (Integer) agv.get("faultCode");
            Integer alarmCode = (Integer) agv.get("alarmCode");

            // 安全地获取嵌套的position坐标
            Object positionObj = agv.get("position");
            JSONArray positionArray = JSONUtil.parseArray(positionObj);

            Console.log("positionObj instanceof List",positionObj instanceof List);
            Console.log(positionArray);

            JSONObject position = positionArray.getJSONObject(0);

            // 提取 x, y, z 值
            String x = position.getStr("x");
            String y = position.getStr("y");
            String z = position.getStr("z");


            String positionDb = "(" + x + ", " + y + ", " + z + ")";
            System.out.println(positionDb);

            // 打印AGV信息
            System.out.println("\nAGV车号：" + vehicleName);
            System.out.println("角度：" + angle + "°");
            System.out.println("电量：" + energyLevel + "%");
            System.out.println("故障码：" + faultCode);
            System.out.println("告警码：" + alarmCode);
            System.out.println("坐标：x=" + x + ", y=" + y + ", z=" + z);

            String agvNo = "01";

            // 更新AGV状态
            taskService.updateAgvState(agvNo, positionDb , energyLevel);
        }
    }







    /**定时查询当前需要执行的任务，如果不是agv执行的任务序列，则不用处理*/
    public void agvTaskSend() {
        //循环13个agv号
        String[] agvs = {"01"};
        for (String agv : agvs) {
            //查询当前需要执行的任务
            Map<String, Object> taskMap = taskService.getNowTaskByAgvNo(agv);

            if(taskMap == null){
                continue;
            }

            // 将Map转换为BjTask实体类
            BjTask nowTask = new BjTask();
            if (taskMap.get("id") != null) {
                nowTask.setId(Long.valueOf(taskMap.get("id").toString()));
            }

            if (taskMap.get("task_no") != null) {
                nowTask.setTaskNo(taskMap.get("task_no").toString());
            }

            if (taskMap.get("agv_no") != null) {
                nowTask.setAgvNo(taskMap.get("agv_no").toString());
            }

            if (taskMap.get("uav_no") != null) {
                nowTask.setUavNo(taskMap.get("uav_no").toString());
            }

            if (taskMap.get("container_no") != null) {
                nowTask.setContainerNo(taskMap.get("container_no").toString());
            }

            if (taskMap.get("sign") != null) {
                nowTask.setSign(taskMap.get("sign").toString());
            }

            if (taskMap.get("sign_no") != null) {
                nowTask.setSignNo(taskMap.get("sign_no").toString());
            }

            if (taskMap.get("task_status") != null) {
                if (taskMap.get("task_status") instanceof Long) {
                    nowTask.setTaskStatus((Long) taskMap.get("task_status"));
                } else {
                    nowTask.setTaskStatus(Long.valueOf(taskMap.get("task_status").toString()));
                }
            }

            if (taskMap.get("remark1") != null) {
                nowTask.setRemark1(taskMap.get("remark1").toString());
            }

            if (taskMap.get("remark2") != null) {
                nowTask.setRemark2(taskMap.get("remark2").toString());
            }

            if (taskMap.get("oil_type") != null) {
                nowTask.setOilType(taskMap.get("oil_type").toString());
            }

            if (taskMap.get("oil_num") != null) {
                nowTask.setOilNum(taskMap.get("oil_num").toString());
            }

            if (taskMap.get("d_type") != null) {
                nowTask.setdType(taskMap.get("d_type").toString());
            }

            if (taskMap.get("d_num") != null) {
                nowTask.setdNum(taskMap.get("d_num").toString());
            }

            if(nowTask == null){continue;}


//            String a = nowTask.getAgvNo();
//            String b = nowTask.getSignNo();
//            String previousTaskNo = getPreviousTaskNoTmp(nowTask.getSignNo());
//            if(previousTaskNo != null){
//                taskService.selectBjTaskByTaskNo(a+"-"+b);
//            }
            //上述逻辑无需这么复杂,全部搜索,当前没有正在执行的AR手持终端任务
            int haveNoDoingArTaskNum = taskService.haveNoDoingArTask(agv);
            if(haveNoDoingArTaskNum>0){
                continue;
            }

            //判断当前任务是否为agv执行任务，如果不是，不处理，如果是，则下发给agv
            String signNo = nowTask.getSignNo();
            String taskId = nowTask.getId()+"";
            if(nowTask.getSignNo().equals("002") || nowTask.getSignNo().equals("003") ||
                    nowTask.getSignNo().equals("005") || nowTask.getSignNo().equals("010")
                    || nowTask.getSignNo().equals("055") || nowTask.getSignNo().equals("060")
                    || nowTask.getSignNo().equals("075") || nowTask.getSignNo().equals("080")){
                String url = "http://192.168.2.2:8086/api/HD/NewTaskDistribution";
                JSONObject paramMap = new JSONObject();
                paramMap.put("MissionUid", taskId);
                //002 判断agv状态
                if(signNo.equals("002")){
                    //最快的思路  执行一个原地不动的指令，后续改为agv状态自检
                    paramMap.put("StationName", "L5");
                    paramMap.put("Balance", false);
                }

                if(signNo.equals("003")){
                    paramMap.put("StationName", "L0");
                    paramMap.put("Balance", false);
                }

                if(signNo.equals("005")){
                    paramMap.put("StationName", "L1");
                    paramMap.put("Balance", false);
                }
                if(signNo.equals("010")){
                    paramMap.put("StationName", "L2");
                    paramMap.put("Balance", true);
                }
                if(signNo.equals("055")){
                    paramMap.put("StationName", "L3");
                    paramMap.put("Balance", false);
                }
                if(signNo.equals("060")){
                    paramMap.put("StationName", "L4");
                    paramMap.put("Balance", true);
                }

                if(signNo.equals("075")){
                    paramMap.put("StationName", "L0");
                    paramMap.put("Balance", false);
                }
                if(signNo.equals("080")){
                    paramMap.put("StationName", "L5");
                    paramMap.put("Balance", false);
                }
                paramMap.put("AGVNum", 1);
                Console.log(paramMap);

                try {

                    String result2 = BjUtil.postJson(url,paramMap.toString());



                    Console.log(result2);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }

    }

    }








//    public static void main(String[] args) throws IOException {
//        String url = "http://192.168.2.2:8086/api/HD/QueryAGVsystem";
//        JSONObject paramMap = new JSONObject();
//        paramMap.put("Task", "AGVInfo");
//
//        String result2 = BjUtil.postJson(url,paramMap.toString());
//        Console.log(result2);
//
//        // 核心：将JSON字符串解析为JSONObject（Hutool的Map增强实现）
//        JSONObject jsonObject = JSONUtil.parseObj(result2);
//        jsonObject.get("data");
//
//        // 1. 获取顶层字段
//        String msg = jsonObject.getStr("Msg");
//        String code = jsonObject.getStr("Code");
//        String result = jsonObject.getStr("Result");
//
//        System.out.println("=== 系统状态信息 ===");
//        System.out.println("消息：" + msg);
//        System.out.println("状态码：" + code);
//        System.out.println("结果标识：" + result);
//
//        // 2. 获取data数组（AGV列表）
//        JSONArray dataArray = jsonObject.getJSONArray("data");
//
//        // 转换为List<Map>方便遍历
//        List<Map> agvList = dataArray.toList(Map.class);
//
//        System.out.println("\n=== AGV车辆列表 ===");
//        for (Map<String, Object> agv : agvList) {
//            // 获取AGV基本信息
//            String vehicleName = (String) agv.get("vehicleName");
//            Object angleObj = agv.get("Angle");
//            String angle = angleObj != null ? angleObj.toString() : "0";
//
//            Integer energyLevel = (Integer) agv.get("energyLevel");
//            Integer faultCode = (Integer) agv.get("faultCode");
//            Integer alarmCode = (Integer) agv.get("alarmCode");
//
//            // 安全地获取嵌套的position坐标
//            Object positionObj = agv.get("position");
//            Integer x = 0, y = 0, z = 0;
//
//            if (positionObj != null) {
//                try {
//                    if (positionObj instanceof Map) {
//                        // 如果position是Map类型
//                        Map<String, Object> position = (Map<String, Object>) positionObj;
//                        x = ((Number) position.get("x")).intValue();
//                        y = ((Number) position.get("y")).intValue();
//                        z = ((Number) position.get("z")).intValue();
//                    } else if (positionObj instanceof List) {
//                        // 如果position是数组类型
//                        List<Object> positionList = (List<Object>) positionObj;
//                        if (positionList.size() >= 3) {
//                            x = ((Number) positionList.get(0)).intValue();
//                            y = ((Number) positionList.get(1)).intValue();
//                            z = ((Number) positionList.get(2)).intValue();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.err.println("解析position数据时出错: " + e.getMessage());
//                }
//            }
//
//            String positionDb = "(" + x + ", " + y + ", " + z + ")";
//            System.out.println(positionDb);
//
//            // 打印AGV信息
//            System.out.println("\nAGV车号：" + vehicleName);
//            System.out.println("角度：" + angle + "°");
//            System.out.println("电量：" + energyLevel + "%");
//            System.out.println("故障码：" + faultCode);
//            System.out.println("告警码：" + alarmCode);
//            System.out.println("坐标：x=" + x + ", y=" + y + ", z=" + z);
//        }
//    }


















    //定时反馈任务状态
    public void ryParams()
    {
        //检查在执行中的任务
        //todo   暂时想不起来这个定时任务是干嘛的

        System.out.println("bj定时任务");
//        System.out.println(list.toString());
    }

}
