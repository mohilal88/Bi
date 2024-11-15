package org.SQLHub.entities;

import org.SQLHub.ineterfaces.QueryBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLQueryBuilder implements QueryBuilder {

    private List<String> columns = new ArrayList<>();
    private String tableName;
    private List<String> conditions = new ArrayList<>();
    private List<String> operators = new ArrayList<>();
    private String orderBy;
    private Integer limit;

    @Override
    public SQLQueryBuilder addColumns(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    @Override
    public SQLQueryBuilder setTable(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SQLQueryBuilder addCondition(String condition) {
        return addCondition(condition, "AND");
    }


    public SQLQueryBuilder addCondition(String condition, String operator) {
        if (!conditions.isEmpty()) {
            operators.add(operator);
        }
        conditions.add(condition);
        return this;
    }

    @Override
    public SQLQueryBuilder setOrderBy(String column, String sortOrder) {
        this.orderBy = "ORDER BY " + column + " " + sortOrder;
        return this;
    }

    @Override
    public SQLQueryBuilder setLimitation(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public String build() {
        StringBuilder query = new StringBuilder("SELECT ");

        query.append(columns.isEmpty() ? "*" : String.join(", ", columns));

        query.append(" FROM ").append(tableName);

        if (!conditions.isEmpty()) {
            query.append(" WHERE ").append(conditions.get(0));
            for (int i = 1; i < conditions.size(); i++) {
                query.append(" ").append(operators.get(i - 1)).append(" ").append(conditions.get(i));
            }
        }

        if (orderBy != null) {
            query.append(" ").append(orderBy);
        }

        if (limit != null) {
            query.append(" LIMIT ").append(limit);
        }

        return query.toString();
    }
}
