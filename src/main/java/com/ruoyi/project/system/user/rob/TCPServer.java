package com.ruoyi.project.system.user.rob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


//服务端
public class TCPServer {
    private static final int PORT = 9999;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("服务器启动，监听端口: " + PORT);

            while (true) {
                // 等待客户端连接
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {


                    System.out.println("客户端连接成功: " + clientSocket.getInetAddress());

                    // 读取客户端发送的数据
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
//                        System.out.println("收到客户端消息: " + inputLine);
                        System.out.println(inputLine);
                        // 处理数据（这里可以添加你的业务逻辑）
                        String response = processData(inputLine);
                        
                        // 发送响应给客户端
                        out.println(response);
                    }
                } catch (IOException e) {
//                    System.err.println("处理客户端连接时出错: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("服务器启动失败: " + e.getMessage());
        }
    }

    // 处理接收到的数据（示例方法，可根据需求修改）
    private static String processData(String data) {
        // 这里可以添加具体的数据处理逻辑
        return "服务器已收到: " + data;
    }
}    