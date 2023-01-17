package com.study.demo1.Repository;

import com.study.demo1.domain.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

// DB를 입력 수정 삭제 저장
// Bean이 됨
// Spring JDCB를 이용해서 DB 프로그램
// @Repository는 @Component 이고 컨테이너가 관리해주는 빈
@Repository
public class RoleDao {
//    public RoleDao() {
//        System.out.println("Dao 생성자 호출");
//    }

    private final JdbcTemplate jdbcTemplate;
    // 필드를 final로 선언하면 반드시 생성자에서 초기화 한다.

    // 생성자에 파라미터를 넣어주면 스프링 부트가 자동으로 주입한다. (생성자 주입)
    public RoleDao(DataSource dataSource) {
        System.out.println(dataSource.getClass().getName());
        jdbcTemplate = new JdbcTemplate(dataSource); // DataSource를 넣어줘야 한다.
    }

    // Role 테이블에 한건 저장
    // 저장을 성공하면 true, 실패하면 false를 반환한다.
    public boolean addRole(Role role) { // Role 객체를 임포트
        String sql = "insert into role(role_id, name) values(?, ?)";
        int result = jdbcTemplate.update(sql, role.getRoleId(), role.getName());
        // update 메소드는 insert, update, delete SQL문을 실행할 때 사용한다.
        System.out.println(result);
        return result == 1;
    }

    // 삭제
    public boolean deleteRole(int roleId) {
        String sql = "delete from role where role_id = ?";
        int result = jdbcTemplate.update(sql, roleId);
        return result == 1;
    }

    // 읽어오기
    public Role getRole(int roleId) {
        String sql = "select role_id, name from role where role_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Role role = new Role();
            role.setRoleId(rs.getInt("role_id"));
            role.setName(rs.getString("name"));
            return role;
        }, roleId);
    }
}
