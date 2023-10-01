package cn.edu.gzgs.ims.dao;

import cn.edu.gzgs.ims.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuDao extends BaseMapper<SysMenu> {
    /**
     * 根据请求路径查询菜单对应的权限
     */
    List<SysMenu> queryMenuRoles(String path);
}

