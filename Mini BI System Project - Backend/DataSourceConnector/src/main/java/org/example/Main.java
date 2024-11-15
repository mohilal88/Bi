package org.example;

import org.dataSourceConnector.entities.Column;
import org.dataSourceConnector.entities.DataModel;
import org.dataSourceConnector.entities.PostgreSQLConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");


            PostgreSQLConnector postgreSQLConnector=new PostgreSQLConnector();
            try {
                postgreSQLConnector.connect("jdbc:postgresql://localhost:5432/testdatabase","postgres","postgres1234");
                Map<String, ArrayList<Column>> metadata = postgreSQLConnector.getMetadata();
                List<DataModel> data=postgreSQLConnector.fetchData("select * from users ");
                System.out.println(data);
                for (Map.Entry<String, ArrayList<Column>> entry : metadata.entrySet()) {
                    System.out.println(entry.getKey());
                    for (Column column : entry.getValue()) {
                        System.out.println(column);

                    }
                }


            }catch (Exception e){
                System.out.println(e);
            }


            for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}