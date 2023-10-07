package cn.edu.gzgs.ims.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysRole对象", description="角色表")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    @ApiModelProperty(value = "编号")
    private Long roleId;
    @ApiModelProperty(value = "角色名")
    private String name;
    @ApiModelProperty(value = "角色编码")
    private String code;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "角色状态 0--可用  1--不可用")
    private Integer roleStatu;
}
