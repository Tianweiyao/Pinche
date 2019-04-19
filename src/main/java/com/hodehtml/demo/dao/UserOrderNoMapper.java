package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.UserOrderNo;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository("UserOrderNoMapper")
public interface UserOrderNoMapper {

    void deleteByPrimaryKey(Integer id);

    void insert(UserOrderNo record);

    void insertSelective(UserOrderNo record);

    UserOrderNo selectByPrimaryKey(@RequestParam("userId") String userId);

    void updateByPrimaryKeySelective(UserOrderNo record);

    void updateByPrimaryKey(UserOrderNo record);
}