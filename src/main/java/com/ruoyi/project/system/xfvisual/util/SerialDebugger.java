package com.ruoyi.project.system.xfvisual.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.TitledBorder;

// 使用jSerialComm库
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SerialDebugger extends JFrame {
    private JComboBox<String> portComboBox;
    private JComboBox<String> baudRateComboBox;
    private JButton connectButton;
    private JButton sendButton;
    private JTextArea receiveArea;
    private JTextField sendField;
    private JPanel controlPanel;
    private JPanel sendPanel;
    private JScrollPane scrollPane;

    private SerialPort serialPort;
    private InputStream input;
    private OutputStream output;

    private static final String[] BAUD_RATES = {"9600", "19200", "38400", "57600", "115200"};

    public SerialDebugger() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        populatePortList();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("极简串口调试工具");
        setSize(600, 400);
        setLocationRelativeTo(null);

        // 添加窗口关闭监听器，确保串口正确关闭
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeSerialPort();
            }
        });
    }

    private void initializeComponents() {
        portComboBox = new JComboBox<>();
        baudRateComboBox = new JComboBox<>(BAUD_RATES);
        baudRateComboBox.setSelectedItem("57600"); // 默认设置为扫码枪常用的57600
        connectButton = new JButton("连接");
        sendButton = new JButton("发送");
        receiveArea = new JTextArea(15, 50);
        receiveArea.setEditable(false);
        sendField = new JTextField(40);
        scrollPane = new JScrollPane(receiveArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        controlPanel = new JPanel(new FlowLayout());
        sendPanel = new JPanel(new FlowLayout());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // 控制面板
        controlPanel.setBorder(new TitledBorder("串口设置"));
        controlPanel.add(new JLabel("串口:"));
        controlPanel.add(portComboBox);
        controlPanel.add(new JLabel("波特率:"));
        controlPanel.add(baudRateComboBox);
        controlPanel.add(connectButton);

        // 发送面板
        sendPanel.setBorder(new TitledBorder("数据发送"));
        sendPanel.add(new JLabel("数据:"));
        sendPanel.add(sendField);
        sendPanel.add(sendButton);

        // 主面板布局
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.NORTH);
        topPanel.add(sendPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        // 连接按钮事件
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connectButton.getText().equals("连接")) {
                    connectToSerialPort();
                } else {
                    closeSerialPort();
                }
            }
        });

        // 发送按钮事件
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendSerialData();
            }
        });

        // 回车发送数据
        sendField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendSerialData();
            }
        });
    }

    private void populatePortList() {
        portComboBox.removeAllItems();
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            portComboBox.addItem(port.getSystemPortName() + " - " + port.getDescriptivePortName());
        }
    }

    private void connectToSerialPort() {
        String selectedItem = (String) portComboBox.getSelectedItem();
        if (selectedItem == null || selectedItem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请选择有效的串口", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 提取端口名称（去除描述部分）
        String portName = selectedItem.split(" - ")[0];
        int baudRate = Integer.parseInt((String) baudRateComboBox.getSelectedItem());

        try {
            // 获取串口
            serialPort = SerialPort.getCommPort(portName);

            // 设置串口参数：57600波特率，8数据位，1停止位，无校验
            serialPort.setComPortParameters(baudRate, 8, 1, 0);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 100, 0);

            // 打开串口
            if (!serialPort.openPort()) {
                throw new RuntimeException("无法打开串口");
            }

            // 设置输入输出流
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();

            // 添加事件监听器
            serialPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                        readSerialData();
                    }
                }
            });

            // 更新界面状态
            connectButton.setText("断开");
            portComboBox.setEnabled(false);
            baudRateComboBox.setEnabled(false);

            receiveArea.append("已连接到 " + portName + " @ " + baudRate + "bps\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "连接失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void readSerialData() {
        try {
            if (input.available() > 0) {
                byte[] buffer = new byte[input.available()];
                int bytesRead = input.read(buffer);
                if (bytesRead > 0) {
                    String receivedData = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                    SwingUtilities.invokeLater(() -> {
                        receiveArea.append(receivedData);
                        receiveArea.setCaretPosition(receiveArea.getDocument().getLength());
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("读取数据出错: " + e.getMessage());
        }
    }

    private void closeSerialPort() {
        if (serialPort != null && serialPort.isOpen()) {
            try {
                serialPort.removeDataListener();
                serialPort.closePort();
                if (input != null) input.close();
                if (output != null) output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            serialPort = null;
            connectButton.setText("连接");
            portComboBox.setEnabled(true);
            baudRateComboBox.setEnabled(true);
            receiveArea.append("\n串口已断开\n");
        }
    }

    private void sendSerialData() {
        if (serialPort == null || !serialPort.isOpen()) {
            JOptionPane.showMessageDialog(this, "请先连接串口", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String data = sendField.getText();
            output.write(data.getBytes(StandardCharsets.UTF_8));
            receiveArea.append("发送: " + data + "\n");
            sendField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "发送失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // 使用系统默认外观
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new SerialDebugger().setVisible(true);
            }
        });
    }
}
