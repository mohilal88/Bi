package services;

import org.dataSourceConnector.entities.DataModel;
import org.dataSourceConnector.interfaces.DataSourceConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QueryExecutor {
    private DataSourceConnector dataSourceConnector;

    public QueryExecutor(DataSourceConnector dataSourceConnector) {
        this.dataSourceConnector = dataSourceConnector;
    }

    public List<DataModel> executeQuery (String query) throws SQLException {
        try {
            return this.dataSourceConnector.fetchData(query);
        } catch (SQLException e) {
            throw new SQLException("Query execution failed" + e.getMessage());
        }
    }
}
