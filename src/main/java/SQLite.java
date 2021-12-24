import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SQLite {
    String db_name = "stock_analysis.sqlite";
    Map<String, String> Statement_of_Comprehensive_Income =    //綜合損益表
        Map.ofEntries(
            Map.entry("year", "integer"),
            Map.entry("season", "integer"),
            Map.entry("type", "string"),
            Map.entry("id", "string"),
            Map.entry("name", "string"),
            Map.entry("eps", "float")
        );
    String Statement_of_Financial_Position;     //資產負債表
    String Statement_of_Cash_Flows;             //現金流量表
    String Statement_of_Operating_Profit;       //營益分析查詢彙總表
    Connection connection = null;

    public SQLite() {
        // create a database connection
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+db_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String generateCreateTableSQL(String tableSchema) {
        String sql = "create table " + tableSchema.toString() + " ("
                + Statement_of_Comprehensive_Income
                .entrySet()
                .stream()
                .map(e -> e.getKey()+" "+e.getValue())
                .collect(Collectors.joining(", "))
                + ");";
        return sql;
    }

    public void createTable(String table) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists " + table);
            statement.executeUpdate(generateCreateTableSQL(table));
//            statement.executeUpdate("insert into person values(1, 'leo')");
//            statement.executeUpdate("insert into person values(2, 'yui')");
//            ResultSet rs = statement.executeQuery("select * from "+table);
//            while(rs.next())
//            {
                // read the result set
//                System.out.println("name = " + rs.getString("name"));
//                System.out.println("id = " + rs.getInt("id"));
//            }
        } catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}
