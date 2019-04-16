package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.UserJob;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("userJobMapper")
public interface UserJobMapper {
    void deleteByPrimaryKey(@Param("jobId") Integer jobId);

    void insert(UserJob record);

    void insertSelective(UserJob record);

    UserJob selectByPrimaryKey(@Param("jobId") Integer jobId);

    void updateByPrimaryKeySelective(UserJob record);

    void updateByPrimaryKey(UserJob record);
}