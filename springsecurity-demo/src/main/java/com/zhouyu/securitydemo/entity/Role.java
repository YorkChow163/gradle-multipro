package com.zhouyu.securitydemo.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Role extends BaseEntity implements GrantedAuthority{
    @Column(length = 64, columnDefinition = "varchar(64) default '' COMMENT '角色名称/菜单名'")
    private String authority;


    @Override
    public String getAuthority() {
        return authority;
    }

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permit_id", referencedColumnName = "id"))
    private List<MyPermission> permissions;

   /* @ManyToMany(mappedBy = "roles")
    private List<MyUser> userList;*/

    @Column(length = 1024, columnDefinition = "varchar(1024) default '' COMMENT '内容'")
    private String descpt;

    @Column(length = 64, columnDefinition = "varchar(64) default '' COMMENT '角色编号'")
    private String code;

    @Column(length = 10, columnDefinition = "int(10) COMMENT '插入者id'")
    private  Integer insertUid;

}
