package com.ruoyi.project.system.xfvisual.api.hutoolCache.service;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Service
public class TimedDataCacheService {

    private TimedCache<String, Object> dataCache;

    @PostConstruct
    public void init() {
        // 初始化定时缓存，默认30分钟过期
        dataCache = CacheUtil.newTimedCache(TimeUnit.MINUTES.toMillis(30));

        // 启动定时任务，每分钟清理一次过期条目
        dataCache.schedulePrune(TimeUnit.MINUTES.toMillis(1));
    }

    /**
     * 存储PLC数据到缓存，使用默认过期时间
     * @param key 缓存键
     * @param value 缓存值
     */
    public void putData(String key, Object value) {
        if (StrUtil.isNotBlank(key) && value != null) {
            dataCache.put(key, value);
        }
    }

    /**
     * 存储PLC数据到缓存，指定过期时间
     * @param key 缓存键
     * @param value 缓存值
     * @param timeout 过期时间(毫秒)
     */
    public void putData(String key, Object value, long timeout) {
        if (StrUtil.isNotBlank(key) && value != null && timeout > 0) {
            dataCache.put(key, value, timeout);
        }
    }

    /**
     * 从缓存获取PLC数据（会重置过期时间）
     * @param key 缓存键
     * @return 缓存值
     */
    public Object getData(String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return dataCache.get(key);
    }

    /**
     * 从缓存获取PLC数据，可选择是否重置过期时间
     * @param key 缓存键
     * @param isUpdateLastAccess 是否更新最后访问时间
     * @return 缓存值
     */
    public Object getData(String key, boolean isUpdateLastAccess) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return dataCache.get(key, isUpdateLastAccess);
    }

    /**
     * 从缓存移除数据
     * @param key 缓存键
     * @return 被移除的值，如果键不存在则返回null
     */
    public void removeData(String key) {
        if (StrUtil.isBlank(key)) {

        }
        dataCache.remove(key);
    }

    /**
     * 清空缓存
     */
    public void clearCache() {
        dataCache.clear();
    }

    /**
     * 获取缓存大小
     * @return 缓存大小
     */
    public int getCacheSize() {
        return dataCache.size();
    }

    @PreDestroy
    public void destroy() {
        // 应用关闭时取消定时清理任务并清理缓存资源
        if (dataCache != null) {
            dataCache.cancelPruneSchedule();
            dataCache.clear();
        }
    }
}
