package queryBuilder.impl;

import Entities.Query;
import interfaces.QueryBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostgreSQLQueryBuilder implements QueryBuilder {

    private Query query;
    private List<String> columnNames;
    private String condition;
    private String tableName ;
    private int limit ;
    private StringBuilder strBuilder;
    public PostgreSQLQueryBuilder() {
        this.query = new Query();
        this.columnNames = new ArrayList<>();
        this.strBuilder = new StringBuilder();
    }

    @Override
    public void selectColumns(List<String> columns) {
        if(columns != null){
            this.query.setColumns(columns);
            this.columnNames.addAll(columns);
        }

    }

    @Override
    public void fromTable(String tableName) {
        if(tableName != null) {
            this.query.setTableName(tableName);
            this.tableName = tableName;
        }
    }

    @Override
    public void addCondition(String condition) {
        if(condition != null) {
            this.condition = condition;
        }
    }

    @Override
    public void setLimit(int limit) {
        this.query.setLimit(limit);
        this.limit = limit;
    }

    @Override
    public String buildQuery() {
        this.strBuilder.append("SELECT ");
        this.columnNames.forEach(c -> {
            this.strBuilder.append(c);
            if(!c.equals(this.columnNames.get(this.columnNames.size()-1)))
                this.strBuilder.append(" , ");
        });
        this.strBuilder.append(" ");
        this.strBuilder.append("FROM ");
        this.strBuilder.append(tableName);
        this.strBuilder.append(" WHERE ");
        this.strBuilder.append(this.condition);
        this.strBuilder.append(" ");
        this.strBuilder.append("LIMIT ");
        this.strBuilder.append(this.limit);
        return this.strBuilder.toString();
    }


}
