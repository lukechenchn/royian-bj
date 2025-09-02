package com.ruoyi.project.system.xfvisual.api.ommkAgv;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * AGV任务状态上报接口控制器
 */
@RestController
@RequestMapping("/api")
public class AgvMissionReportController {

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
     * AGV任务状态上报接口
     */
    @PostMapping("/MissionState")
    public ApiResponse reportMissionState(@RequestBody AgvMissionRequest request) {
        try {
            // 1. 打印接收的上报信息（实际应用中可根据需要处理）
            System.out.println("收到AGV任务上报 - 任务ID: " + request.getMissionUid() + 
                               ", 当前位置: " + request.getCurrentLocation() + 
                               ", 状态: " + request.getState());

            // 2. 业务逻辑处理（根据实际需求实现）
            // 例如：更新数据库中的任务状态
            // 这里可以添加你的业务代码

            // 3. 构建成功响应
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("上报成功", "任务状态已更新");
            return new ApiResponse(0, "", resultMap);
            
        } catch (Exception e) {
            // 4. 处理异常，返回错误响应
            return new ApiResponse(1, "处理失败: " + e.getMessage(), null);
        }
    }
}
