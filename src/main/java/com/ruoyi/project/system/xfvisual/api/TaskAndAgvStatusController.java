package com.ruoyi.project.system.xfvisual.api;

import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.project.system.task.domain.BjTask;
import com.ruoyi.project.system.task.service.impl.BjTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.service.ApiTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.util.BjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 模拟生成任务结果的接口
//    @PostMapping("/tasks_with_result")
//    public ResponseEntity<Map<String, Object>> createTaskWithResult(@RequestBody Map<String, String> data) {
//        System.out.println("\n[服务器日志] 收到 POST /api/tasks_with_result 请求");
//
//        if (data.get("task_no") == null || data.get("result") == null) {
//            System.out.println("[服务器日志] 错误：缺少必要字段 (task_no, result)");
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Missing required fields (task_no, result)");
//            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//        }
//
//        System.out.println("[服务器日志] 客户端发送的 JSON 数据: " + data);
//
////        逻辑
//        String taskNo = "01-005";
//        Map<String, String> fields = BjUtil.splitTaskNo(taskNo);
//        String agvNo = fields.get("agv_no");       // "01"
//        String taskNoOnly = fields.get("task_no"); // "005"
//
//        if (canExecuteCurrentTask(agvNo, taskNoOnly)) {
//            System.out.println("当前任务可以执行");
//        } else {
//            System.out.println("当前任务不能执行，前一个任务未完成");
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", "01");
//        response.put("message", "接收收到");
//        response.put("task_no", data.get("task_no"));
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    // 模拟创建任务并返回执行结果的接口
//    @PostMapping("/tasks")
//    public ResponseEntity<Map<String, Object>> createTask(@RequestBody Map<String, String> data) {
//        System.out.println("\n[服务器日志] 收到 POST /api/tasks 请求");
//
//        if (data.get("task_no") == null) {
//            System.out.println("[服务器日志] 错误：缺少必要字段 (task_no)");
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Missing required fields (task_no)");
//            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//        }
//        System.out.println("[服务器日志] 客户端发送的 JSON 数据: " + data);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("status_code", "01");
//        response.put("message", "接收收到");
//        response.put("task_no", "01-005");
//
//        Map<String, Object> taskResponse = new HashMap<>();
//        taskResponse.put("task_status", 2);
//        taskResponse.put("time_stamp", "1751799627");
//        taskResponse.put("time_consumption", "20s");
//        taskResponse.put("remark", "执行完成");
//
//        response.put("response", taskResponse);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    // AGV状态数据模拟
//    private static final List<Map<String, String>> agvStatusData = new ArrayList<>();
//
//    static {
//        Map<String, String> agv1 = new HashMap<>();
//        agv1.put("agv_no", "01");
//        agv1.put("agv_status", "2");
//        agv1.put("task_no", "xx-001-1.1.1");
//        agv1.put("battery_level", "75%");
//        agv1.put("current_position", "坐标A(100,200)");
//        agv1.put("time_stamp", "1751799627");
//        agv1.put("oil_quantity", "80L");
//        agv1.put("model", "Type-A");
//        agv1.put("quantity", "4枚");
//        agv1.put("container_status", "已开门");
//        agvStatusData.add(agv1);
//
//        Map<String, String> agv2 = new HashMap<>();
//        agv2.put("agv_no", "02");
//        agv2.put("agv_status", "1");
//        agv2.put("task_no", "xx-002-1.1.2");
//        agv2.put("battery_level", "90%");
//        agv2.put("current_position", "坐标B(150,220)");
//        agv2.put("time_stamp", "1751799627");
//        agv2.put("oil_quantity", "95L");
//        agv2.put("model", "Type-B");
//        agv2.put("quantity", "2枚");
//        agv2.put("container_status", "已开门");
//        agvStatusData.add(agv2);
//
//        Map<String, String> agv3 = new HashMap<>();
//        agv3.put("agv_no", "03");
//        agv3.put("agv_status", "3");
//        agv3.put("task_no", "xx-003-1.1.3");
//        agv3.put("battery_level", "60%");
//        agv3.put("current_position", "坐标C(80,180)");
//        agv3.put("time_stamp", "1751799627");
//        agv3.put("oil_quantity", "65L");
//        agv3.put("model", "Type-A");
//        agv3.put("quantity", "0枚");
//        agv3.put("container_status", "已开门");
//        agvStatusData.add(agv3);
//    }

    // 获取AGV状态信息接口
//    @GetMapping("/agv_status")
//    public ResponseEntity<Map<String, Object>> getAgvStatus(@RequestParam(required = false) String agvNo) {
//        System.out.println("\n[服务器日志] 收到 GET /api/agv_status 请求");
//
//        try {
//            Map<String, Object> response = new HashMap<>();
//            if (agvNo != null && !agvNo.isEmpty()) {
//                List<Map<String, String>> matchedAgvs = agvStatusData.stream()
//                        .filter(agv -> agv.get("agv_no").equals(agvNo))
//                        .collect(Collectors.toList());
//
//                if (!matchedAgvs.isEmpty()) {
//                    response.put("status_code", "01");
//                    response.put("message", "查询成功");
//                    response.put("response", matchedAgvs);
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                } else {
//                    response.put("status_code", "04");
//                    response.put("message", "未找到AGV号为 " + agvNo + " 的状态信息");
//                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//                }
//            } else {
//                response.put("status_code", "01");
//                response.put("message", "查询成功");
//                response.put("response", agvStatusData);
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("status_code", "05");
//            errorResponse.put("message", "服务器内部错误: " + e.getMessage());
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }





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
        if(signNo.equals("040") ||  signNo.equals("045")){
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
        String taskNo = taskFields.get("task_no");
        System.out.println("agv_no: " + taskFields.get("agv_no")); // 输出: 01
        System.out.println("task_no: " + taskFields.get("task_no")); // 输出: 001


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

        if(taskNo.equals("001")){
            taskService.updateRemark1OfAgv(agvNo,1);
        }
        if(taskNo.equals("085")){
            //todo 对接agv 此任务执行完成后更新已回库
            taskService.updateRemark1OfAgv(agvNo,0);
        }

        /**处理油弹数量逻辑*/
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

        String dType = data.get("d_type");
        if (dType != null && !dType.isEmpty()) {

            String dNumStr = data.get("d_num");
            if (dNumStr != null && !dNumStr.isEmpty()) {
               int dNum =   Integer.parseInt(dNumStr);
            if (dType.equals("AR-1")) {
                //更新挂弹库总数
                taskService.updateOilContainerD1ByTask(dNum);
            } else if (dType.equals("LT-2")) {
                //更新挂弹库总数
                taskService.updateOilContainerD2ByTask(dNum);
            } else if (dType.equals("PL-16")) {
                //更新挂弹库总数
                taskService.updateOilContainerD3ByTask(dNum);
            }

            }
        }




        genTaskService.insertBjTask(bjTask);
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
     */






}
