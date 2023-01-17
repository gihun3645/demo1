package com.study.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class DevelopApplication implements CommandLineRunner {

    // main 메소드는 SPRING이 관리한다.
    public static void main(String[] args) {
        SpringApplication.run(DevelopApplication.class, args);
    }

    // DataSource Bean(Spring이 관리하는 객체)
    @Autowired // 자동으로 주입
    DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("스프링부트가 관리하는 빈을 사용할 수 있다.");

        Connection connection = dataSource.getConnection();
        connection.close();
    }
}
