package com.lzl.ossservice.entity.SecurityVo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value="Role对象", description="")
public class Role implements Serializable, GrantedAuthority {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Integer id;

    @TableField("roleName")
    private String roleName;

    @TableField("roleDesc")
    private String roleDesc;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return roleName;
    }
}
