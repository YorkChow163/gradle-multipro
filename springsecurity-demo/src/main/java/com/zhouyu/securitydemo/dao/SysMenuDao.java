package com.zhouyu.securitydemo.dao;

import com.zhouyu.securitydemo.entity.menu.SysMenuEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 */
public interface SysMenuDao extends JpaRepository<SysMenuEntity, Long> {
}
