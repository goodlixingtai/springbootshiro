package com.yonyou.springbootshiro.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 0:02
 * 权限类Permission
 */
@Getter
@Setter
@Entity
@Table(name = "sys_permission")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String permission;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinTable(name="sys_role_permission",joinColumns={@JoinColumn(name="permission_id")}
            ,inverseJoinColumns={@JoinColumn(name="role_id")})
    private SysRole sysRole;
}
