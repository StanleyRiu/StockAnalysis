import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class StatementOfOperatingProfit extends SQLite {
    final String TABLE_NAME = "Statement_of_Operating_Profit";
    Map<String, String> Statement_of_Operating_Profit = Map.ofEntries(
            Map.entry("year", "integer"),
            Map.entry("season", "integer"),
            Map.entry("type", "string"),    //sii, otc
            Map.entry("id", "string"),
            Map.entry("name", "string"),
            Map.entry("gross_margin", "float"),
            Map.entry("Operating_profit_Margin", "float"),
            Map.entry("Pre_Tax_Profit_Margin", "float"),
            Map.entry("Profit_Margin", "float")
    );  //營益分析查詢彙總表

    public StatementOfOperatingProfit() {
        MapOfStatements = Map.ofEntries(
            Map.entry("Statement_of_Operating_Profit", Statement_of_Operating_Profit)
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

    public void insertIntoTable(ArrayList<Statement_of_Operating_Profit> al) {
        Statement_of_Operating_Profit soop = null;
        Iterator<Statement_of_Operating_Profit> it = null;
        String sql = "insert into Statement_of_Operating_Profit(year, season, type, id, name, gross_margin, Operating_profit_Margin, Pre_Tax_Profit_Margin, Profit_Margin) values(?,?,?,?,?,?,?,?,?)";
        it = al.iterator();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            while (it.hasNext()) {
                soop = it.next();
                preparedStatement.setInt(1, soop.getYear());
                preparedStatement.setInt(2, soop.getSeason());
                preparedStatement.setString(3, soop.getType());
                preparedStatement.setString(4, soop.getId());
                preparedStatement.setString(5, soop.getName());
                preparedStatement.setFloat(6, soop.getGross_margin());
                preparedStatement.setFloat(7, soop.getOperating_profit_Margin());
                preparedStatement.setFloat(8, soop.getPre_Tax_Profit_Margin());
                preparedStatement.setFloat(9, soop.getProfit_Margin());
                preparedStatement.executeUpdate();
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

    ArrayList<Statement_of_Operating_Profit> queryTable(String sql) {
        ArrayList<Statement_of_Operating_Profit> al = new ArrayList<Statement_of_Operating_Profit>();
        String defaultSql = "select * from Statement_of_Operating_Profit";
        if (sql != null) defaultSql += " where " + sql;
        Statement_of_Operating_Profit soop = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(defaultSql);

            while (rs.next()) {
                //read the result set
                soop = new Statement_of_Operating_Profit();
                soop.setYear(rs.getInt("year"));
                soop.setSeason(rs.getInt("season"));
                soop.setType(rs.getString("type"));
                soop.setId(rs.getString("id"));
                soop.setName(rs.getString("name"));
                soop.setGross_margin(rs.getFloat("gross_margin"));
                soop.setOperating_profit_Margin(rs.getFloat("Operating_profit_Margin"));
                soop.setPre_Tax_Profit_Margin(rs.getFloat("Pre_Tax_Profit_Margin"));
                soop.setProfit_Margin(rs.getFloat("Profit_Margin"));
                al.add(soop);
            }
            System.out.println(al.size());
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

class Statement_of_Operating_Profit {
    int year;
    int season;
    String type;
    String id;
    String name;
    float gross_margin;
    float Operating_profit_Margin;
    float Pre_Tax_Profit_Margin;
    float Profit_Margin;

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

    public float getGross_margin() {
        return gross_margin;
    }

    public void setGross_margin(float gross_margin) {
        this.gross_margin = gross_margin;
    }

    public float getOperating_profit_Margin() {
        return Operating_profit_Margin;
    }

    public void setOperating_profit_Margin(float operating_profit_Margin) {
        Operating_profit_Margin = operating_profit_Margin;
    }

    public float getPre_Tax_Profit_Margin() {
        return Pre_Tax_Profit_Margin;
    }

    public void setPre_Tax_Profit_Margin(float pre_Tax_Profit_Margin) {
        Pre_Tax_Profit_Margin = pre_Tax_Profit_Margin;
    }

    public float getProfit_Margin() {
        return Profit_Margin;
    }

    public void setProfit_Margin(float profit_Margin) {
        Profit_Margin = profit_Margin;
    }

    public void println() {
        System.out.println(getYear()+"\t"+getSeason()+"\t"+getType()+"\t"+getId()+"\t"+getName()+"\t"+getGross_margin()+"\t"+getOperating_profit_Margin()+"\t"+getPre_Tax_Profit_Margin()+"\t"+getProfit_Margin());
    }
}