package com.ruoyi.project.system.xfvisual.api.hutoolCache.service;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class FifoCacheService {

    private Cache<String, Object> fifoCache;

    @PostConstruct
    public void init() {
        // 初始化FIFO缓存，容量为1000
        fifoCache = CacheUtil.newFIFOCache(1000);
    }

    /**
     * 存储数据到FIFO缓存，使用默认不过期策略
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    public void putData(String key, Object value) {
        if (StrUtil.isNotBlank(key) && value != null) {
            fifoCache.put(key, value);
        }
    }

    /**
     * 存储数据到FIFO缓存，指定过期时间
     *
     * @param key     缓存键
     * @param value   缓存值
     * @param timeout 过期时间(毫秒)
     */
    public void putData(String key, Object value, long timeout) {
        if (StrUtil.isNotBlank(key) && value != null) {
            fifoCache.put(key, value, timeout);
        }
    }

    /**
     * 从FIFO缓存获取数据
     *
     * @param key 缓存键
     * @return 缓存值
     */
    public Object getData(String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return fifoCache.get(key);
    }

    /**
     * 从FIFO缓存移除数据
     *
     * @param key 缓存键
     * @return 被移除的值，如果键不存在则返回null
     */
    public void removeData(String key) {
        if (StrUtil.isBlank(key)) {

        }
        fifoCache.remove(key);
    }

    /**
     * 清空FIFO缓存
     */
    public void clearCache() {
        fifoCache.clear();
    }

    /**
     * 获取FIFO缓存大小
     *
     * @return 缓存大小
     */
    public int getCacheSize() {
        return fifoCache.size();
    }

    /**
     * 获取FIFO缓存容量
     *
     * @return 缓存容量
     */
    public int getCacheCapacity() {
        return fifoCache.capacity();
    }

    @PreDestroy
    public void destroy() {
        // 应用关闭时清理缓存资源
        if (fifoCache != null) {
            fifoCache.clear();
        }
    }
}
