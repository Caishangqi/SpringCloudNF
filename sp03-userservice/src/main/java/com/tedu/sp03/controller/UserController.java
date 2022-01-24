package com.tedu.sp03.controller;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.UserService;
import com.tedu.sp03.service.UserServiceImpl;
import cn.tedu.web.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public JsonResult<User> getUser(
            @PathVariable Integer userId) {
        User user = userService.getUser(userId);
        return JsonResult.build().code(200).data(user);
    }

    @GetMapping("/{userId}/score") //  /8/score?score=1000
    public JsonResult<?> addScore(
            @PathVariable Integer userId,
            @RequestParam("score") Integer score) {
        userService.addScore(userId, score);
        return JsonResult.build().code(200).msg("增加积分成功");
    }

    @GetMapping("/favicon.ico")
    public void ico() {
    }
}
