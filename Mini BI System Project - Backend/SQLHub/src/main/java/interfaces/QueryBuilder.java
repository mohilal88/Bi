package interfaces;

import java.util.List;

public interface QueryBuilder {
    void selectColumns(List<String> columns);
    void fromTable(String tableName);
    void addCondition(String condition);
    void setLimit(int limit);
    String buildQuery();
}
