package com.yonyou.springbootshiro.repository;

import com.yonyou.springbootshiro.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 21:49
 */
public interface RoleRepository extends JpaRepository<SysRole,Long> {
}
