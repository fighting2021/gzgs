package cn.edu.gzgs.ims.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysMenu对象", description="菜单表")
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    @ApiModelProperty(value = "菜单id")
    private Long menuId;
    @ApiModelProperty(value = "父级id, 根菜单为0")
    private Long parentId;
    @ApiModelProperty(value = "菜单名")
    private String menuName;
    @ApiModelProperty(value = "排序")
    private Integer srt;
    @ApiModelProperty(value = "路由地址")
    private String path;
    @ApiModelProperty(value = "组件路径")
    private String component;
    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;
    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    private String visible;
    @ApiModelProperty(value = "菜单状态（0正常 1停用）")
    private String status;
    @ApiModelProperty(value = "菜单图标")
    private String icon;
    @TableField(exist = false)
    private List<SysRole> roles;
}

