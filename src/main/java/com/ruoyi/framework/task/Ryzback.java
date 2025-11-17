package com.ruoyi.framework.task;

public class Ryzback {

    // 或者通过配置文件读取
    // @Value("${station.name.l5}")
    // private String stationNameL5;





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
}
