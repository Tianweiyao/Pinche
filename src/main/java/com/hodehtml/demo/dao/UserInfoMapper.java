package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("userInfoMapper")
public interface UserInfoMapper {
    void deleteByPrimaryKey(@Param("infoId") Integer infoId);

    void insert(UserInfo record);

    void insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(@Param("infoId") Integer infoId);

    UserInfo selectByUserId(@Param("userId") String userId);

    void updateByPrimaryKeySelective(UserInfo record);

    void updateByPrimaryKey(UserInfo record);
}