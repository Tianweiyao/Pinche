package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.AccountCall;

public interface AccountCallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountCall record);

    int insertSelective(AccountCall record);

    AccountCall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountCall record);

    int updateByPrimaryKey(AccountCall record);
}