package com.lwq.springsecurity.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * hutool雪花算法
 */
public class IdWorker {

    public static long newId(){
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        return id;
    }
}
