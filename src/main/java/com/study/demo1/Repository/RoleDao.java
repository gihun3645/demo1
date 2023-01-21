package com.study.demo1.Repository;

import com.study.demo1.domain.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCallOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// DB를 입력 수정 삭제 저장
// Bean이 됨
// Spring JDCB를 이용해서 DB 프로그램
// @Repository는 @Component 이고 컨테이너가 관리해주는 빈
@Repository
public class RoleDao {
//    public RoleDao() {
//        System.out.println("Dao 생성자 호출");
//    }

//    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    // 필드를 final로 선언하면 반드시 생성자에서 초기화 한다.
    private SimpleJdbcInsertOperations insertAction; // insert를 쉽게 하도록 도와주는 인터페이스

    // 생성자에 파라미터를 넣어주면 스프링 부트가 자동으로 주입한다. (생성자 주입)
    public RoleDao(DataSource dataSource) {
        System.out.println(dataSource.getClass().getName());
//        jdbcTemplate = new JdbcTemplate(dataSource); // DataSource를 넣어줘야 한다.
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertAction =  new SimpleJdbcInsert(dataSource)
                .withTableName("role"); // insert할 테이블 이름을 넣어준다.
    }

    // Role 테이블에 한건 저장
    // 저장을 성공하면 true, 실패하면 false를 반환한다.
    public boolean addRole(Role role) { // Role 객체를 임포트
//        String sql = "insert into role(role_id, name) values(?, ?)";
//        int result = jdbcTemplate.update(sql, role.getRoleId(), role.getName());
//        // update 메소드는 insert, update, delete SQL문을 실행할 때 사용한다.
//        System.out.println(result);
//        return result == 1;

        // role은 프로퍼티 roleId, name
        // insert into role(role_id, name) values(:roleId, :name);
        // 위와 같은 sql을 simpleJdbcInsert가 내부적으로 만든다.
        // Role클래스의 프로터피이름과 칼럼명이 규칙이 맞아야 한다. 예> role_id 칼럼명 = roleId 프로퍼티
        SqlParameterSource params = new BeanPropertySqlParameterSource(role); // role 객체의 필드명과 테이블의 칼럼명이 같아야 한다.
        int result = insertAction.execute(params);
        return result == 1;
    }

    // 삭제
    public boolean deleteRole(int roleId) {
//        String sql = "delete from role where role_id = ?";
//        int result = jdbcTemplate.update(sql, roleId);
        String sql = "delete from role where role_id = :roleId";
        SqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
        int result = jdbcTemplate.update(sql, params);
        return result == 1;
    }

    // 읽어오기
    public Role getRole(int roleId) { // role_id는 PK니깐 1건 or 0건의 데이터가 조회된다.
        String sql = "select role_id, name from role where role_id = :roleId";
        // queryForObject는 1건 또는 0건을 읽어오는 메소드,
        // queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
        // ...은 여러개의 변수를 받을 수 있다는 것임
//        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
//            Role role = new Role();
//            role.setRoleId(rs.getInt("role_id"));
//            role.setName(rs.getString("name"));
//            return role;
//        }, roleId);
        // exception이 발생하지 않으려면,
        try {
            SqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
            RowMapper<Role> roleRowMapper = BeanPropertyRowMapper.newInstance(Role.class);
            return jdbcTemplate.queryForObject(sql, params, roleRowMapper);
        } catch(Exception e) {
            return null;
        }
    }

    // 모든 정보를 리턴
    public List<Role> getRoles() {
        String sql = "select role_id, name from role order by role_id desc";
        // query 메소드는 여러건의 결과를 구할 때 사용하는 메소드(리스트를 담아 리턴)
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//            Role role = new Role();
//            role.setRoleId(rs.getInt("role_id"));
//            role.setName(rs.getString("name"));
//            return role;
//        });
        // 람다 표현식도 대체가능
        RowMapper<Role> roleRowMapper = BeanPropertyRowMapper.newInstance(Role.class);
        return jdbcTemplate.query(sql, roleRowMapper);
    }
}

// 데이터를 한건 읽어오는 것을 성공한 것을 가정하고, 한건이 데이터를 Role객체에 담아서 리턴하도록 한다.
// 이 클래스가 다른 클래스는 전혀 재사용할일이 없을 경우, 클래스를 만들 필요가 있을까?
//class RoleRowMapper implements RowMapper<Role> {
//    @Override
//    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
//        Role role = new Role();
//        role.setRoleId(rs.getInt("role_id"));
//        role.setName(rs.getString("name"));
//        return role;
//    }
//}