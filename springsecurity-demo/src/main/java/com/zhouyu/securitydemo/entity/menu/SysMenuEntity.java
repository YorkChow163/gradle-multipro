/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.zhouyu.securitydemo.entity.menu;

import com.zhouyu.securitydemo.entity.BaseEntity;

import org.springframework.data.annotation.Transient;

import java.util.List;

import javax.persistence.Table;

import lombok.Data;

/**
 * 菜单管理
 *
 * @author zhouyu
 */
@Data
@Table(name = "sys_menu")
public class SysMenuEntity extends BaseEntity {

  /**
   * 父菜单ID，一级菜单为0
   */
  private Long parentId;

  /**
   * 父菜单名称
   */
  @Transient
  private String parentName;

  /**
   * 菜单名称
   */
  private String name;

  /**
   * 菜单URL
   */
  private String url;

  /**
   * 授权(多个用逗号分隔，如：user:list,user:create)
   */
  private String perms;

  /**
   * 类型     0：目录   1：菜单   2：按钮
   */
  private Integer type;

  /**
   * 菜单图标
   */
  private String icon;

  /**
   * 排序
   */
  private Integer orderNum;

  /**
   * ztree属性
   */
  @Transient
  private Boolean open;
  @Transient
  private List<?> list;
}
