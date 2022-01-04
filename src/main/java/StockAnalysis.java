import java.util.ArrayList;

public class StockAnalysis {
    public static void main(String[] args) {
        HtmlJsoupParser parser = new HtmlJsoupParser();

        StatementOfComprehensiveIncome soci = new StatementOfComprehensiveIncome();
        soci.createTable();
        ArrayList<Statement_of_Comprehensive_Income> al = null;
        al = parser.getStatementOfComprehensiveIncome("110", "01", "sii");
        soci.insertIntoTable(al);
//        al = soci.queryTable(null); al.stream().forEach(x -> x.println());

//        new SQLite().createTable("Statement_of_Operating_Profit");

        //sii, otc
//        parser.getStatementOfOperatingProfit("110", "01", "sii");
    }
}
