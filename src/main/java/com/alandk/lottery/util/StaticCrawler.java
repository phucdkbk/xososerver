package com.alandk.lottery.util;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class StaticCrawler extends TimerTask {

    private static final Logger logger = Logger.getLogger(StaticCrawler.class);

    public static void crawler() throws Exception {
        try {
            Connection connection = DatabaseUtils.getConnection();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2009);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            String result;
            int dateInt;
            if (connection != null) {
                connection.setAutoCommit(false);
                while (cal.getTime().getTime() < new Date().getTime()) {
                    dateInt = Integer.valueOf(df.format(cal.getTime()));
                    result = crawlerByDate(cal.getTime());
                    String sqlQuery = "insert into xosomienbac.lottery values(?,?)";
                    PreparedStatement prepareStatement = connection
                            .prepareStatement(sqlQuery);
                    prepareStatement.setInt(1, dateInt);
                    prepareStatement.setString(2, result);
                    prepareStatement.execute();
                    connection.commit();
                    cal.add(Calendar.DATE, 1);
                }
            }
        } catch (Exception ex) {
            logger.error("", ex);
            throw ex;
        }
    }

    public static String crawlerByDate(Date date) throws Exception {
        String strResult = "";
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String strDate = df.format(date);
            Document doc = Jsoup.connect(
                    "http://www.minhngoc.net.vn/getkqxs/mien-bac/" + strDate
                    + ".js").get();
            Elements giaidb = doc.getElementsByClass("giaidb");
            String giaidbVal = giaidb.get(0).getAllElements().get(0)
                    .childNode(0).toString();
            Elements giai1 = doc.getElementsByClass("giai1");
            String giai1Val = giai1.get(0).getAllElements().get(0).childNode(0)
                    .toString();
            Elements giai2 = doc.getElementsByClass("giai2");
            String giai2Val = giai2.get(0).getAllElements().get(0).childNode(0)
                    .toString();
            Elements giai3 = doc.getElementsByClass("giai3");
            String giai3Val = giai3.get(0).getAllElements().get(0).childNode(0)
                    .toString();
            Elements giai4 = doc.getElementsByClass("giai4");
            String giai4Val = giai4.get(0).getAllElements().get(0).childNode(0)
                    .toString();
            Elements giai5 = doc.getElementsByClass("giai5");
            String giai5Val = giai5.get(0).getAllElements().get(0).childNode(0)
                    .toString();
            Elements giai6 = doc.getElementsByClass("giai6");
            String giai6Val = giai6.get(0).getAllElements().get(0).childNode(0)
                    .toString();
            Elements giai7 = doc.getElementsByClass("giai7");
            String giai7Val = giai7.get(0).getAllElements().get(0).childNode(0)
                    .toString();

            String[] arrGiai2 = giai2Val.split("-");
            String[] arrGiai3 = giai3Val.split("-");
            String[] arrGiai4 = giai4Val.split("-");
            String[] arrGiai5 = giai5Val.split("-");
            String[] arrGiai6 = giai6Val.split("-");
            String[] arrGiai7 = giai7Val.split("-");

            Result result = new Result();
            result.setGiaiDB(giaidbVal);
            result.setGiaiNhat(giai1Val);
            result.setArrGiaiNhi(arrGiai2);
            result.setArrGiaiBa(arrGiai3);
            result.setArrGiaiTu(arrGiai4);
            result.setArrGiaiNam(arrGiai5);
            result.setArrGiaiSau(arrGiai6);
            result.setArrGiaiBay(arrGiai7);
            result.setHaveFullResult();

            Gson gson = new Gson();
            strResult = gson.toJson(result);

        } catch (IOException e) {
            logger.error(date, e);
            //throw e;
        }
        return strResult;
    }

    @Override
    public void run() {
        try {
            crawler();
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }
}