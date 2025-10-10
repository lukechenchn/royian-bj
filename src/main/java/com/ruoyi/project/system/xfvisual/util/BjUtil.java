package com.ruoyi.project.system.xfvisual.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
//            "001", "002", "003", "005",
//            "010", "015", "020", "025",
//            "030", "035", "040", "045",
//            "050", "055", "060", "065",
//            "070", "075", "080", "085"

            "001", "002", "003", "005",
            "010", "015",
            "055", "060", "065", "070",
            "075", "080", "085"
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




    // 超时时间设置（毫秒）
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    /**
     * 发送JSON格式的POST请求
     * @param url 请求地址
     * @param jsonParam JSON格式参数字符串
     * @return 响应结果
     */
    public static String postJson(String url, String jsonParam) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            // 创建URL对象
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();

            // 设置请求方法和头部信息
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true); // 允许输出
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);

            // 发送JSON参数
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonParam.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 读取响应
            int responseCode = connection.getResponseCode();
            // 根据响应码选择输入流（成功或错误流）
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // 非成功响应状态码抛出异常
            if (responseCode >= 300) {
                throw new IOException("请求失败，状态码: " + responseCode + "，响应内容: " + response.toString());
            }

        } finally {
            // 关闭资源
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }
}
