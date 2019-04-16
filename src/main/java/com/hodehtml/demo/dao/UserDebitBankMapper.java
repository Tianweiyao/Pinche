package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.UserDebitBank;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("UserDebitBankMapper")
public interface UserDebitBankMapper {
    int deleteByPrimaryKey(@Param("userDebitId") Integer userDebitId);

    int insert(UserDebitBank record);

    int insertSelective(UserDebitBank record);

    UserDebitBank selectByPrimaryKey(@Param("userDebitId") Integer userDebitId);

    int updateByPrimaryKeySelective(UserDebitBank record);

    int updateByPrimaryKey(UserDebitBank record);
}