package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by pht on 2019/4/15 0015
 */
@Repository("UserDao")
public interface UserDao {

    List<User> userMobileExists(@Param("user_mobile") String user_mobile);

    void inserUser(User user);

    void iforgot(User user);

    User login(User user);
}
