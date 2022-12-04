package com.lwq.springsecurity.service;

import com.lwq.springsecurity.dao.SysUserRepository;
import com.lwq.springsecurity.dto.R;
import com.lwq.springsecurity.entity.SysRole;
import com.lwq.springsecurity.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysUserService {

    @Autowired
    private SysUserRepository  sysUserRepository;


    @Transactional
    public List<String> getUserAuthorities(long id){
        ArrayList<String> authoritiesList = new ArrayList<>();
        Optional<SysUser> sysUserOptional = sysUserRepository.findById(id);
        if(sysUserOptional.isEmpty()) {
            throw new RuntimeException("没有对应用户");
        }
        SysUser user = sysUserOptional.get();
        Set<SysRole> sysRoles = user.getSysRoles();
        for (SysRole r: sysRoles){
            List<String> collect = r.getSysPowers().stream().map(p -> p.getPowerKey()).collect(Collectors.toList());
            authoritiesList.addAll(collect);
        }
        return authoritiesList;


    }



    public R getById(long id){
        Optional<SysUser> sysUserOptional = sysUserRepository.findById(id);
        if(sysUserOptional.isEmpty()){
            return R.fail("根据id找不到对应的用户");
        }else{
            return R.ok(sysUserOptional.get());
        }
    }

    public R addUser(SysUser sysUser) {
        sysUserRepository.save(sysUser);
        return R.ok("添加成功");
    }

    public R getAllUser() {
        List<SysUser> all = sysUserRepository.findAll();
        return R.ok(all);
    }
}
