package com.lwq.springsecurity.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = {"sysUsers","sysPowers"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "lwq_role")
public class SysRole {
    @Id
    private Long id;

    @Column
    private String name;

    @Column(unique = true, nullable = false)
    private String roleKey;

    @JSONField(serialize = false)
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "sysRoles")
    private  Set<SysUser> sysUsers=new HashSet<>();

    @JSONField(serialize = false)
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private  Set<SysPower> sysPowers=new HashSet<>();
}
