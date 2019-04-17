package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.UserDebitBank;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserDebitBankMapper")
public interface UserDebitBankMapper {
    int deleteByPrimaryKey(@Param("userDebitId") Integer userDebitId);

    int insert(UserDebitBank record);

    int insertSelective(UserDebitBank record);

    UserDebitBank selectByPrimaryKey(@Param("userDebitId") Integer userDebitId);

    List<UserDebitBank> selectByUserId(@Param("userId") String userId);

    int updateByPrimaryKeySelective(UserDebitBank record);

    int updateByPrimaryKey(UserDebitBank record);
}