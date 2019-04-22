package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.FullOrder;
import org.springframework.stereotype.Repository;

@Repository("FullOrderMapper")
public interface FullOrderMapper {

    void deleteByPrimaryKey(Integer id);

    void insert(FullOrder record);

    void insertSelective(FullOrder record);

    FullOrder selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(FullOrder record);

    void updateByPrimaryKey(FullOrder record);
}