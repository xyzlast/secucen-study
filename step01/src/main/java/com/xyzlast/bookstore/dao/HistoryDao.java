package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.entity.History;
import com.xyzlast.bookstore.entity.HistoryActionType;
import com.xyzlast.bookstore.util.InnerPreparedStatementAndResultSetProcess;
import com.xyzlast.bookstore.util.SqlExecutor;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao implements BookStoreDao<History, Integer> {
    private final SqlExecutor sqlExecutor;

    public HistoryDao(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM histories";
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
        String sql = "delete from histories";
        this.sqlExecutor.executeProcess(sql, (st) -> {
        });
    }

    @Override
    public List<History> getAll() {
        String sql = "select id, userId, bookId, actionType, insertDate from histories";
        return this.sqlExecutor.executeProcess(sql, new InnerPreparedStatementAndResultSetProcess<List<History>>() {
            @Override
            public void doProcess(PreparedStatement st) throws SQLException {

            }

            @Override
            public List<History> convertFromResultSet(ResultSet rs) throws SQLException {
                List<History> histories = new ArrayList<>();
                while (rs.next()) {
                    histories.add(convertHistoryFromResultSet(rs));
                }
                return histories;
            }
        });
    }

    @Override
    public History getById(Integer id) {
        String sql = "select id, userId, bookId, actionType, insertDate from histories where id = ?";
        return this.sqlExecutor.executeProcess(sql, new InnerPreparedStatementAndResultSetProcess<History>() {
            @Override
            public void doProcess(PreparedStatement st) throws SQLException {
                st.setInt(1, id);
            }

            @Override
            public History convertFromResultSet(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return convertHistoryFromResultSet(rs);
                }
                return null;
            }
        });
    }

    @Override
    public boolean update(History entity) {
        String sql = "update histories set bookId = ?, userId = ?, actionType = ?, insertDate = ? where id = ?";
        sqlExecutor.executeProcess(sql, (st) -> {
            st.setInt(1, entity.getBookId());
            st.setInt(2, entity.getUserId());
            st.setInt(3, entity.getActionType().value());
            st.setDate(4, new Date(entity.getDate().getTime()));
            st.setInt(5, entity.getId());
        });
        return true;
    }

    @Override
    public boolean add(History entity) {
        String sql = "insert histories(bookId, userId, actionType, insertDate) values(?, ?, ?, ?)";
        sqlExecutor.executeProcess(sql, (st) -> {
            st.setInt(1, entity.getBookId());
            st.setInt(2, entity.getUserId());
            st.setInt(3, entity.getActionType().value());
            st.setDate(4, new Date(entity.getDate().getTime()));
        });
        return true;
    }

    @Override
    public boolean delete(History entity) {
        String sql = "delete from histories where id = ?";
        sqlExecutor.executeProcess(sql, (st) -> {
            st.setInt(1, entity.getId());
        });
        return true;
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
