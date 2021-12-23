public class StockAnalysis {
    public static void main(String[] args) {
        HtmlJsoupParser parser = new HtmlJsoupParser();
        //sii, otc
//        parser.getStatementOfComprehensiveIncome("110", "01", "sii");
//        parser.getStatementOfOperatingProfit("110", "01", "sii");
        new SQLite().createTable("Statement_of_Comprehensive_Income");
    }
}
