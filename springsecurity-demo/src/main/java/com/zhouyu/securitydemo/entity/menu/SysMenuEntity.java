package com.zhouyu.securitydemo.entity.menu;

import com.zhouyu.securitydemo.entity.BaseEntity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * 菜单管理
 *
 * @author zhouyu
 */
@Data
@Table(name = "sys_menu")
@Entity
public class SysMenuEntity extends BaseEntity {

  private static final long serialVersionUID = -4831257321976727541L;
  /**
   * 父菜单ID，一级菜单为0
   */
  @Column(length = 10, columnDefinition = "int(10) COMMENT '父菜单id'")
  private Long parentId;

  /**
   * 父菜单名称
   */
  @Transient
  private String parentName;

  /**
   * 菜单名称
   */
  @Column(length = 64, columnDefinition = "varchar(64) default '' COMMENT '菜单名称'")
  private String name;

  /**
   * 菜单URL
   */
  @Column(length = 400, columnDefinition = "varchar(400) default '' COMMENT '菜单URL'")
  private String url;

  /**
   * 授权(多个用逗号分隔，如：user:list,user:create)
   */
  @Column(length = 100, columnDefinition = "varchar(100) default '' COMMENT '授权'")
  private String perms;

  /**
   * 类型     0：目录   1：菜单   2：按钮
   */
  @Column(length = 10, columnDefinition = "int(10) COMMENT ' 0：目录 1：菜单 2：按钮'")
  private Integer type;

  /**
   * 菜单图标
   */
  @Column(length = 100, columnDefinition = "varchar(100) default '' COMMENT '菜单图标'")
  private String icon;

  /**
   * 排序
   */
  @Column(length = 10, columnDefinition = "int(10) COMMENT '排序'")
  private Integer orderNum;

  /**
   * ztree属性
   */
  @Transient
  private Boolean open;
  @Transient
  private List<?> list;
}
