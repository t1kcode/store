package com.t1k.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.t1k.store.mapper")
public class StoreApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(StoreApplication.class, args);
    }

}
