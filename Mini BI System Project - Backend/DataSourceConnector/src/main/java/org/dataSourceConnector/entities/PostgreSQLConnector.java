package org.dataSourceConnector.entities;

import org.dataSourceConnector.interfaces.DataSourceConnector;

import java.sql.*;
import java.util.*;

public class PostgreSQLConnector implements DataSourceConnector {

    private Connection connection;

    @Override
    public boolean connect(String url,String username, String password) throws SQLException {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            throw new SQLException("Connection failed: " + e.getMessage());
        }
    }

    @Override
    public boolean disconnect() throws SQLException {
        if (this.connection != null) {
            if (this.connection.isClosed()) {
                throw new SQLException("Connection is already closed.");
            } else {
                try {
                    this.connection.close();
                    if (this.connection.isClosed()) {
                        return true;
                    }
                    throw new SQLException("Close connection failed..");
                } catch (SQLException e) {
                    throw new SQLException("Close connection failed: " + e.getMessage());
                }
            }
        }
        throw new SQLException("No active connection to close.");
    }

    @Override
    public List<DataModel> fetchData(String query) throws SQLException{

        List<DataModel> data = new ArrayList<>();

        if (this.connection == null) {
            throw new SQLException("No connection to the database. Please connect first.");
        }

        try (Statement stmt = connection.createStatement()) {


            try (ResultSet rs = stmt.executeQuery(query)) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(i);
                        row.put(columnName, value);
                    }
                    data.add(new DataModel(row));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Data fetch failed: " + e.getMessage());
        }

        return data;
    }

    @Override
    public Map<String,ArrayList<Column>> getMetadata() throws SQLException {
        Map<String, ArrayList<Column>> databaseStructure = new HashMap<>();

        if (this.connection == null) {
            throw new SQLException("No connection to the database. Please connect first.");
        }

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                ArrayList<Column> columns = new ArrayList<>();

                Set<String> primaryKeys = getPrimaryKeys(metaData, tableName);
                Set<String> uniqueColumns = getUniqueColumns(metaData, tableName);
                Set<String> foreignKeys = getForeignKeys(metaData, tableName);
                Set<String> indexedColumns = getIndexedColumns(metaData, tableName);

                ResultSet columnsResult = metaData.getColumns(null, null, tableName, "%");
                while (columnsResult.next()) {
                    String columnName = columnsResult.getString("COLUMN_NAME");
                    String columnType = columnsResult.getString("TYPE_NAME");
                    String columnSize = columnsResult.getString("COLUMN_SIZE");
                    boolean isPrimaryKey = primaryKeys.contains(columnName);
                    boolean isForeignKey = foreignKeys.contains(columnName);
                    boolean isNullable = "YES".equals(columnsResult.getString("IS_NULLABLE"));
                    Object defaultValue = columnsResult.getObject("COLUMN_DEF");
                    String description = columnsResult.getString("REMARKS");
                    boolean isIndexed = indexedColumns.contains(columnName);
                    String indexType = getIndexType(metaData, tableName, columnName);
                    boolean isAutoIncrement = "YES".equals(columnsResult.getString("IS_AUTOINCREMENT"));
                    String remarks = columnsResult.getString("REMARKS");
                    boolean isUnique = uniqueColumns.contains(columnName);
                    Column column = new Column(columnName, columnType, columnSize, isPrimaryKey, isForeignKey, isNullable,
                            defaultValue, description, isIndexed, indexType, isAutoIncrement,
                            remarks, isUnique);
                    columns.add(column);
                }
                databaseStructure.put(tableName, columns);
            }
            tables.close();

        } catch (SQLException e) {
            throw new SQLException("Metadata fetch failed: " + e.getMessage());
        }

        return databaseStructure;
    }


    private Set<String> getPrimaryKeys(DatabaseMetaData metaData, String tableName) throws SQLException {
        Set<String> primaryKeys = new HashSet<>();
        ResultSet pkResult = metaData.getPrimaryKeys(null, null, tableName);

        while (pkResult.next()) {
            String columnName = pkResult.getString("COLUMN_NAME");
            primaryKeys.add(columnName);
        }
        return primaryKeys;
    }

    private Set<String> getUniqueColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        Set<String> uniqueColumns = new HashSet<>();
        ResultSet indexInfo = metaData.getIndexInfo(null, null, tableName, true, true);

        while (indexInfo.next()) {
            if (!indexInfo.getBoolean("NON_UNIQUE")) {
                String columnName = indexInfo.getString("COLUMN_NAME");
                uniqueColumns.add(columnName);
            }
        }
        return uniqueColumns;
    }

    private Set<String> getForeignKeys(DatabaseMetaData metaData, String tableName) throws SQLException {
        Set<String> foreignKeys = new HashSet<>();
        ResultSet foreignKeyInfo = metaData.getImportedKeys(null, null, tableName);

        while (foreignKeyInfo.next()) {
            String columnName = foreignKeyInfo.getString("FKCOLUMN_NAME");
            foreignKeys.add(columnName);
        }
        return foreignKeys;
    }

    private Set<String> getIndexedColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        Set<String> indexedColumns = new HashSet<>();
        ResultSet indexInfo = metaData.getIndexInfo(null, null, tableName, false, false);

        while (indexInfo.next()) {
            String columnName = indexInfo.getString("COLUMN_NAME");
            indexedColumns.add(columnName);
        }
        return indexedColumns;
    }

    private String getIndexType(DatabaseMetaData metaData, String tableName, String columnName) throws SQLException {
        ResultSet indexInfo = metaData.getIndexInfo(null, null, tableName, false, false);

        while (indexInfo.next()) {
            if (columnName.equals(indexInfo.getString("COLUMN_NAME"))) {
                return indexInfo.getString("TYPE");
            }
        }
        return null;
    }


}
