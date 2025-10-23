package com.ruoyi.project.system.xfvisual.api.hutoolCache;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.xfvisual.api.hutoolCache.service.FifoCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache/fifo")
public class FifoCacheController {

    @Autowired
    private FifoCacheService fifoCacheService;

    /**
     * 存储数据到FIFO缓存
     *
     * @param key   键
     * @param value 值
     * @return 操作结果
     */
    @PostMapping("/put")
    public AjaxResult putData(@RequestParam String key, @RequestParam Object value) {
        try {
            fifoCacheService.putData(key, value);
            return AjaxResult.success("数据存储成功");
        } catch (Exception e) {
            return AjaxResult.error("数据存储失败: " + e.getMessage());
        }
    }

    /**
     * 存储数据到FIFO缓存，指定过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间(毫秒)
     * @return 操作结果
     */
    @PostMapping("/putWithTimeout")
    public AjaxResult putDataWithTimeout(@RequestParam String key,
                                         @RequestParam Object value,
                                         @RequestParam long timeout) {
        try {
            fifoCacheService.putData(key, value, timeout);
            return AjaxResult.success("数据存储成功，过期时间: " + timeout + "毫秒");
        } catch (Exception e) {
            return AjaxResult.error("数据存储失败: " + e.getMessage());
        }
    }

    /**
     * 从FIFO缓存获取数据
     *
     * @param key 键
     * @return 缓存数据
     */
    @GetMapping("/get")
    public AjaxResult getData(@RequestParam String key) {
        try {
            Object data = fifoCacheService.getData(key);
            if (data != null) {
                return AjaxResult.success("获取数据成功", data);
            } else {
                return AjaxResult.error("未找到对应数据");
            }
        } catch (Exception e) {
            return AjaxResult.error("获取数据失败: " + e.getMessage());
        }
    }

    /**
     * 从FIFO缓存移除数据
     *
     * @param key 键
     * @return 操作结果
     */
    @DeleteMapping("/remove")
    public AjaxResult removeData(@RequestParam String key) {
        try {
            fifoCacheService.removeData(key);
            return AjaxResult.success("数据移除成功");
        } catch (Exception e) {
            return AjaxResult.error("数据移除失败: " + e.getMessage());
        }
    }

    /**
     * 清空FIFO缓存
     *
     * @return 操作结果
     */
    @DeleteMapping("/clear")
    public AjaxResult clearCache() {
        try {
            fifoCacheService.clearCache();
            return AjaxResult.success("缓存清空成功");
        } catch (Exception e) {
            return AjaxResult.error("缓存清空失败: " + e.getMessage());
        }
    }

    /**
     * 获取FIFO缓存信息
     *
     * @return 缓存大小和容量
     */
    @GetMapping("/info")
    public AjaxResult getCacheInfo() {
        try {
            int size = fifoCacheService.getCacheSize();
            int capacity = fifoCacheService.getCacheCapacity();
            return AjaxResult.success("缓存信息获取成功",
                    "当前大小: " + size + ", 总容量: " + capacity);
        } catch (Exception e) {
            return AjaxResult.error("获取缓存信息失败: " + e.getMessage());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        // 创建FIFO缓存服务实例
        FifoCacheService fifoCacheService = new FifoCacheService();

        // 初始化缓存
        fifoCacheService.init();

        try {
            // 测试1: 基本存取功能
            System.out.println("=== 测试基本存取功能 ===");
            fifoCacheService.putData("key1", "value1");
            fifoCacheService.putData("key2", "value2");

            System.out.println("key1的值: " + fifoCacheService.getData("key1"));
            System.out.println("key2的值: " + fifoCacheService.getData("key2"));

            // 测试2: 相同key覆盖
            System.out.println("\n=== 测试相同key覆盖 ===");
            fifoCacheService.putData("key1", "new_value1");
            System.out.println("覆盖后key1的值: " + fifoCacheService.getData("key1"));

            // 测试3: FIFO淘汰机制(创建小容量缓存)
            System.out.println("\n=== 测试FIFO淘汰机制 ===");
            FifoCacheService smallCache = new FifoCacheService();
            // 这里需要修改构造来支持小容量，或者创建新的测试方法
            // 模拟容量为3的缓存行为

            // 测试4: 带过期时间的存储
            System.out.println("\n=== 测试过期时间 ===");
            fifoCacheService.putData("tempKey", "tempValue", 1000); // 1秒过期
            System.out.println("刚存储的tempKey: " + fifoCacheService.getData("tempKey"));

            Thread.sleep(1500); // 等待1.5秒
            System.out.println("1.5秒后tempKey: " + fifoCacheService.getData("tempKey")); // 应该为null

            // 测试5: 缓存信息
            System.out.println("\n=== 测试缓存信息 ===");
            System.out.println("缓存大小: " + fifoCacheService.getCacheSize());
            System.out.println("缓存容量: " + fifoCacheService.getCacheCapacity());

            // 测试6: 移除数据
            System.out.println("\n=== 测试移除数据 ===");
            fifoCacheService.removeData("key1");
            System.out.println("移除key1后: " + fifoCacheService.getData("key1")); // 应该为null

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 清理资源
            fifoCacheService.destroy();
        }
    }
}
