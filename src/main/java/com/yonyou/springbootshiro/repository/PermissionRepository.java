package com.yonyou.springbootshiro.repository;

import com.yonyou.springbootshiro.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 15:40
 */
public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
