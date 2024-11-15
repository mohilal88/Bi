package services;

import Entities.Query;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;


public class QueryManager {
    private final Connection connection;

    public QueryManager(Connection connection) {
        this.connection = connection;
    }


public int save(Query query) throws SQLException {
    String sql = "INSERT INTO queries (created_date, query_text, table-name, column_names , condition , limitation ) VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {

        stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        stmt.setString(2, query.getQueryText());
        stmt.setString(3, query.getTableName());
        stmt.setString(4, String.join(",", query.getColumns()));
        stmt.setString(5, query.getCondition());
        stmt.setInt(6, query.getLimit());

       return stmt.executeUpdate();


    }
}

public void delete(int id) throws SQLException {
    String sql = "DELETE FROM queries WHERE id = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
    public Query findById(int id) throws SQLException {
        String sql = "SELECT * FROM queries WHERE id = ?";
        Query query = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    query = extractQueryFromResultSet(rs);
                }
            }
        }
        return query;
    }

    private Query extractQueryFromResultSet(ResultSet rs) throws SQLException {
        Query query = new Query();
        query.setCreatedDate(rs.getDate("created_date"));
        query.setQueryText(rs.getString("query_text"));
        query.setTableName(rs.getString("table_name"));
        query.setColumns(Arrays.asList(rs.getString("column_names").split(",")));
        query.setCondition(rs.getString("condition"));
        query.setLimit(rs.getInt("limitation"));

        return query;
    }

    public List<Query> findAll() throws SQLException {
        String sql = "SELECT * FROM queries";
        List<Query> queries = new ArrayList<>();

        try (Statement stmt = connection.createStatement()){
             ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Query query = extractQueryFromResultSet(rs);
                queries.add(query);
            }
        }
        return queries;
    }
}
