package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao implements BookStoreDao<User, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer countAll() {
        String sql = "SELECT COUNT(*) FROM users";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from users";
        this.jdbcTemplate.update(sql);
    }

    @Override
    public List<User> getAll() {
        String sql = "select id, name, password, point, level from users";
        List<Map<String, Object>> maps = this.jdbcTemplate.queryForList(sql);
        List<User> users = new ArrayList<>();
        maps.forEach(m -> users.add(convertUserFromMap(m)));
        return users;
    }

    @Override
    public User getById(Integer id) {
        String sql = "select id, name, password, point, level from users where id = ?";
        return this.jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNum) -> convertUserFromResultSet(rs));
    }

    @Override
    public boolean update(User user) {
        String sql = "update users set name = ?, password = ?, point = ?, level = ? where id = ?";
        return this.jdbcTemplate.update(sql, user.getName(), user.getPassword(),
            user.getPoint(), user.getLevel(), user.getId()) == 1;

    }

    @Override
    public boolean add(User user) {
        String sql = "insert users(name, password, point, level) values(?, ?, ?, ?)";
        return this.jdbcTemplate.update(sql, user.getName(), user.getPassword(),
            user.getPoint(), user.getLevel()) == 1;
    }

    @Override
    public boolean delete(User user) {
        String sql = "delete from users where id = ?";
        return this.jdbcTemplate.update(sql, user.getId()) == 1;
    }

    private User convertUserFromMap(Map<String, Object> map) {
        User user = new User();
        user.setId((int) map.get("id"));
        user.setName((String) map.get("name"));
        user.setPassword((String) map.get("password"));
        user.setLevel((int) map.get("level"));
        user.setPoint((int) map.get("point"));
        return user;
    }

    private User convertUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setPoint(rs.getInt("point"));
        user.setLevel(rs.getInt("level"));
        return user;
    }
}
