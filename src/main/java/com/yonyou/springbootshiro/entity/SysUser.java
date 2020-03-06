package com.yonyou.springbootshiro.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 21:42
 */
@Getter
@Setter
@Entity
@Table(name="sys_user")
public class SysUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
    //JoinTable的name是中间表的名字 JoinTable
    @OneToMany(fetch=FetchType.EAGER)
    @JoinTable(name="sys_user_role",joinColumns={@JoinColumn(name="user_id")}
            ,inverseJoinColumns={@JoinColumn(name="role_id")})
    private List<SysRole> sysRoles;

}
