package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.entity.History;
import com.xyzlast.bookstore.entity.HistoryActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class HistoryDao implements BookStoreDao<History, Integer> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer countAll() {
        String sql = "SELECT COUNT(*) FROM histories";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from histories";
        this.jdbcTemplate.update(sql);
    }

    @Override
    public List<History> getAll() {
        String sql = "select id, userId, bookId, actionType, insertDate from histories";
        List<History> histories = new ArrayList<>();
        List<Map<String, Object>> maps = this.jdbcTemplate.queryForList(sql);
        maps.forEach(map -> histories.add(convertHistoryFromMap(map)));
        return histories;
    }

    @Override
    public History getById(Integer id) {
        String sql = "select id, userId, bookId, actionType, insertDate from histories where id = ?";
        return this.jdbcTemplate.queryForObject(sql,
            new Object[] {id}, (rs, rowNum) -> convertHistoryFromResultSet(rs));
    }

    @Override
    public boolean update(History history) {
        String sql = "update histories set bookId = ?, userId = ?, actionType = ?, insertDate = ? where id = ?";
        return this.jdbcTemplate.update(sql, history.getBookId(), history.getUserId(),
            history.getActionType().value(), history.getDate(), history.getId()) == 1;
    }

    @Override
    public boolean add(History history) {
        String sql = "insert histories(bookId, userId, actionType, insertDate) values(?, ?, ?, ?)";
        return this.jdbcTemplate.update(sql, history.getBookId(), history.getUserId(),
            history.getActionType().value(), history.getDate()) == 1;
    }

    @Override
    public boolean delete(History history) {
        String sql = "delete from histories where id = ?";
        return this.jdbcTemplate.update(sql, history.getId()) == 1;
    }

    private History convertHistoryFromMap(Map<String, Object> map) {
        History history = new History();
        history.setId((int) map.get("id"));
        history.setUserId((int) map.get("userId"));
        history.setBookId((int) map.get("bookId"));
        history.setActionType(HistoryActionType.parse((int) map.get("actionType")));
        history.setDate((Timestamp) map.get("insertDate"));
        return history;
    }

    private History convertHistoryFromResultSet(ResultSet rs) throws SQLException {
        History history = new History();
        history.setId(rs.getInt("id"));
        history.setUserId(rs.getInt("userId"));
        history.setBookId(rs.getInt("bookId"));
        history.setActionType(HistoryActionType.parse(rs.getInt("actionType")));
        java.util.Date date = new java.util.Date(rs.getDate("insertDate").getTime());
        history.setDate(date);
        return history;
    }
}
