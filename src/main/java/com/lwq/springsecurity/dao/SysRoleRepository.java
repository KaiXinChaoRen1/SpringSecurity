package com.lwq.springsecurity.dao;

import com.lwq.springsecurity.entity.SysRole;
import com.lwq.springsecurity.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole,Long> {
}
