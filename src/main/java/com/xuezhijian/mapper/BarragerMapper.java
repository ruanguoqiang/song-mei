package com.xuezhijian.mapper;

import com.xuezhijian.model.Barrager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BarragerMapper {
    int deleteByPrimaryKey(Integer barrid);

    int insert(Barrager record);

    int insertSelective(Barrager record);

    Barrager selectByPrimaryKey(Integer barrid);

    int updateByPrimaryKeySelective(Barrager record);

    int updateByPrimaryKey(Barrager record);

    List<Barrager> getAllBarrager();
}