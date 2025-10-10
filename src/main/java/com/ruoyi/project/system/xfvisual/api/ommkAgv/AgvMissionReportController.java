package com.ruoyi.project.system.xfvisual.api.ommkAgv;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.service.impl.BjTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.service.ApiTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AGV任务状态上报接口控制器
 */
@RestController
@RequestMapping("/api")
@Anonymous
public class AgvMissionReportController {
//    Omark agv管理地址
//    http://192.168.2.2:8080/#/vehicleRunning/running
//    guest
//    888888


    @Autowired
    private ApiTaskServiceImpl taskService;

    @Autowired
    private BjTaskServiceImpl bjTaskService;


    /**调用接口，获取AGV实时状态*/
//    @GetMapping("/OmarkAgvState")
//    public Map<String, Object> getOmarkAgvState() {
//        String url = "http://192.168.2.2:8086/api/HD/QueryAGVsystem";
//        Map paramMap = new HashMap<>();
//        paramMap.put("Task", "AGVInfo");
//        String result2 = HttpRequest.post(url)
////            .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                .form(paramMap)//表单内容
//                .timeout(20000)//超时，毫秒
//                .execute().body();
//        Console.log(result2);
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
//            Integer angle = (Integer) agv.get("Angle");
//            Integer energyLevel = (Integer) agv.get("energyLevel");
//            Integer faultCode = (Integer) agv.get("faultCode");
//            Integer alarmCode = (Integer) agv.get("alarmCode");
//
//            // 获取嵌套的position坐标（也是一个Map）
//            Map<String, Integer> position = (Map<String, Integer>) agv.get("position");
//            Integer x = position.get("x");
//            Integer y = position.get("y");
//            Integer z = position.get("z");
//            String positionDb = "(" + x + ", " + y + ", " + z + ")";
//            System.out.println("(" + x + ", y=" + y + ", z=" + z+")");
//
//
//            // 打印AGV信息
//            System.out.println("\nAGV车号：" + vehicleName);
//            System.out.println("角度：" + angle + "°");
//            System.out.println("电量：" + energyLevel + "%");
//            System.out.println("故障码：" + faultCode);
//            System.out.println("告警码：" + alarmCode);
//            System.out.println("坐标：x=" + x + ", y=" + y + ", z=" + z);
//
//            String agvNo = "01";
//
//            // 更新AGV状态
//            taskService.updateAgvState(agvNo, positionDb,energyLevel);
//        }
//        Map map = new HashMap<>();
//        map.put("msg",result2);
//        return  null;
//    }



    @GetMapping("/newTaskDistribution")
    public Map<String, Object> newTaskDistribution() {
        String url = "http://192.168.2.2:8086/api/HD/newTaskDistribution";
        Map paramMap = new HashMap<>();
//        {
//            "MissionUid": "xxxxxxx",     //任务ID
//                "StationName": "xxxxxx",   //目标工位名称
//                "AGVNum": 1,   //指定车辆，0：不指定，1-4指定车辆
//                "Balance": true      //是否调平
//        }
        paramMap.put("MissionUid", "01-005");
        paramMap.put("StationName", "L1");
        paramMap.put("AGVNum", 0);
        paramMap.put("Balance", true);
        String result2 = HttpRequest.post(url)
                .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        Console.log(result2);
        Map map = new HashMap<>();
        map.put("msg",result2);
        return map;
    }



    /**
     * AGV任务状态上报接口
     */
    @PostMapping("/missionState")
    public ApiResponse reportMissionState(@RequestBody String jsonString) {
        try {
            System.out.println("接收到的JSON数据：" + jsonString);
            // 将JSON字符串转换为实体类
            AgvMissionRequest request = JSONUtil.toBean(jsonString, AgvMissionRequest.class);
            
            // 1. 打印接收的上报信息（实际应用中可根据需要处理）
            System.out.println("收到AGV任务上报 - 任务ID: " + request.getMissionUid() + 
                               ", 当前位置: " + request.getCurrentLocation() + 
                               ", 状态: " + request.getState());

            // 2. 业务逻辑处理
//           根据MissionUid更新任务状态
            taskService.updateAgvTaskState(request.getMissionUid(), request.getState());

            //2.5  根据任务id   查出来,如果是010,装置状态改为装配中 1  装配完成2
            BjTask bjTask = bjTaskService.selectBjTaskById(Long.parseLong(request.getMissionUid().trim()));

            if(bjTask.getSignNo().equals("010")){
                taskService.updateZpStatus(bjTask.getAgvNo(), request.getState());
            }

            // 3. 构建成功响应
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("上报成功", "任务状态已更新");
            Console.log(resultMap);
            return new ApiResponse(0, "", resultMap);
            
        } catch (Exception e) {
            // 4. 处理异常，返回错误响应
            return new ApiResponse(1, "处理失败: " + e.getMessage(), null);
        }
    }








































    /**
     * 响应结果内部类
     */
    static class ApiResponse {
        private int resFlag;
        private String errMsg;
        private Object result;

        public ApiResponse(int resFlag, String errMsg, Object result) {
            this.resFlag = resFlag;
            this.errMsg = errMsg;
            this.result = result;
        }

        // Getters and Setters
        public int getResFlag() {
            return resFlag;
        }

        public void setResFlag(int resFlag) {
            this.resFlag = resFlag;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }
    }

    /**
     * 请求参数内部类
     */
    static class AgvMissionRequest {


        @NotBlank(message = "MissionUid不能为空")
        private String MissionUid;

        @NotBlank(message = "CurrentLocation不能为空")
        private String CurrentLocation;

        @NotNull(message = "State不能为空")
        private Integer State;

        private Boolean Balance;
        private String ErrorMsg;

        // Getters and Setters
        public String getMissionUid() {
            return MissionUid;
        }

        public void setMissionUid(String missionUid) {
            MissionUid = missionUid;
        }

        public String getCurrentLocation() {
            return CurrentLocation;
        }

        public void setCurrentLocation(String currentLocation) {
            CurrentLocation = currentLocation;
        }

        public Integer getState() {
            return State;
        }

        public void setState(Integer state) {
            State = state;
        }

        public Boolean getBalance() {
            return Balance;
        }

        public void setBalance(Boolean balance) {
            Balance = balance;
        }

        public String getErrorMsg() {
            return ErrorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            ErrorMsg = errorMsg;
        }
    }
}
