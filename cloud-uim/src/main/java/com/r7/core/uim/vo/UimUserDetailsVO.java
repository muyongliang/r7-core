package com.r7.core.uim.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 登录信息
 *
 * @author mrli
 */
@ApiModel("登陆信息")
public class UimUserDetailsVO implements UserDetails {

    private Long id;

    @ApiModelProperty("用户名")
    private String userName;


    @ApiModelProperty("登陆名")
    private String loginName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("状态0正常1暂停使用")
    private Integer status;

    @ApiModelProperty("删除状态0正常1删除")
    private Integer del;

    @ApiModelProperty("角色")
    private List<String> roles;

    private List<GrantedAuthority> authorities;

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 账户算法过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 账户是否被冻结
        return this.status == 2;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 帐户密码是否过期，一般有的密码要求性高的系统会使用到，比较每隔一段时间就要求用户重置密码
        return true;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
        List<GrantedAuthority> authorities = Lists.newArrayList();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        this.authorities = authorities;
    }

    @Override
    public boolean isEnabled() {
        // 帐号是否可用
        return this.status == 1;
    }
}
