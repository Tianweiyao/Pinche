package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.AccountCall;
import org.springframework.stereotype.Repository;

@Repository("AccountCallMapper")
public interface AccountCallMapper {

    void deleteByPrimaryKey(Integer id);

    void insert(AccountCall record);

    void insertSelective(AccountCall record);

    AccountCall selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(AccountCall record);

    void updateByPrimaryKey(AccountCall record);
}