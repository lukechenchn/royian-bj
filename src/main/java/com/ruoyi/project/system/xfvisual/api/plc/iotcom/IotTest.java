package com.ruoyi.project.system.xfvisual.api.plc.iotcom;

import com.github.xingshuangs.iot.protocol.s7.enums.EPlcType;
import com.github.xingshuangs.iot.protocol.s7.service.S7PLC;
import com.ruoyi.project.system.xfvisual.api.plc.HexConverter;

public class IotTest {
    public static void main(String[] args) {
        // 长连接方式，即持久化为true
        S7PLC s7PLC = new S7PLC(EPlcType.S1200, "127.0.0.1");
        s7PLC.writeByte("DB2.1", (byte) 0x11);
        s7PLC.readByte("DB2.1");
        // 需要手动关闭，若一直要使用，则不需要关闭
        s7PLC.close();

        // 字节转十六进制
        String hex = HexConverter.byteToHex((byte) 0x11); // 结果: "11"

        // 十六进制转字节
        byte b = HexConverter.hexToByte("11"); // 结果: 17

        // 十进制与十六进制转换
        String hexStr = HexConverter.decimalToHex(17); // 结果: "11"
        int decimal = HexConverter.hexToDecimal("11"); // 结果: 17

    }


}
