package com.xuezhijian.util;

import com.xuezhijian.model.Barrager;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil implements Serializable {
    private static final long serialVersionUID = -5393496161251379029L;
    /**
     * 缓存集合
     */
    private static ConcurrentHashMap<String, CacheValue> mapCache = new ConcurrentHashMap<String, CacheValue>();

    private static Timer timer;
    private static byte[] lock = new byte[0];
    private static boolean isRun = false;

    //没隔多少秒清理一次  6秒
    private static Long DEFAULT_TASK_PERIOD = 1000L * 6 ;

    //该key的值超时时间为    30秒
    private static Long DEFAULT_TIMEOUT = 1000L * 6 ;

    private static CacheUtil instance;
    private CacheUtil() {
    }
    public static CacheUtil getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new CacheUtil();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        clearCache();
                    }
                }, 0, DEFAULT_TASK_PERIOD);
            }
        }
        return instance;
    }


    public static Object put(String key, Object value,long timeout) {
        CacheValue cvalue = new CacheValue();
        if(key != null){
            cvalue.setKey(key);
        }
        cvalue.setValue(value);
        if(timeout != 0){
            cvalue.setTimeout(timeout);
        }else{
            cvalue.setTimeout(DEFAULT_TIMEOUT);
        }
        cvalue.setCreateTime(System.currentTimeMillis());
        return mapCache.put(key, cvalue);
    }

    public static Object get(String key) {
        if (key != null) {
            Object obj = null;
            if (mapCache.containsKey(key)) {
                obj = mapCache.get(key);
            }
            if (obj != null) {
                CacheValue cvalue = (CacheValue) obj;
                return cvalue.getValue();
            }
        }
        return null;
    }

    public boolean containsKey(String key) {
        return mapCache.containsKey(key);
    }


    private static void clearCache() {
        if (!isRun) {
            synchronized (lock) {
                try {
                    isRun = true;
                    if (!mapCache.isEmpty()) {
                        System.out.println("移除前:"+mapCache);
                        Iterator<Map.Entry<String, CacheValue>> iterator = mapCache.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, CacheValue> entry = iterator.next();
                            CacheValue cacheValue = entry.getValue();
                            if (cacheValue.isTimeout()) {
                                mapCache.remove(entry.getKey());
                                System.out.println("移除超时__:"+entry.getKey());
                            }
                        }
                        System.out.println("剩余__:"+mapCache);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    isRun = false;
                }
            }
        }
    }

    public static ConcurrentHashMap<String, CacheValue> getMapCache() {
        clearCache();
        return mapCache;
    }

    public static void setMapCache(ConcurrentHashMap<String, CacheValue> mapCache) {
        CacheUtil.mapCache = mapCache;
    }


}
