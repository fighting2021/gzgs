package cn.edu.gzgs.ims.controller;

import cn.edu.gzgs.ims.common.WrapperResult;
import cn.edu.gzgs.ims.domain.LoginUser;
import cn.edu.gzgs.ims.domain.User;
import cn.edu.gzgs.ims.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/sys/query")
    public WrapperResult<LoginUser> query(String name) {
        LoginUser loginUser = userService.queryUser(name);
        return WrapperResult.success(loginUser);
    }

    @PostMapping("/sys/delete")
    public WrapperResult<String> delete(int userId) {
        return WrapperResult.success("删除成功");
    }

}
