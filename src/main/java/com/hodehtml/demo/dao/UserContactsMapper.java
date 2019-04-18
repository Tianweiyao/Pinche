package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.UserContacts;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("UserContactsMapper")
public interface UserContactsMapper {
    void deleteByPrimaryKey(Integer id);

    void insert(UserContacts record);

    void insertSelective(UserContacts record);

    UserContacts selectByPrimaryKey(@Param("UserId") String UserId);

    void updateByPrimaryKeySelective(UserContacts record);

    void updateByPrimaryKey(UserContacts record);
}