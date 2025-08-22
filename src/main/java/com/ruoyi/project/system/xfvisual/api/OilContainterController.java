package com.ruoyi.project.system.xfvisual.api;


import com.ruoyi.framework.aspectj.lang.annotation.Anonymous;
import com.ruoyi.project.system.container.domain.BjOilContainer;
import com.ruoyi.project.system.container.service.impl.BjOilContainerServiceImpl;
import com.ruoyi.project.system.status.domain.BjAgvStatus;
import com.ruoyi.project.system.status.service.impl.BjAgvStatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;



@RestController
@Anonymous
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 允许跨域请求
public class OilContainterController {

    @Autowired
    private BjOilContainerServiceImpl oilContainerService;

    @Autowired
    private BjAgvStatusServiceImpl agvStatusService;

/*


//   .接收无人机号、无人机油量、挂弹类型、挂弹量
//    詹入参
    {
        "uav_no": "01",
        "oil_type": "A",
        "oil_quantity": "10L",
        "quantity1": "10",
        "quantity2": "0",
        "quantity3": "0",
        "quantity4": "0",
        "quantity5": "0"
    }


    //接收油弹集装箱开闭状态
    //    詹入参
    {
        "oil_container": "开/关",
    }



//查询油弹集装箱状态
//    不需要入参
//    陆返回
    {
        "oil_container": "开/关",
    }

       //接收普通集装箱开闭状态
    {
        "agv_no":"01";
        "container": "开/关",
    }


    //  .接收无人机号、取油量、油类型
    //    詹入参
{
    "uav_no": "01",
    "oil_type": "A",
    "oil_quantity": "10L"
}

//  .接收无人机号、挂弹量、弹类型
//    詹入参
{
    "uav_no": "01",
    "quantity1": "10",
    "quantity2": "0",
    "quantity3": "0",
    "quantity4": "0",
    "quantity5": "0"
}


//接收加油挂弹车的油类型、油量、弹类型、弹量
//    詹入参
{
        "oil_type1": "A",
        "oil_quantity1": "10L",
        "oil_type2": "B",
        "oil_quantity2": "10L",
        "oil_type3": "C",
        "oil_quantity3": "10L",
        "quantity1": "10",
        "quantity2": "20",
        "quantity3": "30",
        "quantity4": "20",
        "quantity5": "10"
}


* */


    /**
     * 接收无人机号、无人机油量、挂弹类型、挂弹量
     * @param params 包含 uav_no, oil_type, oil_quantity, quantity1~quantity5 的 Map
     * @return AjaxResult 响应结果
     */
    @PostMapping("/receiveUavInfo")
    public Map receiveUavInfo(@RequestBody Map<String, String> params) {
        // 提取参数
        String uavNo = params.get("uav_no");
        String oilType = params.get("oil_type");
        String oilQuantity = params.get("oil_quantity");
        String quantity1 = params.get("quantity1");
        String quantity2 = params.get("quantity2");
        String quantity3 = params.get("quantity3");
        String quantity4 = params.get("quantity4");
        String quantity5 = params.get("quantity5");


        BjAgvStatus bjAgvStatus = new BjAgvStatus();
        bjAgvStatus.setUavNo(uavNo);
        bjAgvStatus.setOilType(oilType);
        bjAgvStatus.setOilQuantity(oilQuantity);
        bjAgvStatus.setQuantity1(quantity1);
        bjAgvStatus.setQuantity2(quantity2);
        bjAgvStatus.setQuantity3(quantity3);
        bjAgvStatus.setQuantity4(quantity4);
        bjAgvStatus.setQuantity5(quantity5);

        agvStatusService.updateBjAgvWithOilStatus(bjAgvStatus);

        Map map = new HashMap();
        map.put("code",200);
        map.put("msg", "数据接收成功");
        return map;
    }


    /**
     * 接收油弹集装箱开闭状态
     * @param params 包含 oil_container 状态的 Map
     * @return Map 响应结果
     */
    @PostMapping("/receiveOilContainerStatus")
    public Map receiveOilContainerStatus(@RequestBody Map<String, String> params) {
        // 提取参数
        String oilContainerStatus = params.get("oil_container");

        BjOilContainer bjOilContainer = new BjOilContainer();
        bjOilContainer.setId(1L);
        bjOilContainer.setOilContainer(oilContainerStatus);
        oilContainerService.updateBjOilContainer(bjOilContainer);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "油弹集装箱状态接收成功");
        return response;
    }

    /**
     * 查询油弹集装箱状态
     * @return Map 返回油弹集装箱的开关状态
     */
    @PostMapping("/queryOilContainerStatus")
    public Map queryOilContainerStatus() {
        // 调用服务层获取当前油弹集装箱状态
        BjOilContainer bjOilContainer = oilContainerService.selectBjOilContainerById(1L);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("oil_container", bjOilContainer.getOilContainer());
        response.put("msg", "查询成功");
        return response;
    }




    /**
     * 接收普通集装箱开闭状态
     * @param params 包含 agv_no, container 状态的 Map
     * @return Map 响应结果
     */
