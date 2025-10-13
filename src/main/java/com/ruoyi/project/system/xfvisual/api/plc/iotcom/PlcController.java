//package com.ruoyi.project.system.xfvisual.api.plc.iotcom;
//
//import com.github.xingshuangs.iot.protocol.s7.enums.EPlcType;
//import com.github.xingshuangs.iot.protocol.s7.service.S7PLC;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
//
//@RestController
//public class PlcController {
//
//    private S7PLC s7PLC;
//
//    @PostConstruct
//    public void init() {
//        // 初始化时创建一次连接
//        this.s7PLC = new S7PLC(EPlcType.S1500, "127.0.0.1");
//    }
//
//    @RequestMapping("/plc-operation")
//    public String plcOperation() {
//        // 多次复用同一个连接实例
//        s7PLC.writeByte("DB2.1", (byte) 0x11);
//        byte result = s7PLC.readByte("DB2.1");
//        return "success："+result;
//    }
//
//    @PreDestroy
//    public void destroy() {
//        // 应用关闭时释放连接
//        if (s7PLC != null) {
//            s7PLC.close();
//        }
//    }
//
//
//    @RequestMapping("/plc-health")
//    public String checkPlcHealth() {
//        try {
//            // 检查连接是否存活
//            if (s7PLC.checkConnected()) {
//                // 尝试读取数据验证通信
//                Byte a = s7PLC.readByte("DB2.1");
//                System.out.println("PLC读取数据成功：" + a);
//                return "PLC通信正常";
//            } else {
//                return "PLC未连接";
//            }
//        } catch (Exception e) {
//            return "PLC通信异常: " + e.getMessage();
//        }
//    }
//}
