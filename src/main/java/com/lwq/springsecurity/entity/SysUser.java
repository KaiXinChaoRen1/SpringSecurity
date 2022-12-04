package com.lwq.springsecurity.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//JPA需要实体类有空参构造,使用Builder需要三件套:@Builder,@AllArgsConstructor,@NoArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "lwq_user")
@EqualsAndHashCode(exclude = {"sysRoles"})
@ToString(exclude = {"sysRoles"})
public class SysUser {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column
    private String nickName;

    @Column
    private String password;

    @JSONField(serialize = false)
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST})
    private  Set<SysRole> sysRoles=new HashSet<>();



}