//    @PostMapping("/receiveContainerStatus")
//    public Map receiveContainerStatus(@RequestBody Map<String, String> params) {
//        // 提取参数
//        String agvNo = params.get("agv_no");
//        String containerStatus = params.get("container");
//
//        BjAgvStatus bjAgvStatus = new BjAgvStatus();
//        bjAgvStatus.setUavNo(agvNo);
//        bjAgvStatus.setContainer(containerStatus);
//
//        agvStatusService.updateBjAgvWithContainerStatus(bjAgvStatus);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("code", 200);
//        response.put("msg", "普通集装箱状态接收成功");
//        return response;
//    }



    /**
     * 接收无人机号、取油量、油类型
     * @param params 包含 uav_no, oil_type, oil_quantity 的 Map
     * @return Map 响应结果
     */
    @PostMapping("/receiveUavOilInfo")
    public Map receiveUavOilInfo(@RequestBody Map<String, String> params) {
        // 提取参数
        String uavNo = params.get("uav_no");
        String oilType = params.get("oil_type");
        String oilQuantity = params.get("oil_quantity");

        // 构建实体对象
        BjAgvStatus bjAgvStatus = new BjAgvStatus();
        bjAgvStatus.setUavNo(uavNo);
        bjAgvStatus.setOilType(oilType);
        bjAgvStatus.setOilQuantity(oilQuantity);

        // 调用服务层更新数据
//        agvStatusService.updateBjAgvWithOilStatus(bjAgvStatus);

        // 返回响应
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "无人机取油信息接收成功");
        return response;
    }



    /**
     * 接收无人机号、挂弹量、弹类型
     * @param params 包含 uav_no, quantity1~quantity5 的 Map
     * @return Map 响应结果
     */
    @PostMapping("/receiveUavAmmoInfo")
    public Map receiveUavAmmoInfo(@RequestBody Map<String, String> params) {
        // 提取参数
        String uavNo = params.get("uav_no");
        String quantity1 = params.get("quantity1");
        String quantity2 = params.get("quantity2");
        String quantity3 = params.get("quantity3");
        String quantity4 = params.get("quantity4");
        String quantity5 = params.get("quantity5");

        BjAgvStatus bjAgvStatus = new BjAgvStatus();
        bjAgvStatus.setUavNo(uavNo);
        bjAgvStatus.setQuantity1(quantity1);
        bjAgvStatus.setQuantity2(quantity2);
        bjAgvStatus.setQuantity3(quantity3);
        bjAgvStatus.setQuantity4(quantity4);
        bjAgvStatus.setQuantity5(quantity5);

//        agvStatusService.updateBjAgvWithAmmoStatus(bjAgvStatus);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "无人机挂弹信息接收成功");
        return response;
    }



    /**
     * 接收加油挂弹车的油类型、油量、弹类型、弹量
     * @param params 包含 oil_type1~oil_type3, oil_quantity1~oil_quantity3, quantity1~quantity5 的 Map
     * @return Map 响应结果
     */
    @PostMapping("/receiveRefuelAmmoInfo")
    public Map receiveRefuelAmmoInfo(@RequestBody Map<String, String> params) {
        // 提取油类型和油量参数
        String oilType1 = params.get("oil_type1");
        String oilQuantity1 = params.get("oil_quantity1");
        String oilType2 = params.get("oil_type2");
        String oilQuantity2 = params.get("oil_quantity2");
        String oilType3 = params.get("oil_type3");
        String oilQuantity3 = params.get("oil_quantity3");

        // 提取弹类型和弹量参数
        String quantity1 = params.get("quantity1");
        String quantity2 = params.get("quantity2");
        String quantity3 = params.get("quantity3");
        String quantity4 = params.get("quantity4");
        String quantity5 = params.get("quantity5");

        // 构建实体对象并设置属性（示例）
        // 这里假设 BjOilContainer 或其他对应的实体类有相关字段
        BjOilContainer bjOilContainer = new BjOilContainer();
        bjOilContainer.setOilType1(oilType1);
        bjOilContainer.setOilQuantity1(oilQuantity1);
        bjOilContainer.setOilType2(oilType2);
        bjOilContainer.setOilQuantity2(oilQuantity2);
        bjOilContainer.setOilType3(oilType3);
        bjOilContainer.setOilQuantity3(oilQuantity3);
        bjOilContainer.setQuantity1(quantity1);
        bjOilContainer.setQuantuty2(quantity2);
        bjOilContainer.setQuantity3(quantity3);
        bjOilContainer.setQuantity4(quantity4);
        bjOilContainer.setQuantity5(quantity5);

        // 调用服务层更新数据
        oilContainerService.receiveRefuelAmmoInfo(bjOilContainer);

        // 返回响应
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "加油挂弹车信息接收成功");
        return response;
    }
}
