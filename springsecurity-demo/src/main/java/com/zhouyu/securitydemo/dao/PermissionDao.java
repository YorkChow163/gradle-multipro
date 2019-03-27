package com.zhouyu.securitydemo.dao;

import com.zhouyu.securitydemo.entity.MyPermission;
import org.springframework.data.repository.CrudRepository;

/**
 * @Description:权限Dao
 * @Date:2019/3/27 13:56
 * @Author:zhouyu
 */
public interface PermissionDao extends CrudRepository<MyPermission,Long> {
    MyPermission getByPage(String url);
}
