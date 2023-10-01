package cn.edu.gzgs.ims.service.impl;

import cn.edu.gzgs.ims.dao.SysUserDao;
import cn.edu.gzgs.ims.domain.LoginUser;
import cn.edu.gzgs.ims.entity.SysUser;
import cn.edu.gzgs.ims.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public LoginUser queryUser(String name) {
        SysUser sysUser =  sysUserDao.queryOneLoginUser(name);
        return new LoginUser(sysUser, null);
    }
}
