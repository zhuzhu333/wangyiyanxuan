package com.kgc.provider.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/4 20:41
 * @since
 */
@Configuration
@MapperScan("com.kgc.provider.mapper")
public class MyBatisConfiguration {
}
