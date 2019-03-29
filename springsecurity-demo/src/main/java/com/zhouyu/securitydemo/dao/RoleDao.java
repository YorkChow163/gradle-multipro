package com.zhouyu.securitydemo.dao;

import com.zhouyu.securitydemo.entity.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * @Description:角色Dao
 * @Date:2019/3/29 13:50
 * @Author:zhouyu
 */
public interface RoleDao extends CrudRepository<Role,Long> {
}
