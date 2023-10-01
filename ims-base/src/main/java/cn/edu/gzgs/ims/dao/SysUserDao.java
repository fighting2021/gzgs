package cn.edu.gzgs.ims.dao;

import cn.edu.gzgs.ims.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {
    SysUser queryOneLoginUser(String username);
}
