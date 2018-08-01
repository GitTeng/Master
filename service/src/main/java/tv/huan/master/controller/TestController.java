package tv.huan.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tv.huan.master.entity.User;
import tv.huan.master.service.UserService;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/11/6 0006
 * Time: 19:12
 * To change this template use File | Settings | File Templates
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "lockTest")
    public User lockTest(Long id){
     User user = userService.findById(id);
     user.setAge(user.getAge()+1);
     userService.save(user);
     return user;
    }
}
