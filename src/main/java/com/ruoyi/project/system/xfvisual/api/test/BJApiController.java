package com.ruoyi.project.system.xfvisual.api.test;

import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.project.system.task.service.impl.BjTaskServiceImpl;
import com.ruoyi.project.system.xfvisual.api.TaskAndAgvStatusController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Anonymous
@RestController
@RequestMapping("/api")
public class BJApiController {

    @Autowired
    private BjTaskServiceImpl taskService;

    @Autowired
    TaskAndAgvStatusController tcontroller;

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

        /*测试检查是否存在重复任务  测试完成,未发现bug*/
//        int count  = taskService.selectCountByTaskNo("01-001");

        /*检查当前任务是否可以执行*/
        boolean x = tcontroller.canExecuteCurrentTask("03","002");

        Map<String, String> res = new HashMap<>();
        res.put("flag", "0");
        res.put("msg", "success");
        res.put("receivedParam", x+"");

        return res;
    }


}
