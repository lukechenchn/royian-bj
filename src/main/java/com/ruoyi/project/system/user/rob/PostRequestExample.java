package com.ruoyi.project.system.user.rob;

import com.alibaba.fastjson.JSON;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PostRequestExample {

    public static void main(String[] args) {
        try {
            // 目标URL（替换为你的实际地址）
            String url = "192.168.1.209:8081/get_air_wg";

            // 创建请求数据
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("key1", "value1");
            requestData.put("key2", "value2");

            // 将Map转换为JSON字符串
            String jsonInputString = JSON.toJSONString(requestData);

            // 创建URL对象和连接
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // 设置请求方法和参数
            con.setRequestMethod("POST");
            con.setDoOutput(true); // 启用输出流
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // 发送请求体
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 获取响应状态码
            int responseCode = con.getResponseCode();
            System.out.println("响应码: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
