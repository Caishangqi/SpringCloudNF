package com.tedu.sp03.service;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.UserService;
import cn.tedu.web.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RefreshScope //y有这个注解，刷新到的新的用户配置才会重新注入到对象中
public class UserServiceImpl implements UserService {
    @Value("${sp.user-service.users}")
    private String userJson;

    @Override
    public User getUser(Integer userId) {
        log.info("获取用户，userId="+userId);

        // userJson ------> List<User>
        List<User> list =
                JsonUtil.from(userJson,
                        new TypeReference<List<User>>() {});
        for (User u : list) {
            if (u.getId().equals(userId)) {
                return u; // 7,  8,  9
            }
        }
        return new User(userId,"用户名"+userId,"密码"+userId);
    }

    @Override
    public void addScore(Integer userId, Integer score) {
        log.info("增加用户积分，userId="+userId+"，score="+score);
    }
}
