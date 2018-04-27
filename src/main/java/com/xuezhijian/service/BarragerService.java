package com.xuezhijian.service;

import com.xuezhijian.model.Barrager;

import java.util.Map;

public interface BarragerService {

    public Map<String,Barrager> getAllBarrager();

    public Map<String,Barrager> getActualBarrager();

    public Boolean addBarrager(Barrager barrager);
}
