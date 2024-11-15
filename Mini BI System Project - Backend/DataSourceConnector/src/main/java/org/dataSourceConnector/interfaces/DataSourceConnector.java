package org.dataSourceConnector.interfaces;

import org.dataSourceConnector.entities.Column;
import org.dataSourceConnector.entities.DataModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DataSourceConnector {
    public boolean connect(String url,String username, String password) throws SQLException;
    public boolean disconnect() throws SQLException;
    public List<DataModel> fetchData(String query) throws SQLException;
    public Map<String, ArrayList<Column>>  getMetadata() throws SQLException ;

}
