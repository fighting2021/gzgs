package cn.edu.gzgs.ims.service.impl;

import cn.edu.gzgs.ims.dao.SysUserDao;
import cn.edu.gzgs.ims.domain.LoginUser;
import cn.edu.gzgs.ims.entity.SysRole;
import cn.edu.gzgs.ims.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
    注意：这个类必须要实现spring security框架的UserDetailsService接口，并重写loadUserByUsername方法。
*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws InternalAuthenticationServiceException {
        SysUser sysUser = sysUserDao.queryOneLoginUser(username);
        if (sysUser == null) {
            throw new InternalAuthenticationServiceException("用户名不存在");
        }
        // 查询权限对应的信息
        List<String> permissions = new ArrayList<>();
        for (SysRole role : sysUser.getRoles()) {
            permissions.add(role.getCode());
        }
        return new LoginUser(sysUser, permissions);
    }
}
