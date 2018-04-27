package com.xuezhijian.service.Imp;

import com.xuezhijian.mapper.BarragerMapper;
import com.xuezhijian.model.Barrager;
import com.xuezhijian.service.BarragerService;
import com.xuezhijian.util.CacheUtil;
import com.xuezhijian.util.CacheValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.xuezhijian.util.CacheUtil.getMapCache;

@Service
public class BarragerServiceImp implements BarragerService {

    private static ConcurrentHashMap<String, Barrager> actualBarragerCache;

    @Autowired
    BarragerMapper barragerMapper;
    @Override
    public Map<String, Barrager> getAllBarrager() {
        Map<String,Barrager> barragerMap=new HashMap<>();
       List<Barrager> barragerList= barragerMapper.getAllBarrager();
       for (Barrager barrager:barragerList){
           barragerMap.put(barrager.getBarrid().toString(),barrager);
       }
        return barragerMap;
    }

    @Override
    public Map<String, Barrager> getActualBarrager() {
        Map actualMap= CacheUtil.getMapCache();
        actualBarragerCache = new ConcurrentHashMap<String, Barrager>();
        Iterator<Map.Entry<String, CacheValue>> iterator =actualMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, CacheValue> entry = iterator.next();
               String key=entry.getValue().getKey();
               Barrager barrager=(Barrager) entry.getValue().getValue();
                if (barrager!=null) {
                    actualBarragerCache.put(key, barrager);
                    return actualBarragerCache;
                }
                //actualBarragerCache.put(entry.getValue().getKey(), (Barrager) entry.getValue().getValue());
            }

            return null;
    }

    @Override
    public Boolean addBarrager(Barrager barrager) {
        CacheUtil.put(barrager.getInfo(),barrager,0);
        barragerMapper.insertSelective(barrager);
        return true;
    }
}
