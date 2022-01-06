import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StockAnalysis {
    HtmlJsoupParser parser = null;
    int lastTYear = 110;
    int lastSeason = 03;

    public static void main(String[] args) {
        StockAnalysis sa = new StockAnalysis();
//        sa.fetchData(110);
        sa.test();
    }
    void test() {
        parser = new HtmlJsoupParser();
        ArrayList<Statement_Of_Dividend_Distribution> al_soci = null;
        al_soci = parser.getStatementOfDividendDistribution("110", "sii");
        System.out.println(al_soci.size());
    }
    void fetchData(int year) {
        parser = new HtmlJsoupParser();

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("Y");
//        System.out.println(Integer.parseInt(sdf.format(date)) - 1911);

        /**/
        StatementOfComprehensiveIncome soci = new StatementOfComprehensiveIncome();
        soci.createTable();
        ArrayList<Statement_of_Comprehensive_Income> al_soci = null;

        for (int y = year; y <= lastTYear; y++)
            for (int s = 1; s <= lastSeason; s++) {
                al_soci = parser.getStatementOfComprehensiveIncome(Integer.toString(y), Integer.toString(s), "sii");
                soci.insertIntoTable(al_soci);
            }
        al_soci = soci.queryTable(null);
        soci.closeConnection(); //SQLite can't support a high level of concurrency.
//        al_soci.stream().forEach(x -> x.println());
        System.out.println(al_soci.size());

        /**/
        StatementOfOperatingProfit soop = new StatementOfOperatingProfit();
        soop.createTable();
        ArrayList<Statement_of_Operating_Profit> al_soop = null;

        for (int y = year; y <= lastTYear; y++)
            for (int s = 1; s <= lastSeason; s++) {
                al_soop = parser.getStatementOfOperatingProfit(Integer.toString(y), Integer.toString(s), "sii");
                soop.insertIntoTable(al_soop);
            }
        al_soop = soop.queryTable(null);
        soop.closeConnection(); //SQLite can't support a high level of concurrency.
//        al_soop.stream().forEach(x -> x.println());
        System.out.println(al_soop.size());

        /**/

    }
}
