import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HtmlJsoupParser {
    String StatementOfComprehensiveIncomeURL = "https://mops.twse.com.tw/mops/web/t163sb04";
    String OperatingProfitMarginAnalysisURL = "https://mops.twse.com.tw/mops/web/t163sb06";
    public HtmlJsoupParser() {
    }

    public void getStatementOfComprehensiveIncome(String year, String season) {
        Connection.Response response = null;
        try {
            response = Jsoup.connect(StatementOfComprehensiveIncomeURL)
                    .method(Connection.Method.POST)
                    .data("encodeURIComponent", "1")
                    .data("step", "1")
                    .data("firstin", "1")
                    .data("off", "1")
                    .data("isQuery", "Y")
                    .data("TYPEK", "sii")
                    .data("year", year)
                    .data("season", season)
                    .execute();
            Document doc = response.parse();
            Element company = doc.select("tr.tblHead").first();
            Elements heads = company.select("th:eq(0), th:eq(1), th:last-child");
            for (Element e : heads) {
                System.out.print(e.text().replace(" ", "")+" ");
            }
            System.out.println();
            Elements companies = doc.select("tr.even, tr.odd");
            for (Element element : companies) {
                Elements info = element.select("td:eq(0), td:eq(1), td:last-child");
                System.out.println(info.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getOperatingProfitMarginAnalysis(String year, String season) {
        Connection.Response response = null;
        try {
            response = Jsoup.connect(OperatingProfitMarginAnalysisURL)
                    .method(Connection.Method.POST)
                    .data("encodeURIComponent", "1")
                    .data("step", "1")
                    .data("firstin", "1")
                    .data("off", "1")
                    .data("isQuery", "Y")
                    .data("TYPEK", "sii")
                    .data("year", year)
                    .data("season", season)
                    .execute();
            Document doc = response.parse();
            Element company = doc.select("tr.tblHead").first();
            Elements heads = company.select("td:not(td:eq(2))");
            for (Element e : heads) {
                System.out.print(e.text().replace(" ", "")+" ");
            }
            System.out.println();
            Elements companies = doc.select("tr.even, tr.odd");
            for (Element element : companies) {
                Elements info = element.select("td:not(td:eq(2))");
                System.out.println(info.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
