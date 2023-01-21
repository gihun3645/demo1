package com.study.demo1;
// 이 패키지 이하로 Component를 찾아서 Bean에 올림

import com.study.demo1.Repository.RoleDao;
import com.study.demo1.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

// @Componnent 붙어 있는 객체는 스프링 컨테이너가 관리하는 객체가 된다. Bean이 됨
@SpringBootApplication
public class DevelopApplication implements CommandLineRunner {

    // main 메소드는 SPRING이 관리한다.
    public static void main(String[] args) {
        SpringApplication.run(DevelopApplication.class, args);
    }

    // DataSource Bean(Spring이 관리하는 객체)을 주입
    // DataSouce라는 빈을 메모리에 올림
    @Autowired // 자동으로 주입
    DataSource dataSource;

    // Spring이 Object로 참조 할 수 있는 모든 Bean을 주입한다.
    @Autowired
    List<Object> beans;

    @Autowired
    RoleDao roleDao;

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("Hello World!");

        // 모든 Bean들을 출력(configuration 빼고 나옴)
//        for(Object obj: beans) {
//            System.out.println(obj.getClass().getName());
//        }
//
//        System.out.println("-----");
//        System.out.println(dataSource.getClass().getName());
//
////         JDBC 사용 가능
//        Connection connection = dataSource.getConnection();
//        connection.close();

//        List<Connection> list = new ArrayList<>();

//        int i=0;
//        while(true) {
//            Connection conn = dataSource.getConnection();
//            // conn을 이용하여 SQL 실행. slow sql을 실행하게 되면...
//            // close를 안하면 큰일남.. 서버가 꺼짐
//            conn.close();
//            Thread.sleep(100); // 0.1초 쉰다.
//        }

//        // 입력
//        Role role = new Role();
//        role.setRoleId(3);
//        role.setName("Role_TEST");
//        roleDao.addRole(role);

        // 삭제
//        boolean flag = roleDao.deleteRole(3);
//        System.out.println("flag "+flag); // 성공시 true

        // 읽어 오기
//        Role role = roleDao.getRole(2);
//        // null이 발생하지 않게 하려면,
//        if(role != null) {
//            System.out.println(role.getRoleId() + ", " + role.getName());
//        }


        // 여러건 읽어오기
        List<Role> list = roleDao.getRoles();
        for(Role role : list) {
            System.out.println(role.getRoleId()+", "+role.getName());
        }
    }
}
