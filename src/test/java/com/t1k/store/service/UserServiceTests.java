package com.t1k.store.service;

import com.t1k.store.entity.User;
import com.t1k.store.mapper.UserMapper;
import com.t1k.store.service.ex.ServiceException;
import com.t1k.store.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.io.File;

@SpringBootTest
public class UserServiceTests
{
    @Resource
    IUserService userService;

    @Resource
    UserServiceImpl service;

    @Resource
    BCryptPasswordEncoder encoder;

    @Test
    void reg()
    {
        try
        {
            User user = new User();
            user.setUsername("t7k");
            user.setPassword("123");
            userService.reg(user);
        } catch(ServiceException e)
        {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Resource
    UserMapper mapper;

    @Test
    void changeInfo()
    {

        Integer uid = 1;
        String username = "t1k";
        String phone = "11111";
        Integer gender = 1;
        String email = " ";
        User user = new User();
        user.setUid(1);
        user.setPhone(phone);
        user.setGender(gender);
        mapper.updateById(user);

    }

}
