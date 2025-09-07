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
        } catch (Exception e) {
            System.err.println("删除 AGV " + agvNo + " 的任务失败：" + e.getMessage());
        }
    }




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
            Integer x = 0, y = 0, z = 0;

            if (positionObj != null) {
                try {
                    if (positionObj instanceof Map) {
                        // 如果position是Map类型
                        Map<String, Object> position = (Map<String, Object>) positionObj;
                        x = ((Number) position.get("x")).intValue();
                        y = ((Number) position.get("y")).intValue();
                        z = ((Number) position.get("z")).intValue();
                    } else if (positionObj instanceof List) {
                        // 如果position是数组类型
                        List<Object> positionList = (List<Object>) positionObj;
                        if (positionList.size() >= 3) {
                            x = ((Number) positionList.get(0)).intValue();
                            y = ((Number) positionList.get(1)).intValue();
                            z = ((Number) positionList.get(2)).intValue();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("解析position数据时出错: " + e.getMessage());
                }
            }

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


    public static void main(String[] args) throws IOException {
        String url = "http://192.168.2.2:8086/api/HD/QueryAGVsystem";
        JSONObject paramMap = new JSONObject();
        paramMap.put("Task", "AGVInfo");

        String result2 = BjUtil.postJson(url,paramMap.toString());
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

        System.out.println("\n=== AGV车辆列表 ===");
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
            Integer x = 0, y = 0, z = 0;

            if (positionObj != null) {
                try {
                    if (positionObj instanceof Map) {
                        // 如果position是Map类型
                        Map<String, Object> position = (Map<String, Object>) positionObj;
                        x = ((Number) position.get("x")).intValue();
                        y = ((Number) position.get("y")).intValue();
                        z = ((Number) position.get("z")).intValue();
                    } else if (positionObj instanceof List) {
                        // 如果position是数组类型
                        List<Object> positionList = (List<Object>) positionObj;
                        if (positionList.size() >= 3) {
                            x = ((Number) positionList.get(0)).intValue();
                            y = ((Number) positionList.get(1)).intValue();
                            z = ((Number) positionList.get(2)).intValue();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("解析position数据时出错: " + e.getMessage());
                }
            }

            String positionDb = "(" + x + ", " + y + ", " + z + ")";
            System.out.println(positionDb);

            // 打印AGV信息
            System.out.println("\nAGV车号：" + vehicleName);
            System.out.println("角度：" + angle + "°");
            System.out.println("电量：" + energyLevel + "%");
            System.out.println("故障码：" + faultCode);
            System.out.println("告警码：" + alarmCode);
            System.out.println("坐标：x=" + x + ", y=" + y + ", z=" + z);
        }
    }


















    //定时反馈任务状态
    public void ryParams()
    {
        //检查在执行中的任务
        //todo   暂时想不起来这个定时任务是干嘛的

        System.out.println("bj定时任务");
//        System.out.println(list.toString());
    }

}
