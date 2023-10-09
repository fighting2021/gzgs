package cn.edu.gzgs.ims.utils;

import cn.edu.gzgs.ims.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
    /**
     * 获取 Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户信息
     */
    public static LoginUser getLoginUser(){
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        }catch (Exception e){
            throw new RuntimeException("获取用户信息异常");
        }
    }

}
