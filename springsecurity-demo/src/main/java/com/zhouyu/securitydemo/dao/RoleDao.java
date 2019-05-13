package com.zhouyu.securitydemo.dao;

import com.zhouyu.securitydemo.entity.sys.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

/**
 * @Description:角色Dao
 * @Date:2019/3/29 13:50
 * @Author:zhouyu
 */
public interface RoleDao extends JpaRepository<Role,Long> {

    @Query(value = "select role_id from role_permission where permit_id= ?1",nativeQuery = true)
    public List<BigInteger> findAllByPermissionId(Long perId);
}
