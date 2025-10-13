package com.ruoyi.project.system.xfvisual.api.plc.hslcom.controller;

import HslCommunication.Core.Types.OperateResult;
import HslCommunication.Core.Types.OperateResultExOne;
import HslCommunication.Profinet.Melsec.MelsecMcNet;
import HslCommunication.Profinet.Siemens.SiemensS7Net;
import com.ruoyi.project.system.xfvisual.api.plc.hslcom.service.SiemensPlcConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/siemens")
public class HslController {

    @Autowired
    private SiemensPlcConnectionService plcConnectionService;

    @RequestMapping("/readInt16")
    public String readInt16() {
        try {
            SiemensS7Net siemensPLC = plcConnectionService.getPlc();

            if (siemensPLC != null) {
                // 使用HSL库读取16位整数
                OperateResultExOne<Short> readResult = siemensPLC.ReadInt16("DB2.0");
                if (readResult.IsSuccess) {
                    return "读取成功: " + readResult.Content;
                } else {
                    return "读取失败: " + readResult.Message;
                }
            } else {
                return "PLC未初始化";
            }
        } catch (Exception e) {
            return "读取异常: " + e.getMessage();
        }
    }

    @RequestMapping("/read")
    public String read() {
        SiemensS7Net plc = plcConnectionService.getPlc();
        OperateResultExOne<Short> read = plc.ReadInt16("D100");
        if (read.IsSuccess) {
            short value = read.Content;
            // 然后进行相关的业务操作
        } else {
            // 提示失败，回滚业务
        }
        return null;
    }


}
