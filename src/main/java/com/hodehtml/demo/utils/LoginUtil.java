package com.hodehtml.demo.utils;/**
 * created by pht on 2019/3/26 0026
 */

import com.hodehtml.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pht
 * @program demo
 * @date 2019/3/26 0026
 */
@Component
public class LoginUtil {

    private HttpSession session;
    @Autowired
    private JedisUtil jedisUtil;

    public HttpSession LoginUtil(HttpSession session){
         this.session = session;
         return session;
    }

    public User verification() {
        String message = "";
        Map<String,Object> map = new HashMap<String, Object>();
        String token = (String) session.getAttribute("token");
        boolean token1 = JedisUtil.existsKey(token);
        User user = null;
        if (token1 == false) {
            message = "请重新登录";
            map.put("message",message);
            return user;
        } else {
            user = (User) session.getAttribute("user");
            JedisUtil.disableTime(token,user.toString(), 6000);
        }
        return user;
    }

}
