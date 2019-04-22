package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.UserOrderBill;
import org.springframework.stereotype.Repository;

@Repository("UserOrderBillMapper")
public interface UserOrderBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOrderBill record);

    int insertSelective(UserOrderBill record);

    UserOrderBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOrderBill record);

    int updateByPrimaryKey(UserOrderBill record);
}