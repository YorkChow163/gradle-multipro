package com.zhouyu.securitydemo.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Date:2019/3/26 11:15
 * @Author:zhouyu
 */
@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime COMMENT '创建时间'")
    protected Date insertTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime COMMENT '修改时间'")
    protected Date updateTime;
}
