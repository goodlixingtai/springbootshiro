package com.yonyou.springbootshiro.repository;

import com.yonyou.springbootshiro.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 21:49
 */
public interface UserRepository extends JpaRepository<SysUser,Long> {
    SysUser findByName(String name);
  /*  List<SysUser> findByUserNameAndAge(String userName, Integer age);
    List<SysUser> findByUserNameLike(String userName);*/

}
