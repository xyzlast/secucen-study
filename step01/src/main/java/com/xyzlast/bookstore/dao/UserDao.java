package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.entity.User;
import com.xyzlast.bookstore.util.InnerPreparedStatementAndResultSetProcess;
import com.xyzlast.bookstore.util.SqlExecutor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements BookStoreDao<User, Integer> {

    private final SqlExecutor sqlExecutor;

    public UserDao(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM users";
        return this.sqlExecutor.executeProcess(sql, new InnerPreparedStatementAndResultSetProcess<Integer>() {
            @Override
            public void doProcess(PreparedStatement st) throws SQLException {

            }

            @Override
            public Integer convertFromResultSet(ResultSet rs) throws SQLException {
                rs.next();
                return rs.getInt(1);
            }
        });
    }

    @Override
    public void deleteAll() {
        String sql = "delete from users";
        this.sqlExecutor.executeProcess(sql, (st) -> {
        });
    }

    @Override
    public List<User> getAll() {
        String sql = "select id, name, password, point, level from users";
        return sqlExecutor.executeProcess(sql, new InnerPreparedStatementAndResultSetProcess<List<User>>() {
            @Override
            public void doProcess(PreparedStatement st) throws SQLException {

            }

            @Override
            public List<User> convertFromResultSet(ResultSet rs) throws SQLException {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(convertUserFromResultSet(rs));
                }
                return users;
            }
        });
    }

    @Override
    public User getById(Integer id) {
        String sql = "select id, name, password, point, level from users where id = ?";
        return sqlExecutor.executeProcess(sql, new InnerPreparedStatementAndResultSetProcess<User>() {
            @Override
            public void doProcess(PreparedStatement st) throws SQLException {
                st.setInt(1, id);
            }

            @Override
            public User convertFromResultSet(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return convertUserFromResultSet(rs);
                }
                return null;
            }
        });
    }

    @Override
    public boolean update(User entity) {
        String sql = "update users set name = ?, password = ?, point = ?, level = ? where id = ?";
        sqlExecutor.executeProcess(sql, (st) -> {
            st.setString(1, entity.getName());
            st.setString(2, entity.getPassword());
            st.setInt(3, entity.getPoint());
            st.setInt(4, entity.getLevel());
            st.setInt(5, entity.getId());
        });
        return true;
    }

    @Override
    public boolean add(User entity) {
        String sql = "insert users(name, password, point, level) values(?, ?, ?, ?)";
        sqlExecutor.executeProcess(sql, (st) -> {
            st.setString(1, entity.getName());
            st.setString(2, entity.getPassword());
            st.setInt(3, entity.getPoint());
            st.setInt(4, entity.getLevel());
        });
        return true;
    }

    @Override
    public boolean delete(User entity) {
        String sql = "delete from users where id = ?";
        sqlExecutor.executeProcess(sql, (st) -> {
            st.setInt(1, entity.getId());
        });
        return true;
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
