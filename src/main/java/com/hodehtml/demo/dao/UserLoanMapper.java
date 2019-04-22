package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.UserLoan;
import com.hodehtml.demo.utils.Lists;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userLoanMapper")
public interface UserLoanMapper {

    void deleteByPrimaryKey(@Param("loanNo") String loanNo);

    void insert(UserLoan record);

    void insertSelective(UserLoan record);

    UserLoan selectByPrimaryKey(@Param("loanNo") String loanNo);

    void updateByPrimaryKeySelective(UserLoan record);

    void updateByPrimaryKey(UserLoan record);

    List<UserLoan> selectUserLoan(@Param("userId") String userId);
}