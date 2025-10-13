//package com.ruoyi.project.system.xfvisual.api.plc.iotcom;
//
////import com.github.xingshuangs.iot.exceptions.PlcException;
//import com.github.xingshuangs.iot.protocol.s7.service.S7PLC;
//import com.github.xingshuangs.iot.protocol.s7.enums.EPlcType;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//
///**
// * S7PLC 核心功能测试类
// * 覆盖：连接、常用数据类型读写、PLC控制、异常处理
// */
//public class S7PLCTest {
//    // -------------------------- 配置参数（根据实际设备修改）--------------------------
//    // PLC型号（S7_1200/S7_300/S200_SMART）
//    private static final EPlcType PLC_TYPE = EPlcType.S1200;
//    // PLC IP地址
//    private static final String PLC_IP = "192.168.0.1";
//    // 机架号（S7-1200默认0，S7-300默认0）
//    private static final int RACK = 0;
//    // 插槽号（S7-1200默认1，S7-300默认2）
//    private static final int SLOT = 1;
//    // 测试用DB块编号（确保PLC中存在该DB块，且地址未被占用）
//    private static final int TEST_DB_NUMBER = 1;
//
//    public static void main(String[] args) {
//        S7PLC plc = null;
//        try {
//            // 1. 初始化PLC连接（带重连逻辑）
//            plc = initPlcConnection();
//            if (plc == null) {
//                System.err.println("PLC连接初始化失败，退出测试");
//                return;
//            }
//            System.out.println("=== PLC连接成功 ===");
//
//            // 2. 测试：读写常用数据类型（基于TEST_DB_NUMBER块）
//            testCommonDataTypeReadWrite(plc);
//
//            // 3. 测试：读写特殊数据类型（时间/日期）
//            testSpecialDataTypeReadWrite(plc);
//
//            // 4. 测试：PLC控制功能（可选，谨慎执行！）
//            // 注意：stop/restart会影响PLC运行，仅在测试环境执行
//            // testPlcControl(plc);
//
//        } catch (Exception e) {
//            System.err.println("PLC通信异常：" + e.getMessage());
//        } finally {
//            // 5. 关闭PLC连接（无论成功失败，确保资源释放）
//            if (plc != null && plc.checkConnected()) {
//                plc.close();
//
//                System.out.println("\n=== PLC连接已断开 ===");
//            }
//        }
//    }
//
//    /**
//     * 初始化PLC连接（带3次重连逻辑）
//     */
//    private static S7PLC initPlcConnection() {
//        S7PLC plc = new S7PLC(PLC_TYPE, PLC_IP, 102, RACK, SLOT);
//        int retryCount = 3; // 最大重连次数
//        while (retryCount > 0) {
//            try {
//                if (plc.checkConnected()) {
//                    return plc;
//                }
//            } catch (Exception e) {
//                System.err.println("第" + (4 - retryCount) + "次连接失败：" + e.getMessage());
//            }
//            retryCount--;
//            // 重连间隔1秒
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 测试：常用数据类型读写（位、字节、整数、浮点数、字符串）
//     */
//    private static void testCommonDataTypeReadWrite(S7PLC plc) {
//        System.out.println("\n=== 开始测试常用数据类型读写 ===");
//        String dbPrefix = "DB" + TEST_DB_NUMBER + ".";
//
//        // -------------------------- 1. 位（Bool）读写 --------------------------
//        String boolAddr = dbPrefix + "DBX1.0"; // DB1.DBX1.0
//        boolean writeBool = true;
//        plc.writeBoolean(boolAddr, writeBool);
//        boolean readBool = plc.readBoolean(boolAddr);
//        System.out.println("位（" + boolAddr + "）：写入=" + writeBool + "，读取=" + readBool + "，结果" + (writeBool == readBool ? "一致" : "不一致"));
//
//        // -------------------------- 2. 字节（Byte）读写 --------------------------
//        String byteAddr = dbPrefix + "DBB2"; // DB1.DBB2
//        byte writeByte = (byte) 0x1A; // 十六进制1A
//        plc.writeByte(byteAddr, writeByte);
//        byte readByte = plc.readByte(byteAddr);
//        System.out.println("字节（" + byteAddr + "）：写入=0x" + Integer.toHexString(writeByte & 0xFF) + "，读取=0x" + Integer.toHexString(readByte & 0xFF) + "，结果" + (writeByte == readByte ? "一致" : "不一致"));
//
//        // -------------------------- 3. 16位整数（Int16）读写 --------------------------
//        String int16Addr = dbPrefix + "DBW3"; // DB1.DBW3（占2字节：DBB3+DBB4）
//        short writeInt16 = (short) 1234;
//        plc.writeInt16(int16Addr, writeInt16);
//        short readInt16 = plc.readInt16(int16Addr);
//        System.out.println("16位整数（" + int16Addr + "）：写入=" + writeInt16 + "，读取=" + readInt16 + "，结果" + (writeInt16 == readInt16 ? "一致" : "不一致"));
//
//        // -------------------------- 4. 32位浮点数（Float32）读写 --------------------------
//        String float32Addr = dbPrefix + "DBD5"; // DB1.DBD5（占4字节：DBB5-DBB8）
//        float writeFloat32 = 3.1415f;
//        plc.writeFloat32(float32Addr, writeFloat32);
//        float readFloat32 = plc.readFloat32(float32Addr);
//        // 浮点数比较：允许微小误差（0.0001）
//        boolean floatEqual = Math.abs(writeFloat32 - readFloat32) < 0.0001;
//        System.out.println("32位浮点数（" + float32Addr + "）：写入=" + writeFloat32 + "，读取=" + readFloat32 + "，结果" + (floatEqual ? "一致" : "不一致"));
//
//        // -------------------------- 5. 字符串（String）读写 --------------------------
//        String stringAddr = dbPrefix + "DBB9"; // DB1.DBB9（字符串起始地址，占N+2字节）
//        String writeString = "Test from Java";
//        plc.writeString(stringAddr, writeString);
//        String readString = plc.readString(stringAddr, writeString.length()); // 指定读取长度
//        System.out.println("字符串（" + stringAddr + "）：写入=" + writeString + "，读取=" + readString + "，结果" + (writeString.equals(readString) ? "一致" : "不一致"));
//    }
//
//    /**
//     * 测试：特殊数据类型读写（时间、日期、日期时间）
//     */
//    private static void testSpecialDataTypeReadWrite(S7PLC plc) {
//        System.out.println("\n=== 开始测试特殊数据类型读写 ===");
//        String dbPrefix = "DB" + TEST_DB_NUMBER + ".";
//
//        // -------------------------- 1. 时间（毫秒）读写 --------------------------
//        String timeAddr = dbPrefix + "DBD20"; // DB1.DBD20（4字节，存储毫秒）
//        long writeTime = 5000; // 5秒
//        plc.writeTime(timeAddr, writeTime);
//        long readTime = plc.readTime(timeAddr);
//        System.out.println("时间（" + timeAddr + "）：写入=" + writeTime + "ms，读取=" + readTime + "ms，结果" + (writeTime == readTime ? "一致" : "不一致"));
//
//        // -------------------------- 2. 日期（LocalDate）读写 --------------------------
//        String dateAddr = dbPrefix + "DBW24"; // DB1.DBW24（2字节，存储1990-01-01起的天数）
//        LocalDate writeDate = LocalDate.now();
//        plc.writeDate(dateAddr, writeDate);
//        LocalDate readDate = plc.readDate(dateAddr);
//        System.out.println("日期（" + dateAddr + "）：写入=" + writeDate + "，读取=" + readDate + "，结果" + (writeDate.equals(readDate) ? "一致" : "不一致"));
//
//        // -------------------------- 3. 日期时间（LocalDateTime）读写 --------------------------
//        String dtlAddr = dbPrefix + "DBB26"; // DB1.DBB26（12字节，存储完整日期时间）
//        LocalDateTime writeDtl = LocalDateTime.now();
//        plc.writeDTL(dtlAddr, writeDtl);
//        LocalDateTime readDtl = plc.readDTL(dtlAddr);
//        // 日期时间比较：忽略纳秒（PLC存储精度可能不足）
//        boolean dtlEqual = writeDtl.withNano(0).equals(readDtl.withNano(0));
//        System.out.println("日期时间（" + dtlAddr + "）：写入=" + writeDtl + "，读取=" + readDtl + "，结果" + (dtlEqual ? "一致" : "不一致"));
//    }
//
//    /**
//     * 测试：PLC控制功能（谨慎执行！）
//     * 包含：停止PLC、热重启、冷重启（会影响设备运行，仅测试环境用）
//     */
//    private static void testPlcControl(S7PLC plc) {
//        System.out.println("\n=== 开始测试PLC控制功能（谨慎执行！） ===");
//        try {
//            // 1. 读取当前PLC状态（模拟：实际需结合PLC状态寄存器，此处仅示例）
//            System.out.println("当前PLC状态：已连接（" + plc.checkConnected() + "）");
//
//            // 2. 停止PLC（注释：执行后PLC进入STOP模式，需手动重启）
//            // System.out.println("执行PLC停止...");
//            // plc.plcStop();
//            // Thread.sleep(2000); // 等待停止生效
//            // System.out.println("PLC停止后状态：已连接（" + plc.isConnected() + "）");
//
//            // 3. 热重启PLC（注释：需先确保PLC处于STOP模式）
//            // System.out.println("执行PLC热重启...");
//            // plc.hotRestart();
//            // Thread.sleep(5000); // 等待重启生效
//            // System.out.println("PLC热重启后状态：已连接（" + plc.isConnected() + "）");
//
//        } catch (Exception e) {
//            Thread.currentThread().interrupt();
//            System.err.println("PLC控制等待异常：" + e.getMessage());
//        }
//    }
//}