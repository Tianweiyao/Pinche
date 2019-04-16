package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.Reptile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("ReptileMapper")
public interface ReptileMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    void insert(Reptile record);

    void insertSelective(Reptile record);

    Reptile selectByPrimaryKey(@Param("id") Integer id);

    void updateByPrimaryKeySelective(Reptile record);

    void updateByPrimaryKeyWithBLOBs(Reptile record);

    void updateByPrimaryKey(Reptile record);
}