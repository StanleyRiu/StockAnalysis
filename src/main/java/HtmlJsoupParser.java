import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HtmlJsoupParser {
    String Statement_of_Comprehensive_Income_url = "https://mops.twse.com.tw/mops/web/t163sb04";    //綜合損益表
    String Statement_of_Financial_Position_url = "https://mops.twse.com.tw/mops/web/t163sb05";      //資產負債表
    String Statement_of_Operating_Profit_url = "https://mops.twse.com.tw/mops/web/t163sb06";        //營益分析查詢彙總表

    public HtmlJsoupParser() {
    }

    public void getStatementOfComprehensiveIncome(String year, String season, String type) {
        Connection.Response response = null;
        try {
            response = Jsoup.connect(Statement_of_Comprehensive_Income_url)
                    .method(Connection.Method.POST)
                    .data("encodeURIComponent", "1")
                    .data("step", "1")
                    .data("firstin", "1")
                    .data("off", "1")
                    .data("isQuery", "Y")
                    .data("TYPEK", type)   //sii, otc
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

    public void getStatementOfOperatingProfit(String year, String season, String type) {
        Connection.Response response = null;
        try {
            response = Jsoup.connect(Statement_of_Operating_Profit_url)
                    .method(Connection.Method.POST)
                    .data("encodeURIComponent", "1")
                    .data("step", "1")
                    .data("firstin", "1")
                    .data("off", "1")
                    .data("isQuery", "Y")
                    .data("TYPEK", type)
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
