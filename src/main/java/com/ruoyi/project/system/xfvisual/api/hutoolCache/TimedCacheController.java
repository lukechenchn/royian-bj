package com.ruoyi.project.system.xfvisual.api.hutoolCache;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.xfvisual.api.hutoolCache.service.TimedDataCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class TimedCacheController {

    @Autowired
    private TimedDataCacheService timedDataCacheService;

    /**
     * 存储数据到缓存
     * @param key 键
     * @param value 值
     * @return 操作结果
     */
    @PostMapping("/put")
    public AjaxResult putData(@RequestParam String key, @RequestParam Object value) {
        try {
            timedDataCacheService.putData(key, value);
            return AjaxResult.success("数据存储成功");
        } catch (Exception e) {
            return AjaxResult.error("数据存储失败: " + e.getMessage());
        }
    }

    /**
     * 从缓存获取数据
     * @param key 键
     * @return 缓存数据
     */
    @GetMapping("/get")
    public AjaxResult getData(@RequestParam String key) {
        try {
            Object data = timedDataCacheService.getData(key);
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
     * 从缓存移除数据
     * @param key 键
     * @return 操作结果
     */
    @DeleteMapping("/remove")
    public AjaxResult removeData(@RequestParam String key) {
        try {
            timedDataCacheService.removeData(key);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("数据移除失败: " + e.getMessage());
        }
    }

    /**
     * 清空缓存
     * @return 操作结果
     */
    @DeleteMapping("/clear")
    public AjaxResult clearCache() {
        try {
            timedDataCacheService.clearCache();
            return AjaxResult.success("缓存清空成功");
        } catch (Exception e) {
            return AjaxResult.error("缓存清空失败: " + e.getMessage());
        }
    }

    /**
     * 获取缓存大小
     * @return 缓存大小
     */
    @GetMapping("/size")
    public AjaxResult getCacheSize() {
        try {
            int size = timedDataCacheService.getCacheSize();
            return AjaxResult.success("获取缓存大小成功", size);
        } catch (Exception e) {
            return AjaxResult.error("获取缓存大小失败: " + e.getMessage());
        }
    }
}
