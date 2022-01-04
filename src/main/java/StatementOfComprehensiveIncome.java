import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
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
                .map(e -> e.getKey() + " " + e.getValue())
                .collect(Collectors.joining(", "))
                + ");";
        return sql;
    }

    public void createTable() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("drop table if exists " + TABLE_NAME);
            statement.executeUpdate(generateCreateTableSQL(TABLE_NAME));
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertIntoTable(ArrayList<Statement_of_Comprehensive_Income> al) {
        Statement_of_Comprehensive_Income soci = null;
        Iterator<Statement_of_Comprehensive_Income> it = null;
        String sql = "insert into Statement_of_Comprehensive_Income(year, season, type, id, name, eps) values(?,?,?,?,?,?)";
        it = al.iterator();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            while (it.hasNext()) {
                soci = it.next();
                preparedStatement.setInt(1, soci.getYear());
                preparedStatement.setInt(2, soci.getSeason());
                preparedStatement.setString(3, soci.getType());
                preparedStatement.setString(4, soci.getId());
                preparedStatement.setString(5, soci.getName());
                preparedStatement.setFloat(6, soci.getEps());
                preparedStatement.executeUpdate();
//                System.out.println(soci.getYear()+"\t"+soci.getSeason()+"\t"+soci.getType()+"\t"+soci.getId()+'\t'+soci.getName()+'\t'+soci.getEps());
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    ArrayList<Statement_of_Comprehensive_Income> queryTable(String sql) {
        ArrayList<Statement_of_Comprehensive_Income> al = new ArrayList<Statement_of_Comprehensive_Income>();
        String defaultSql = "select * from Statement_of_Comprehensive_Income";
        if (sql != null) defaultSql += " where " + sql;
        Statement_of_Comprehensive_Income soci = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(defaultSql);

            while (rs.next()) {
                //read the result set
                soci = new Statement_of_Comprehensive_Income();
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
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return al;
    }
}

class Statement_of_Comprehensive_Income {
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

    public void println() {
        System.out.println(getYear()+"\t"+getSeason()+"\t"+getType()+"\t"+getId()+"\t"+getName()+"\t"+getEps());
    }
}