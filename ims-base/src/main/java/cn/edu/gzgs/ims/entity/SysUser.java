package cn.edu.gzgs.ims.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUser对象", description="用户表")
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    @ApiModelProperty(value = "用户编号")
    private Long userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "用户密码")
    private String pwd;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "是否可用 0--可用  1--不可用")
    private Integer userStatu;
    @ApiModelProperty("上一次登录时间")
    private Date lastLoginTime;
    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private List<SysRole> roles;
}
