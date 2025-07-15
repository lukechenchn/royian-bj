package com.ruoyi.project.system.user.rob;

import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/visual")
public class VisualApiController {

    @PostMapping("/measure")
    public Map<String,String> measure(@RequestBody String body)
    {
        JSONArray datajson = JSONArray.parseArray(body);
        if (datajson == null || datajson.isEmpty()) {
            throw new IllegalArgumentException("输入数据为空");
        }else{
            System.out.println("datajson:"+datajson);
            try (FileWriter file = new FileWriter("data.json")) {
                file.write(datajson.toString());
                System.out.println("JSON数据已写入文件");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String,String> res  = new HashMap<String,String>();
        try {
        for (int i = 0; i < datajson.size(); i++) {
            Map map = (Map) datajson.get(i);
            String zbx_id =  map.get("zbx_id").toString();
            String x = map.get("x").toString();
            String y = map.get("y").toString();
            String z = map.get("z").toString();
            String rx = map.get("rx").toString();
            String ry = map.get("ry").toString();
            String rz = map.get("rz").toString();
            System.out.println("zbx_id:"+zbx_id+" x:"+x+" y:"+y+" z+:"+z+" rx:"+rx+" ry:"+ry+" rz:"+rz);
        }
        res.put("retflag","0");
        res.put("retmsg","success");
        }catch(Exception e){
            res.put("retflag","1");
            res.put("retmsg","接收数据不完整");
        }
        return res;
    }



    /*
    * 入参样例
    [
    {"zbx_id": "zbx_1","x": "12.452","y": "1502.159","z": "366.352","rx":"12.25","ry":"102.36","rz":"29.24"},
    {"zbx_id":"zbx_2","x":"12.452","y":"1502.159","z":"366.352","rx": "12.25","ry": "102.36","rz":"29.24"},
    {"zbx_id": "zbx_3","x": "12.452","y": "1502.159","z": "366.352","rx": "12.25","ry": "102 36","rz": "29.24"},
    {"zbx_id": "zbx_4","x": "12.452","y": "1502.159","z": "366.352","rx": "12.25","ry": "102 36","rz": "29.24"}
    ]
    * */

//   [
//    {"zbx_id": "Base1","x": "+20.0001","y": "+34.0002","z": "-30.0000","rx":"-25.0000","ry":"-23.0000","rz":"+30.0000"},
//    {"zbx_id": "Base2","x": "+20.0001","y": "+34.0002","z": "-30.0000","rx":"-25.0000","ry":"-23.0000","rz":"+30.0000"},
//    {"zbx_id": "Base3","x": "+20.0001","y": "+34.0002","z": "-30.0000","rx":"-25.0000","ry":"-23.0000","rz":"+30.0000"},
//    {"zbx_id": "Base4","x": "+20.0001","y": "+34.0002","z": "-30.0000","rx":"-25.0000","ry":"-23.0000","rz":"+30.0000"},
//    ]
//
//    Base1
//    X1：+20.0001
//    Y1：+34.0002
//    Z1：-30.0000
//    A1：-25.0000
//    B1：-23.0000
//    C1：+30.0000
//
//    Base2
//    X2：+20.0001
//    Y2：+34.0002
//    Z2：-30.0000
//    A2：-25.0000
//    B2：-23.0000
//    C2：+30.0000
//
//    Base3
//    X3：+20.0001
//    Y3：+34.0002
//    Z3：-30.0000
//    A3：-25.0000
//    B3：-23.0000
//    C3：+30.0000
//
//    Base4
//    X4：+20.0001
//    Y4：+34.0002
//    Z4：-30.0000
//    A4：-25.0000
//    B4：-23.0000
//    C4：+30.0000

}
