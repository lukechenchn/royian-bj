package com.ruoyi.project.system.xfvisual.api.hutoolCache.test;

import cn.hutool.core.lang.Console;
import com.ruoyi.project.system.xfvisual.api.hutoolCache.service.FifoCacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestBySpring {
    @Autowired
    private FifoCacheService service; // 可以正常注入依赖的service

    @Test
    public void testService() {
        service.putData("key1", "value1");
        Console.log(YELLOW+service.getData("key1")+RESET);
        service.clearCache();
        // 测试4: 带过期时间的存储
        System.out.println("\n=== 测试过期时间 ===");
        service.putData("tempKey", "tempValue", 1000); // 1秒过期
        System.out.println("刚存储的tempKey: " + service.getData("tempKey"));

        try {
            Thread.sleep(1500); // 等待1.5秒
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("1.5秒后tempKey: " + service.getData("tempKey")); // 应该为null

        // 测试5: 缓存信息
        System.out.println("\n=== 测试缓存信息 ===");
        System.out.println("缓存大小: " + service.getCacheSize());
        System.out.println("缓存容量: " + service.getCacheCapacity());
    }
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
}
