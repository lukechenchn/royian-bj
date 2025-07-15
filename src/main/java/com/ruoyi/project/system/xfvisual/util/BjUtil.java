package com.ruoyi.project.system.xfvisual.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BjUtil {

    private BjUtil() {
        // 私有构造器，防止实例化
    }

    /**
     * 拆分任务编号为两个字段，如：01-001 → agv_no=01, task_no=001
     */
    public static Map<String, String> splitTaskNo(String taskNo) {
        String[] parts = taskNo.split("-");
        Map<String, String> result = new HashMap<>();
        result.put("agv_no", parts[0]);
        result.put("task_no", parts[1]);
        return result;
    }



    private static final List<String> TASK_SEQUENCE = Arrays.asList(
            "001", "002", "003", "005",
            "010", "015", "020", "025",
            "030", "035", "040", "045",
            "050", "055", "060", "065",
            "070", "075", "080"
    );

    public static String getPreviousTaskNo(String currentTaskNo) {
        int index = TASK_SEQUENCE.indexOf(currentTaskNo);
        if (index > 0) {
            return TASK_SEQUENCE.get(index - 1);
        }
        return null; // 当前任务已是第一个，无前置任务
    }


    /*
    *
    *
    * 使用方式示例
    Map<String, String> taskFields = BjUtil.splitTaskNo("01-001");
    System.out.println("Field1: " + taskFields.get("field1")); // 输出: 01
    System.out.println("Field2: " + taskFields.get("field2")); // 输出: 001
    * */


//    任务号有001，002，003，005，010，015，020，025，030，035，040，045，050，055，060，065，070，075，080这19个
}
