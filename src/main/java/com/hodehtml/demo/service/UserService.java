package com.hodehtml.demo.service;

import com.hodehtml.demo.model.User;
import java.util.List;

/**
 * created by pht on 2019/4/12 0012
 */
public interface UserService {


    Boolean userMobileExists(String user_mobile);

    void reg(User user);

    void iforgot(User user);

    User login(User user);


}
