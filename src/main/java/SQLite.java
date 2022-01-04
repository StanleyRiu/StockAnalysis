import java.sql.*;
import java.util.*;

public class SQLite {
    String db_name = "stock_analysis.sqlite";

    Map<String, String> Statement_of_Financial_Position = Map.ofEntries(

    );  //資產負債表
    Map<String, String> Statement_of_Cash_Flows = Map.ofEntries(

    );  //現金流量表

    Map<String, Map<String, String>> MapOfStatements = null;

    Connection connection = null;

    public SQLite() {
        // create a database connection
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+db_name);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }
}
