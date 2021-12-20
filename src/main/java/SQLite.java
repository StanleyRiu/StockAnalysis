import java.sql.*;

public class SQLite {
    String db_name = "stock.db";
    String Statement_of_Comprehensive_Income;   //綜合損益表
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

    public void createTable(String table) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists "+table);
            statement.executeUpdate("create table person (id string, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
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
