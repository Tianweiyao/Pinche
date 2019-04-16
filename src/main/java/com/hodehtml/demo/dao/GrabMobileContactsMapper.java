package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.GrabMobileContacts;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("GrabMobileContactsMapper")
public interface GrabMobileContactsMapper {
    int deleteByPrimaryKey(@Param("id") Integer grabId);

    int insert(GrabMobileContacts record);

    int insertSelective(GrabMobileContacts record);

    GrabMobileContacts selectByPrimaryKey(@Param("id") Integer grabId);

    int updateByPrimaryKeySelective(GrabMobileContacts record);

    int updateByPrimaryKey(GrabMobileContacts record);
}