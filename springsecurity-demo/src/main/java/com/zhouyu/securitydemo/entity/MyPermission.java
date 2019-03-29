package com.zhouyu.securitydemo.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

/**
 * @Description:权限表
 * @Date:2019/3/26 11:06
 * @Author:zhouyu
 */
@Data
@Table(name = "permission")
@Entity
public class MyPermission extends  BaseEntity{
    @Column(columnDefinition = "varchar(64) default '' COMMENT '权限名称'")
    private String name;

   /* @ManyToMany(mappedBy = "permissions",fetch = FetchType.EAGER)
    private List<Role> roles;*/

    @Column(length = 10, columnDefinition = "int(10) COMMENT '父菜单id'")
    private Integer pid;


    @Column(length = 10, columnDefinition = "int(10) COMMENT '菜单排序'")
    private Integer zindex;


    @Column(length = 1, columnDefinition = "int(1) COMMENT '权限分类（0 菜单；1 功能）'")
    private Integer istype;

    @Column(length = 64, columnDefinition = "varchar(64) default '' COMMENT '权限描述'")
    private String descpt;

    @Column(length = 64, columnDefinition = "varchar(64) default '' COMMENT '图标'")
    private String icon;

    @Column(length = 64, columnDefinition = "varchar(64) default '' COMMENT '代号'")
    private String code;

    @Column(length = 64, columnDefinition = "varchar(64) default '' COMMENT '菜单url'")
    private String page;
}
