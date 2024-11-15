package org.dataSourceConnector.entities;

import java.sql.ResultSet;

public class Column {
    private final String columnName;
    private final String columnType;
    private final String columnSize;
    private final boolean isPrimaryKey;
    private final boolean isForeignKey;
    private final boolean isNullable;
    private final Object defaultValue;
    private final String description;
    private final boolean isIndexed;
    private final String indexType;
    private final boolean isAutoIncrement;
    private final String remarks;
    private final boolean isUnique;

    // Constructor
    public Column(String columnName, String columnType, String columnSize, boolean isPrimaryKey,
                  boolean isForeignKey, boolean isNullable, Object defaultValue, String description,
                  boolean isIndexed, String indexType, boolean isAutoIncrement, String remarks,
                  boolean isUnique) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnSize = columnSize;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
        this.isNullable = isNullable;
        this.defaultValue = defaultValue;
        this.description = description;
        this.isIndexed = isIndexed;
        this.indexType = indexType;
        this.isAutoIncrement = isAutoIncrement;
        this.remarks = remarks;
        this.isUnique = isUnique;
    }


    public String getColumnName() { return columnName; }

    public String getColumnType() { return columnType; }

    public String getColumnSize() { return columnSize; }

    public boolean isPrimaryKey() { return isPrimaryKey; }

    public boolean isForeignKey() { return isForeignKey; }

    public boolean isNullable() { return isNullable; }

    public Object getDefaultValue() { return defaultValue; }

    public String getDescription() { return description; }

    public boolean isIndexed() { return isIndexed; }

    public String getIndexType() { return indexType; }

    public boolean isAutoIncrement() { return isAutoIncrement; }

    public String getRemarks() { return remarks; }

    public boolean isUnique() { return isUnique; }


    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnSize=" + columnSize +
                ", isPrimaryKey=" + isPrimaryKey +
                ", isForeignKey=" + isForeignKey +
                ", isNullable=" + isNullable +
                ", defaultValue=" + defaultValue +
                ", description='" + description + '\'' +
                ", isIndexed=" + isIndexed +
                ", indexType='" + indexType + '\'' +
                ", isAutoIncrement=" + isAutoIncrement +
                ", remarks='" + remarks + '\'' +
                ", isUnique=" + isUnique +
                '}';
    }


}
