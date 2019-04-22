package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.MessageCalls;
import org.springframework.stereotype.Repository;

@Repository("MessageCallsMapper")
public interface MessageCallsMapper {

    void deleteByPrimaryKey(Integer id);

    void insert(MessageCalls record);

    void insertSelective(MessageCalls record);

    MessageCalls selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(MessageCalls record);

    void updateByPrimaryKey(MessageCalls record);
}