package com.lwq.springsecurity.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(exclude = {"sysRoles"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "lwq_power")
public class SysPower {
    @Id
    private Long id;

    @Column
    private String name;

    @Column(unique = true, nullable = false)
    private String powerKey;

    @JSONField(serialize = false)
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "sysPowers")
    private  Set<SysRole> sysRoles=new HashSet<>();


}
