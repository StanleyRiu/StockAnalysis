import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlJsoupParser {
    String Statement_of_Comprehensive_Income_url = "https://mops.twse.com.tw/mops/web/t163sb04";    //綜合損益表
    String Statement_of_Financial_Position_url = "https://mops.twse.com.tw/mops/web/t163sb05";      //資產負債表
    String Statement_of_Operating_Profit_url = "https://mops.twse.com.tw/mops/web/t163sb06";        //營益分析查詢彙總表

    public HtmlJsoupParser() {
    }

    public ArrayList<Statement_of_Comprehensive_Income> getStatementOfComprehensiveIncome(String year, String season, String type) {
        Connection.Response response = null;
        ArrayList<Statement_of_Comprehensive_Income> al = new ArrayList<Statement_of_Comprehensive_Income>();
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
/*
            Element company = doc.select("tr.tblHead").first();
            Elements heads = company.select("th:eq(0), th:eq(1), th:last-child");
            for (Element e : heads) {
                System.out.print(e.text().replace(" ", "")+" ");
            }
            System.out.println();
*/
            Statement_of_Comprehensive_Income soci = null;
            Elements companies = doc.select("tr.even, tr.odd");
            for (Element element : companies) {
                Elements es = element.select("td:eq(0), td:eq(1), td:last-child");

                soci = new Statement_of_Comprehensive_Income();

                soci.setYear(Integer.parseInt(year));
                soci.setSeason(Integer.parseInt(season));
                soci.setType(type);

                List<String> list = es.eachText();
                soci.setId(list.get(0));
                soci.setName(list.get(1));
                soci.setEps(Float.parseFloat(list.get(2)));

                al.add(soci);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return al;
    }

    public ArrayList<Statement_of_Operating_Profit> getStatementOfOperatingProfit(String year, String season, String type) {
        ArrayList<Statement_of_Operating_Profit> al = new ArrayList<Statement_of_Operating_Profit>();
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
            /*
            Element company = doc.select("tr.tblHead").first();
            Elements heads = company.select("td:not(td:eq(2))");
            for (Element e : heads) {
                System.out.print(e.text().replace(" ", "")+" ");
            }
            System.out.println();
             */
            Statement_of_Operating_Profit soop = null;
            Elements companies = doc.select("tr.even, tr.odd");
            for (Element element : companies) {
                Elements es = element.select("td:not(td:eq(2))");

                soop = new Statement_of_Operating_Profit();
                soop.setYear(Integer.parseInt(year));
                soop.setSeason(Integer.parseInt(season));
                soop.setType(type);

                List<String> list = es.eachText();
                soop.setId(list.get(0));
                soop.setName(list.get(1));
                soop.setGross_margin(Float.parseFloat(list.get(2)));
                soop.setOperating_profit_Margin(Float.parseFloat(list.get(3)));
                soop.setPre_Tax_Profit_Margin(Float.parseFloat(list.get(4)));
                soop.setProfit_Margin(Float.parseFloat(list.get(5)));

                al.add(soop);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return al;
    }
}
