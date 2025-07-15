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
     * 拆分任务编号为两个字段，如：01-001 → agv_no=01, sign_no=001
     */
    public static Map<String, String> splitTaskNo(String taskNo) {
        String[] parts = taskNo.split("-");
        Map<String, String> result = new HashMap<>();
        result.put("agv_no", parts[0]);
        result.put("sign_no", parts[1]);
        return result;
    }


    /**
     * 拆分任务编号为三个字段，如：01-010.2 → agv_no=01, task_no=010, sign_no=2
     */
    public static Map<String, String> splitComplexTaskNo(String taskNo) {
        Map<String, String> result = new HashMap<>();

        String[] mainParts = taskNo.split("-");
        result.put("agv_no", mainParts[0]);

        String[] subParts = mainParts[1].split("\\.");
        result.put("task_no", subParts[0]);

        if (subParts.length > 1) {
            result.put("sign_no", subParts[1]);
        } else {
            result.put("sign_no", ""); // 如果没有小数点部分，则为空
        }

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
    * 使用方式示例   2个字段
    Map<String, String> taskFields = BjUtil.splitTaskNo("01-001");
    System.out.println("Field1: " + taskFields.get("field1")); // 输出: 01
    System.out.println("Field2: " + taskFields.get("field2")); // 输出: 001
    * */


    /*   三个字段
    Map<String, String> fields = BjUtil.splitComplexTaskNo("01-010.2");
System.out.println("agv_no: " + fields.get("agv_no"));   // 输出: 01
System.out.println("task_no: " + fields.get("task_no")); // 输出: 010
System.out.println("sign_no: " + fields.get("sign_no")); // 输出: 2
    */


//    任务号有001，002，003，005，010，015，020，025，030，035，040，045，050，055，060，065，070，075，080这19个
    // 当某AGV任务号执行到080,且080执行状态为2的时候,删除该AGV的所有任务
}
