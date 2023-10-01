package cn.edu.gzgs.ims.config;

import cn.edu.gzgs.ims.dao.SysMenuDao;
import cn.edu.gzgs.ims.entity.SysRole;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import cn.edu.gzgs.ims.entity.SysMenu;

// 安全元数据源
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private SysMenuDao sysMenuDao;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //查询具体某个接口的权限
        List<SysMenu> sysMenus = sysMenuDao.queryMenuRoles(requestUrl);
        if(CollectionUtils.isEmpty(sysMenus)){
            return Arrays.asList(new SecurityConfig("ROLE_NO_USER"));
        }
        //菜单对应的权限
        List<SysRole> roles = sysMenus.get(0).getRoles();
        List<String> attributes =  new ArrayList<>();
        for(int i = 0; i < roles.size(); i++){
            SysRole sysRole = roles.get(i);
            if (sysRole.getRoleStatu() == 0) {
                attributes.add(roles.get(i).getCode());
            }
        }
        return CollectionUtils.isEmpty(attributes) ? Arrays.asList(new SecurityConfig("ROLE_NO_USER"))
                : SecurityConfig.createList(attributes.toArray(new String[0]));
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

