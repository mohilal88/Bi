package org.SQLHub.ineterfaces;

public interface QueryBuilder {

    QueryBuilder addColumns(String... columns);

    QueryBuilder setTable(String tableName);

    QueryBuilder addCondition(String condition);

    QueryBuilder setOrderBy(String column, String sortOrder);

    QueryBuilder setLimitation(int limit);

    String build();
}
