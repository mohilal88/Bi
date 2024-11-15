package org.example;

import interfaces.QueryBuilder;
import queryBuilder.impl.PostgreSQLQueryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        PostgreSQLQueryBuilder sq = new PostgreSQLQueryBuilder();
        sq.addCondition("id > 10");
        sq.fromTable("employees");
        sq.selectColumns(List.of("name" , "salary" ));
        sq.setLimit(100);
        System.out.println(sq.buildQuery());
    }
}