package com.yonyou.springbootshiro.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 9:29
 */
@Getter
@Setter
@Entity
@Table(name  ="sys_role")
public class SysRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "role_name")
    private String roleName;
  /*  @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
  *    cascade  级联操作时才加
  *
  * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role",joinColumns = {@JoinColumn(name = "role_id")}
    ,inverseJoinColumns ={@JoinColumn(name = "user_id")} )
    private  SysUser sysUsers;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinTable(name="sys_role_permission",joinColumns={@JoinColumn(name="role_id")}
            ,inverseJoinColumns={@JoinColumn(name="permission_id")})
    private List<Permission> permissions ;

}
