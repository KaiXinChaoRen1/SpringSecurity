package com.lwq.springsecurity;

import com.lwq.springsecurity.dao.SysPowerRepository;
import com.lwq.springsecurity.dao.SysRoleRepository;
import com.lwq.springsecurity.dao.SysUserRepository;
import com.lwq.springsecurity.entity.SysPower;
import com.lwq.springsecurity.entity.SysRole;
import com.lwq.springsecurity.entity.SysUser;
import com.lwq.springsecurity.utils.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ManyToManyTest {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysPowerRepository sysPowerRepository;

    @Test
    @Transactional
    @Commit
    void name1(){
        //设置保存权限
        SysPower build = SysPower.builder().id(IdWorker.newId()).name("访问hello4").powerKey("hello3").build();
        SysPower save = sysPowerRepository.save(build);
        //设置保存角色
        SysRole build1 = SysRole.builder().id(IdWorker.newId()).name("董事长").roleKey("father").build();
        build1.getSysPowers().add(save);
        SysRole save1 = sysRoleRepository.save(build1);

        //查看角色对用的所有权限
        Set<SysPower> sysPowers = save1.getSysPowers();
        List<String> collect = sysPowers.stream().map(o -> o.getPowerKey()).collect(Collectors.toList());
        System.out.println(collect);


        //把角色设置给用户
        SysUser user = SysUser.builder().id(IdWorker.newId()).userName("root").password("1234").build();
        SysUser save2 = sysUserRepository.save(user);

        Set<SysRole> sysRoles = save2.getSysRoles();
        sysRoles.add(save1);
        SysUser finalUser = sysUserRepository.save(save2);
    }

}
