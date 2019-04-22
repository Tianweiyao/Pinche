package com.hodehtml.demo.service.Impl;/**
 * created by pht on 2019/4/12 0012
 */

import com.hodehtml.demo.dao.MessageCallsMapper;
import com.hodehtml.demo.dao.UserDao;
import com.hodehtml.demo.model.MessageCalls;
import com.hodehtml.demo.model.User;
import com.hodehtml.demo.model.UserLoan;
import com.hodehtml.demo.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pht
 * @program demo
 * @date 2019/4/12 0012
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageCallsMapper messageCallsMapper;


    @Override
    public Boolean userMobileExists(String user_mobile) {

        Boolean result = null;
        List<User> list = userDao.userMobileExists(user_mobile);
        if (list.size()>0 && list != null) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public void reg(User user){

        userDao.inserUser(user);
    }

    @Override
    public void iforgot(User user){

        userDao.iforgot(user);
    };


    @Override
    public User login(User user){

      User user1 = userDao.login(user);
      return user1;
    }


    @Override
    public void insert(MessageCalls record){

        messageCallsMapper.insert(record);
    }



}
