package com.auction.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {

    private final DataSource dataSource;

    protected BaseDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    protected void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignored) {
                // ignored
            }
        }
    }

    protected void closeQuietly(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ignored) {
                // ignored
            }
        }
    }
}


