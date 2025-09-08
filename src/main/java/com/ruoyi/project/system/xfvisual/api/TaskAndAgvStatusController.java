package com.ruoyi.project.system.xfvisual.api;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.service.impl.BjTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.service.ApiTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.util.BjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import static com.ruoyi.project.system.xfvisual.util.BjUtil.getPreviousTaskNo;

@RestController
@Anonymous
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 允许跨域请求
public class TaskAndAgvStatusController {

    @Autowired
    private BjTaskServiceImpl genTaskService;

    @Autowired
    private ApiTaskServiceImpl taskService;




    /**最终接口1：任务接收下发*/
    /** 新增任务至数据库*/
    @PostMapping("/addTask")
    public ResponseEntity<Map<String, Object>> addTask(@RequestBody Map<String, String> data) {
        if (data.get("task_no") == null) {
            System.out.println("[服务器日志] 错误：缺少必要字段 (task_no)");
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Missing required fields (task_no)");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        System.out.println("[服务器日志] 客户端发送的 JSON 数据: " + data);
        String signNo = BjUtil.splitTaskNo(data.get("task_no")).get("sign_no");

        /* 020 - 050的任务都可以重复下发 */

        if(signNo.equals("020") || signNo.equals("025") || signNo.equals("030")
                || signNo.equals("035") || signNo.equals("040") ||
                signNo.equals("045") || signNo.equals("050")
        ){
            return addBjTaskDbData(data);
        }

        //检查当前AGV任务有没有重复
        int count  = taskService.selectCountByTaskNo(data.get("task_no"));
        if (count == 0){
            return addBjTaskDbData(data);
        }else {
            Map<String, Object> response = new HashMap<>();
            response.put("status_code", "error");
            response.put("message", "任务已经重复,不可重复下发");
            response.put("task_no", data.get("task_no")+".1");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    private ResponseEntity<Map<String, Object>> addBjTaskDbData(Map<String, String> data) {
        //任务没有重复  20250822去掉检查此任务是否重复，因为加油挂弹任务需要重复下发  20250827又加上了

        Map<String, String> taskFields = BjUtil.splitTaskNo(data.get("task_no"));
        String agvNo = taskFields.get("agv_no");
        String signNo = taskFields.get("sign_no");
        System.out.println("agv_no: " + taskFields.get("agv_no")); // 输出: 01
        System.out.println("sign_no: " + taskFields.get("sign_no")); // 输出: 001


        Map<String, Object> response = new HashMap<>();
        response.put("status_code", "01");
        response.put("message", "接收收到");
        response.put("task_no", data.get("task_no")+".1");

        long currentTimestamp = System.currentTimeMillis();
        Map<String, Object> taskResponse = new HashMap<>();
        //todo 废弃  检查当前AGV存不存在执行中的任务,存在则状态为0,不存在才返回1执行中
        taskResponse.put("task_status", 0);
        taskResponse.put("time_stamp", currentTimestamp);
//        taskResponse.put("time_consumption", "20s");
        taskResponse.put("remark", "未执行");

        //保存到数据库,状态为未执行
        BjTask bjTask = new BjTask();
        bjTask.setTaskNo(data.get("task_no"));
        bjTask.setAgvNo(taskFields.get("agv_no"));
        bjTask.setUavNo(taskFields.get("agv_no"));
        bjTask.setContainerNo(taskFields.get("agv_no"));
        bjTask.setSignNo(taskFields.get("sign_no"));
        bjTask.setSign(data.get("sign"));
        bjTask.setTaskStatus(0L);
        //新增加油、挂弹字段
        //“oil_ty”:
        //“oil_num”:
        //“D_type”
        //”D_num”
        bjTask.setOilType(data.get("oil_type"));
        bjTask.setOilNum(data.get("oil_num"));
        bjTask.setdType(data.get("d_type"));
        bjTask.setdNum(data.get("d_num"));


        /** 任务相关逻辑处理
         *  bj_agv_status 备注1: 是否出库0否1是
         * */

        if(signNo.equals("001")){
            taskService.updateRemark1OfAgv(agvNo,1);
        }
        if(signNo.equals("085")){
            //todo 对接agv 此任务执行完成后更新已回库
            taskService.updateRemark1OfAgv(agvNo,0);
        }

        /**处理油数量逻辑*/
        String oilType = data.get("oil_type");
        if (oilType != null && !oilType.isEmpty()) {
        }
        String oilNumStr = data.get("oil_num");
        BigDecimal oilNum = null;
        if (oilNumStr != null && !oilNumStr.isEmpty()) {
            // 先转换为BigDecimal，避免精度丢失
            BigDecimal originalNum = new BigDecimal(oilNumStr);
            // 保留两位小数（四舍五入）
            oilNum = originalNum.setScale(2, BigDecimal.ROUND_HALF_UP);
            //更新油弹库总数
            taskService.updateOilContainerOilByTask(oilNum);
        }

//        /**处理油数量逻辑  这块逻辑拿到045 */
//        String dType = data.get("d_type");
//        if (dType != null && !dType.isEmpty()) {
//            String dNumStr = data.get("d_num");
//            if (dNumStr != null && !dNumStr.isEmpty()) {
//               int dNum =   Integer.parseInt(dNumStr);
//            if (dType.equals("AR-1")) {
//                //更新挂弹库总数
//                taskService.updateOilContainerD1ByTask(dNum);
//            } else if (dType.equals("LT-2")) {
//                //更新挂弹库总数
//                taskService.updateOilContainerD2ByTask(dNum);
//            } else if (dType.equals("PL-16")) {
//                //更新挂弹库总数
//                taskService.updateOilContainerD3ByTask(dNum);
//            }
//
//            }
//        }

        int taskId = genTaskService.insertBjTask(bjTask);
        taskId = Integer.parseInt(bjTask.getId()+"");
        //需要下发给AGV的情况
        if(signNo.equals("001") || signNo.equals("002") || signNo.equals("003") ||
                signNo.equals("005") || signNo.equals("010")
                || signNo.equals("055") || signNo.equals("060")|| signNo.equals("075")
                || signNo.equals("080")
        ){
            String url = "http://192.168.2.2:8086/api/HD/NewTaskDistribution";
            JSONObject paramMap = new JSONObject();
//        {
//            "MissionUid": "xxxxxxx",     //任务ID
//                "StationName": "xxxxxx",   //目标工位名称
//                "AGVNum": 1,   //指定车辆，0：不指定，1-4指定车辆
//                "Balance": true      //是否调平
//        }
//        L0准备区  L1/L2起飞区  L3/L4弹射位   L5机库

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
                paramMap.put("Balance", true);
            }
            if(signNo.equals("080")){
                paramMap.put("StationName", "L5");
                paramMap.put("Balance", false);
            }
            paramMap.put("AGVNum", 1);

            String result2 = null;
            try {

                result2 = BjUtil.postJson(url,paramMap.toString());
//                 result2 = HttpRequest.post(url)
//                        .body(paramMap.toString())
//                        .execute()
//                        .body();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Console.log("result2====================",result2);

        }

        response.put("response", taskResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // 任务号白名单
    private static final Set<String> VALID_TASK_NUMBERS = new HashSet<>(Arrays.asList(
            "001", "002", "003", "005",
            "010", "015", "020", "025",
            "030", "035", "040", "045",
            "050", "055", "060", "065",
            "070", "075", "080", "085"
    ));

//    private static final Set<String> VALID_TASK_NUMBERS = new HashSet<>(Arrays.asList(
//            "001", "005",
//            "010", "015", "020", "025",
//            "030", "035", "040", "045",
//            "050", "055", "060", "065",
//            "070", "075", "080", "085"
//    ));

    //判断当前任务是否可以执行
    public boolean canExecuteCurrentTask(String agvNo, String currentTaskNo) {
        if (!VALID_TASK_NUMBERS.contains(currentTaskNo)) {
            throw new IllegalArgumentException("无效的任务编号: " + currentTaskNo);
        }

        String previousTaskNo = getPreviousTaskNo(currentTaskNo);

        System.out.println("上个任务短编号: " + previousTaskNo);
        // 如果没有前置任务，直接允许执行
        if (previousTaskNo == null) {
            return true;
        }

        // 检查前一个任务是否完成
        return isPreviousTaskCompleted(agvNo, previousTaskNo);
    }


    //    查询数据库中前一个任务是否完成（伪代码）
    public boolean isPreviousTaskCompleted(String agvNo, String previousTaskNo) {
        // 示例 SQL 查询：
        // SELECT COUNT(*) FROM task_table WHERE agv_no = ? AND task_no = ? AND status = 'completed'
        // 返回 true 表示已完成，false 表示未完成或不存在

        // 这里需要替换为你自己的 DAO 或 Mapper 查询逻辑
//        return taskMapper.isTaskCompleted(agvNo, previousTaskNo);
        int isCom = taskService.isTaskCompleted(agvNo, previousTaskNo);
        System.out.println(isCom);
        if(isCom == 0){
            return false;
        }else{
            return true;
        }
    }

    public static void main(String[] args) {
        String previousTaskNo = getPreviousTaskNo("080");
        // 如果没有前置任务，直接允许执行
        if (previousTaskNo == null) {
            System.out.println(true);
        }else{
            System.out.println(previousTaskNo);
        }
    }



    @PostMapping("/deleteTasks")
    public ResponseEntity<Map<String, Object>> deleteTasks(@RequestBody Map<String, String> data) {
        if (data.get("task_no") == null || data.get("result") == null) {
        }
        String taskNo = data.get("task_no");
        Map<String, String> fields = BjUtil.splitTaskNo(taskNo);
        String agvNo = fields.get("agv_no");
        String taskNoOnly = fields.get("task_no");
        if (canExecuteCurrentTask(agvNo, taskNoOnly)) {
            System.out.println("当前任务可以执行");

            // 判断是否是任务 080 且执行成功
            if ("080".equals(taskNoOnly) && "2".equals(data.get("result"))) {
                // 删除该 AGV 所有任务
//                taskService.deleteTasksByAgvNo(agvNo);
                System.out.println("AGV: " + agvNo + " 的所有任务已删除");
            }
        } else {
            System.out.println("当前任务不能执行，前一个任务未完成");
        }
        // 返回响应...
        return null;
    }



//"001":"出库"
//"005":"移至待命区"
//"010":"自主组装"
//"015":“电检"
//"020":"打开油弹库"
//"025":"油出库"
//"030":“"弹出库"
// 035":"油弹移至XX号无人机"
//"040":"加油XXL"，
//"045":"挂XX弹X枚"
//"050":"油弹AGV移出"
//"055":"移至TS准备区"
//"060":"举升至TS平台"
//"065":"移至TS位置"
//"070":"迁移装置装配"
//"075":"无人机AGV还原"
//"080":"移回机库"
//"085": "TS"


    /**
      20250902  在待命区升降模拟组装
     升降信号对接
     任务下发   工位
     任务反馈，配置url
     */






}
