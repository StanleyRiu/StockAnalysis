import java.util.ArrayList;

public class StockAnalysis {
    public static void main(String[] args) {
        HtmlJsoupParser parser = new HtmlJsoupParser();

        StatementOfComprehensiveIncome soci = new StatementOfComprehensiveIncome();
        soci.createTable();
        ArrayList<Statement_of_Comprehensive_Income> al_soci = null;
        al_soci = parser.getStatementOfComprehensiveIncome("110", "01", "sii");
        soci.insertIntoTable(al_soci);
//        al_soci = soci.queryTable(null);
//        al_soci.stream().forEach(x -> x.println());

        StatementOfOperatingProfit soop = new StatementOfOperatingProfit();
        soop.createTable();
        ArrayList<Statement_of_Operating_Profit> al_soop = null;
        al_soop = parser.getStatementOfOperatingProfit("110", "01", "sii");
        soop.insertIntoTable(al_soop);
        al_soop = soop.queryTable(null);
//        al_soop.stream().forEach(x -> x.println());
        System.out.println(al_soop.size());
    }
}
