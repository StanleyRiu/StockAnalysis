import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class StatementOfComprehensiveIncome extends SQLite {
    final String TABLE_NAME = "Statement_of_Comprehensive_Income";
    Map<String, String> Statement_of_Comprehensive_Income = Map.ofEntries(
            Map.entry("year", "integer"),
            Map.entry("season", "integer"),
            Map.entry("type", "string"),    //sii, otc
            Map.entry("id", "string"),
            Map.entry("name", "string"),
            Map.entry("eps", "float")
    );  //綜合損益表

    public StatementOfComprehensiveIncome() {
        MapOfStatements = Map.ofEntries(
                Map.entry("Statement_of_Comprehensive_Income", Statement_of_Comprehensive_Income)
//            Map.entry("Statement_of_Operating_Profit", Statement_of_Operating_Profit),
//            Map.entry("Statement_of_Financial_Position", Statement_of_Financial_Position),
//            Map.entry("Statement_of_Cash_Flows", Statement_of_Cash_Flows)
        );
    }

    String generateCreateTableSQL(String TABLE_NAME) {
        String sql = "";
        sql = "create table " + TABLE_NAME + " ("
                + MapOfStatements.get(TABLE_NAME)
                .entrySet()
                .stream()
                .map(e -> e.getKey()+" "+e.getValue())
                .collect(Collectors.joining(", "))
                + ");";
        return sql;
    }

    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("drop table if exists " + TABLE_NAME);
            statement.executeUpdate(generateCreateTableSQL(TABLE_NAME));
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

    public void insertIntoTable(Statement_of_Comprehensive_Income sci) {
        String sql = "insert into Statement_of_Comprehensive_Income values(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(0, sci.getYear());
            preparedStatement.setInt(1, sci.getSeason());
            preparedStatement.setString(2, sci.getType());
            preparedStatement.setString(3, sci.getId());
            preparedStatement.setString(4, sci.getName());
            preparedStatement.setFloat(5, sci.getEps());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    ArrayList<Statement_of_Comprehensive_Income> queryTable(String sql) {
        String defaultSql = "select * from Statement_of_Comprehensive_Income";
        if (sql != null) defaultSql += " where " + sql;

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(defaultSql);
            ArrayList<Statement_of_Comprehensive_Income> al = new ArrayList<Statement_of_Comprehensive_Income>();
            while(rs.next()) {
                //read the result set
                Statement_of_Comprehensive_Income soci = new Statement_of_Comprehensive_Income();
                soci.setYear(rs.getInt("year"));
                soci.setSeason(rs.getInt("season"));
                soci.setType(rs.getString("type"));
                soci.setId(rs.getString("id"));
                soci.setName(rs.getString("name"));
                soci.setEps(rs.getFloat("eps"));
                al.add(soci);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static class Statement_of_Comprehensive_Income {
        int year;
        int season;
        String type;
        String id;
        String name;
        float eps;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getSeason() {
            return season;
        }

        public void setSeason(int season) {
            this.season = season;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getEps() {
            return eps;
        }

        public void setEps(float eps) {
            this.eps = eps;
        }
    }
}
