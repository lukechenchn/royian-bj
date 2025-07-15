package com.ruoyi.project.system.xfvisual.api.test;

import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Anonymous
@RestController
@RequestMapping("/api")
public class BJApiController {

    @PostMapping("/post")
    public Map<String,String> test(@RequestBody String body)
    {
        Map<String,Object> datajson = JSON.parseObject(body);
        if (datajson == null || datajson.isEmpty()) {
            throw new IllegalArgumentException("输入数据为空");
        }else{
            System.out.println("datajson:"+datajson);
//            try (FileWriter file = new FileWriter("data.json")) {
//                file.write(datajson.toString());
//                System.out.println("JSON数据已写入文件");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        Map<String,String> res  = new HashMap<String,String>();
        res.put("flag","0");
        res.put("msg","success");
        return res;
    }


    @GetMapping("/get")
//    @CrossOrigin
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Map<String, String> getUrlParam(@RequestParam String param) {
        Map<String, String> res = new HashMap<>();
        res.put("flag", "0");
        res.put("msg", "success");
        res.put("receivedParam", param);
        return res;
    }


}
