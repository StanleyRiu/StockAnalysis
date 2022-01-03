import java.sql.*;
import java.util.*;

public class SQLite {
    String db_name = "stock_analysis.sqlite";
    Map<String, String> Statement_of_Operating_Profit = Map.ofEntries(
            Map.entry("year", "integer"),
            Map.entry("season", "integer"),
            Map.entry("type", "string"),    //sii, otc
            Map.entry("id", "string"),
            Map.entry("name", "string"),
            Map.entry("gross margin", "float"),
            Map.entry("Operating profit Margin", "float"),
            Map.entry("Pre-Tax Profit Margin", "float"),
            Map.entry("Profit Margin", "float")
    );  //營益分析查詢彙總表
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
