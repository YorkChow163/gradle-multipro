package com.zhouyu.securitydemo.service;

import com.zhouyu.securitydemo.dao.PermissionDao;
import com.zhouyu.securitydemo.entity.MyPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Date:2019/3/27 13:52
 * @Author:zhouyu
 */
@Service
public class PermissionService {

    private  static  Logger logger = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    PermissionDao permissionDao;


    /**
     * 根据url权限
     * @param url
     * @return
     */
    public MyPermission getPermission(String url){
        MyPermission permission = permissionDao.getByPage(url);
        logger.info("获取权限,url:{},permission:{}",url,permission);
        return permission;
    }
}
