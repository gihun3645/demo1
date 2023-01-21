package com.study.demo1.Repository;

import com.study.demo1.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsertOperations insertAction;

    // 생성자 주입
    public UserDao(DataSource dataSource) {
        System.out.println(dataSource.getClass().getName());
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("user");
    }

    // 입력
    public boolean addUser(User user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        int result = insertAction.execute(params);
        return result == 1;
    }

    // 읽어오기
    public User getUser(int userId) {
        String sql = "select user_id, name, email from user where user_id = :userId";
        try {
            SqlParameterSource params = new MapSqlParameterSource("userId", userId);
            RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);
            return jdbcTemplate.queryForObject(sql, params, userRowMapper);
        } catch (Exception e) {
            return null;
        }
    }

    // 삭제
    public boolean deleteUser(int userId) {
        String sql = "delete from user where user_id = :userId";
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        int result = jdbcTemplate.update(sql, params);
        return result == 1;
    }

    // 모든 정보 리턴
    public List<User> getUsers() {
        String sql = "select user_id, name, email from user order by user_id";
        RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);
        return  jdbcTemplate.query(sql, userRowMapper);
    }


}
