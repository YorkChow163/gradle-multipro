package com.zhouyu.securitydemo.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@ToString
public class MyUser extends BaseEntity implements UserDetails {
    /**
     *  @JoinTable：name-中间表的名字
     *  JoinColumn:当前表的referencedColumnName的字段（id）在中间表的字段名字（user_id）
     *  inverseJoinColumns: 关联外键表的referencedColumnName的字段（id）在中间表的字段名字（role_id）
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @Column(unique = true, length = 32, columnDefinition = "varchar(32)  DEFAULT '' COMMENT '用户名'")
    private String username;

    @Column(length = 32, columnDefinition = "varchar(32)  DEFAULT '' COMMENT '电话'")
    private String mobile;

    @Column(length = 32, columnDefinition = "varchar(32)  DEFAULT '' COMMENT '邮箱'")
    private String email;

    @Column(length = 32, columnDefinition = "int(32)  COMMENT '添加人id'")
    private Integer insertUid;

    @Column(length = 5, columnDefinition = "int(32)  COMMENT '是否删除 0正常 1删除'")
    private Integer isDel;

    @Column(length = 5, columnDefinition = "int(32)  COMMENT '是否在职 0在 1不在'")
    private Integer isJob;

    @Column(length = 5, columnDefinition = "int(32)  COMMENT '版本号'")
    private Integer version;

    @Column(length = 50, columnDefinition = " varchar(50) DEFAULT '' COMMENT '密码'")
    private String password;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 获取权限列表
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  roles;
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
