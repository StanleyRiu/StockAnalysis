public class StockAnalysis {
    public static void main(String[] args) {
        HtmlJsoupParser parser = new HtmlJsoupParser();

        StatementOfComprehensiveIncome soci = new StatementOfComprehensiveIncome();
        soci.createTable();
        parser.getStatementOfComprehensiveIncome("110", "01", "sii");

//        new SQLite().createTable("Statement_of_Operating_Profit");

        //sii, otc
        parser.getStatementOfOperatingProfit("110", "01", "sii");
    }
}
