package cn.edu.gzgs.ims.service;


import cn.edu.gzgs.ims.domain.LoginUser;
import cn.edu.gzgs.ims.domain.User;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface IUserService {

    LoginUser queryUser(String name);

}
