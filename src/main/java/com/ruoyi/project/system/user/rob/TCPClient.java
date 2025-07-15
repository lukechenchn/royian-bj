package com.ruoyi.project.system.user.rob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//客户端
public class TCPClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9999;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("已连接到服务器: " + SERVER_HOST + ":" + SERVER_PORT);

            // 发送数据到服务器
            String message = generateSampleMessage();
            System.out.println("发送到服务器: " + message);
            out.println(message);

            // 读取服务器响应
            String response = in.readLine();
            System.out.println("服务器响应: " + response);

        } catch (IOException e) {
            System.err.println("与服务器通信时出错: " + e.getMessage());
        }
    }

    // 生成示例消息（可替换为实际数据）
    private static String generateSampleMessage() {
        // 这里生成符合你格式要求的消息
        return "Sample TCP Message";
    }
}    