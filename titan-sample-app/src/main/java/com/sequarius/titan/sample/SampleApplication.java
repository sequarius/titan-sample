package com.sequarius.titan.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 30/01/2020
 */
@SpringBootApplication()
@MapperScan(basePackages = "com.sequarius.titan.sample.**.repository")
public class SampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

}
