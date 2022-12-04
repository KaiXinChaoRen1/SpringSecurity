package com.lwq.springsecurity.dao;

import com.lwq.springsecurity.entity.SysPower;
import com.lwq.springsecurity.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysPowerRepository extends JpaRepository<SysPower,Long> {
}
