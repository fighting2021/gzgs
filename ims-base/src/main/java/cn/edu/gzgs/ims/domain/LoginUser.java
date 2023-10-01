package cn.edu.gzgs.ims.domain;

import cn.edu.gzgs.ims.entity.SysUser;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {
    //用户信息
    private SysUser sysUser;
    //用户角色列表
    private List<String> permissions;
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    public LoginUser(SysUser sysUser, List<String> permissions) {
        this.sysUser = sysUser;
        this.permissions = permissions;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        if (permissions != null) {
            String[] strings = permissions.toArray(new String[permissions.size()]);
            authorities = AuthorityUtils.createAuthorityList(strings);
        }
        return authorities;
    }

    public String getPassword() {
        return sysUser.getPwd();
    }

    public String getUsername() {
        return sysUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
