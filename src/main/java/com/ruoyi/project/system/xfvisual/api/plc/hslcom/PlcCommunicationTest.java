package com.ruoyi.project.system.xfvisual.api.plc.hslcom;


import HslCommunication.Core.Types.OperateResult;
import HslCommunication.Core.Types.OperateResultExOne;
import HslCommunication.Profinet.Siemens.SiemensPLCS;
import HslCommunication.Profinet.Siemens.SiemensS7Net;
//没用工具类，此单文件可以执行
public class PlcCommunicationTest {
    public static void main(String[] args) {
        // 创建PLC连接对象，这里以西门子S7-1200/1500为例
        // 如果是其他品牌PLC，请使用对应的类，如三菱的MelsecFxNet等
        SiemensS7Net siemensS7Net = new SiemensS7Net(SiemensPLCS.S1200, "192.168.0.1");

        // 设置连接参数
        siemensS7Net.setPort(102); // 设置端口号，默认102

        // 连接PLC
        OperateResult connectResult = siemensS7Net.ConnectServer();
        if (!connectResult.IsSuccess) {
            System.out.println("连接PLC失败：" + connectResult.Message);
            return;
        }
        System.out.println("连接PLC成功！");

        try {
            // 读取单个bool值 (例如：Q0.0)
            OperateResultExOne<Boolean> boolResult = siemensS7Net.ReadBool("Q0.0");
            if (boolResult.IsSuccess) {
                System.out.println("Q0.0的值：" + boolResult.Content);
            } else {
                System.out.println("读取Q0.0失败：" + boolResult.Message);
            }

            // 写入单个bool值 (例如：Q0.1)
            OperateResult writeBoolResult = siemensS7Net.Write("Q0.1", true);
            if (writeBoolResult.IsSuccess) {
                System.out.println("写入Q0.1成功");
            } else {
                System.out.println("写入Q0.1失败：" + writeBoolResult.Message);
            }

            // 读取int值 (例如：DB1.DBW0)
            OperateResultExOne<Short> intResult = siemensS7Net.ReadInt16("DB1.DBW0");
            if (intResult.IsSuccess) {
                System.out.println("DB1.DBW0的值：" + intResult.Content);
            } else {
                System.out.println("读取DB1.DBW0失败：" + intResult.Message);
            }

            // 写入int值 (例如：DB1.DBW2)
            OperateResult writeIntResult = siemensS7Net.Write("DB1.DBW2", (short) 1234);
            if (writeIntResult.IsSuccess) {
                System.out.println("写入DB1.DBW2成功");
            } else {
                System.out.println("写入DB1.DBW2失败：" + writeIntResult.Message);
            }

            // 读取float值 (例如：DB1.DBD4)
            OperateResultExOne<Float> floatResult = siemensS7Net.ReadFloat("DB1.DBD4");
            if (floatResult.IsSuccess) {
                System.out.println("DB1.DBD4的值：" + floatResult.Content);
            } else {
                System.out.println("读取DB1.DBD4失败：" + floatResult.Message);
            }

            // 写入float值 (例如：DB1.DBD8)
            OperateResult writeFloatResult = siemensS7Net.Write("DB1.DBD8", 3.14f);
            if (writeFloatResult.IsSuccess) {
                System.out.println("写入DB1.DBD8成功");
            } else {
                System.out.println("写入DB1.DBD8失败：" + writeFloatResult.Message);
            }

        } finally {
            // 断开连接
            siemensS7Net.ConnectClose();
            System.out.println("已断开与PLC的连接");
        }
    }
}
