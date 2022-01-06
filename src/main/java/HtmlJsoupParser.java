import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class HtmlJsoupParser {
    String Statement_of_Comprehensive_Income_url = "https://mops.twse.com.tw/mops/web/t163sb04";    //綜合損益表
    String Statement_of_Operating_Profit_url = "https://mops.twse.com.tw/mops/web/t163sb06";        //營益分析查詢彙總表
    String Statement_Of_Dividend_Distribution_url = "https://mops.twse.com.tw/server-java/t05st09sub";  //股利分派情形彙總表
//    String Statement_of_Financial_Position_url = "https://mops.twse.com.tw/mops/web/t163sb05";      //資產負債表

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

                NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                Number number = null;
                float f = 0;
/*
                DecimalFormat df = new DecimalFormat();
                DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                dfs.setDecimalSeparator('.');
                dfs.setGroupingSeparator(',');
                df.setDecimalFormatSymbols(dfs);
                number = df.parse(list.get(2));
*/
                number = format.parse(list.get(2));
                f = number.floatValue();
                soop.setGross_margin(f);

                number = format.parse(list.get(3));
                f = number.floatValue();
                soop.setOperating_profit_Margin(f);

                number = format.parse(list.get(4));
                f = number.floatValue();
                soop.setPre_Tax_Profit_Margin(f);

                number = format.parse(list.get(5));
                f = number.floatValue();
                soop.setProfit_Margin(f);

                al.add(soop);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
//           e.printStackTrace();
        }
        return al;
    }

    public ArrayList<Statement_Of_Dividend_Distribution> getStatementOfDividendDistribution(String year, String type) {
        ArrayList<Statement_Of_Dividend_Distribution> al = new ArrayList<Statement_Of_Dividend_Distribution>();
        Connection.Response response = null;

        try {
            response = Jsoup.connect(Statement_Of_Dividend_Distribution_url)
                    .method(Connection.Method.POST)
                    .data("step", "1")
                    .data("TYPEK", type)
                    .data("YEAR", year)     //要大寫
                    .data("first", "")
                    .data("qryType", "1")
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
            Statement_Of_Dividend_Distribution sodd= null;
            Elements companies = doc.select("tr.even, tr.odd");
            for (Element element : companies) {
                Elements es = element.select("td:eq(0), td:eq(2), td:eq(11), td:eq(12), td:eq(13), td:eq(15), td:eq(16), td:eq(17)");
                List<String> list = es.eachText();
//                list.stream().map(x -> new BigDecimal(x).stripTrailingZeros().toString()).collect(Collectors.joining(",")).lines().forEach(System.out::println);
                for (String s : list) {

                }
/*
                sodd = new Statement_Of_Dividend_Distribution();
                sodd.setYear(Integer.parseInt(year));
                sodd.setType(type);

                List<String> list = es.eachText();
                sodd.setId(list.get(0));
                sodd.setName(list.get(1));

                NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                Number number = null;
                float f = 0;

//                DecimalFormat df = new DecimalFormat();
//                DecimalFormatSymbols dfs = new DecimalFormatSymbols();
//                dfs.setDecimalSeparator('.');
//                dfs.setGroupingSeparator(',');
//                df.setDecimalFormatSymbols(dfs);
//                number = df.parse(list.get(2));

                number = format.parse(list.get(2));
                f = number.floatValue();
                sodd.setGross_margin(f);

                number = format.parse(list.get(3));
                f = number.floatValue();
                sodd.setOperating_profit_Margin(f);

                number = format.parse(list.get(4));
                f = number.floatValue();
                sodd.setPre_Tax_Profit_Margin(f);

                number = format.parse(list.get(5));
                f = number.floatValue();
                sodd.setProfit_Margin(f);
*/
                al.add(sodd);
            }
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (ParseException e) {
//           e.printStackTrace();
        }
        return al;
    }
    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }
}
