package com.lwq.springsecurity.dao;

import com.lwq.springsecurity.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepository extends JpaRepository<SysUser,Long> {
    //jpa组装方法名查询
    SysUser findByUserName(String username);
}
