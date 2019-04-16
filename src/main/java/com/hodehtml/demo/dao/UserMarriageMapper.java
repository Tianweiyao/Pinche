package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.UserMarriage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("userMarriageMapper")
public interface UserMarriageMapper {
    void deleteByPrimaryKey(@Param("marriageId") Integer marriageId);

    void insert(UserMarriage record);

    void insertSelective(UserMarriage record);

    UserMarriage selectByPrimaryKey(@Param("marriageId") Integer marriageId);

    void updateByPrimaryKeySelective(UserMarriage record);

    void updateByPrimaryKey(UserMarriage record);
}