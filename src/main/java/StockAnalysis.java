public class StockAnalysis {
    public static void main(String[] args) {
        HtmlJsoupParser parser = new HtmlJsoupParser();
//        parser.getStatementOfComprehensiveIncome("110", "01");
        parser.getOperatingProfitMarginAnalysis("110", "01");
    }
}
