package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.UserBace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("UserBaceMapper")
public interface UserBaceMapper {
    void deleteByPrimaryKey(Integer id);

    void insert(UserBace record);

    void insertSelective(UserBace record);

    UserBace selectByPrimaryKey(@Param("userId") String userId);

    void updateByPrimaryKeySelective(UserBace record);

    void updateByPrimaryKey(UserBace record);



}