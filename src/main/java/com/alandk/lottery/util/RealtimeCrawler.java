/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alandk.lottery.util;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author phucdk
 */
public class RealtimeCrawler extends TimerTask {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(StaticCrawler.class);

    @Override
    public void run() {
        try {
            Calendar cal = Calendar.getInstance();
            int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
            if (hourOfDay == 18) {
                Connection conn;
                PreparedStatement ps;
                ResultSet rs;

                System.out.println("Start automatic crawling");
                Result currentResult;
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                Date date = new Date();
                int dateInt = Integer.valueOf(df.format(date));
                Gson gson = new Gson();
                conn = DatabaseUtils.getConnection();
                ps = conn.prepareStatement("select * from xosomienbac.lottery a where a.date =?");
                ps.setInt(1, dateInt);
                rs = ps.executeQuery();
                String strResult = "";
                while (rs.next()) {
                    strResult = rs.getString("result");
                }
                if (strResult != null && !strResult.isEmpty()) {
                    currentResult = gson.fromJson(strResult.trim(), Result.class);
                    currentResult.setHaveFullResult();
                    if (!currentResult.isHasFullValue()) {
//                    if (true) {
                        crawler(conn, dateInt);
                    } else {
                        System.out.println("Have full value. Don't need to crawl");
                    }
                } else {
                    crawler(conn, dateInt);
                }
                if (conn != null) {
                    conn.close();
                }
                System.out.println("End automatic crawling");
            } else {
                System.out.println("Do not in crawler time");
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(RealtimeCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ScriptException ex) {
            Logger.getLogger(RealtimeCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RealtimeCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RealtimeCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RealtimeCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
//        catch (InterruptedException ex) {
//            Logger.getLogger(RealtimeCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private void crawler(Connection conn, int dateInt) throws IOException, SQLException, ScriptException {
        Gson gson = new Gson();
        PreparedStatement ps;
        ResultSet rs;
        Result result =null;
        try{
            result = getResultFromXSKTDotComDotvn();
        } catch(Exception ex){
            
        }
        if(result == null){
            //result = getResultFromKetquaDotnet();
        }
        
        //Result result = getResultFromKetquaDotnet();
        ps = conn.prepareStatement("select * from xosomienbac.lottery a where a.date =?");
        ps.setInt(1, dateInt);
        rs = ps.executeQuery();
        DateFormat df = new SimpleDateFormat("ddMM");
        String currentDateMonth = df.format(new Date());
        if (currentDateMonth.equals(result.getDateMonth())) {
            if (rs.next()) {
                String sqlQuery = "update xosomienbac.lottery set result = ? where date = ?";
                PreparedStatement prepareStatement = conn
                        .prepareStatement(sqlQuery);
                prepareStatement.setString(1, gson.toJson(result));
                prepareStatement.setInt(2, dateInt);
                prepareStatement.execute();
                prepareStatement.close();
            } else {
                String sqlQuery = "insert into xosomienbac.lottery values(?,?)";
                PreparedStatement prepareStatement = conn
                        .prepareStatement(sqlQuery);
                prepareStatement.setInt(1, dateInt);
                prepareStatement.setString(2, gson.toJson(result));
                prepareStatement.execute();
                prepareStatement.close();
            }
            if (result.isHasFullValue()) {
            //System.out.println("Sleep to next day");
                //Thread.sleep(getTimeToNextStart());
            }
        }
        ps.close();
    }

    private long getTimeToNextStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 15);
        return cal.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
    }

    /**
     *
     * Retrieve lottery result from xskt.net
     *
     * @return
     * @throws ScriptException
     * @throws IOException
     */
    public static Result getResultFromXSKTDotComDotvn() throws ScriptException, IOException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        Document doc = Jsoup.connect("http://xskt.com.vn/").get();
        Element rmb = doc.getElementById("rmb");
        Elements elements = rmb.getElementsByClass("result-prize");

        Elements resultDate = doc.getElementsByTag("h2");

        String strDate = resultDate.get(0).text();
        strDate = strDate.replaceAll("[^0-9.,]+", "");

        //Giai dac biet
        String data = elements.get(0).data();
        String giaiDB = "";
        if (data.length() > 20) {
            data = data.substring(15, data.length() - 2);
            giaiDB = (String) engine.eval(data);
        }
        if (giaiDB.isEmpty()) {
            giaiDB = Jsoup.parse(elements.get(0).html()).text();
        }

        //Giai nhat
        data = elements.get(1).data();
        String giaiNhat = "";
        if (data.length() > 20) {
            data = data.substring(15, data.length() - 2);
            giaiNhat = (String) engine.eval(data);
        }

        if (giaiNhat.isEmpty()) {
            giaiNhat = Jsoup.parse(elements.get(1).html()).text();
        }

        //Giai nhi
        String giaiNhi = Jsoup.parse(elements.get(2).html()).text();

        //Giai ba
        String giaiBa = Jsoup.parse(elements.get(3).html()).text();

        //Giai tu
        String giaiTu = Jsoup.parse(elements.get(4).html()).text();

        //Giai nam
        String giaiNam = Jsoup.parse(elements.get(5).html()).text();

        //Giai sau
        String giaiSau = Jsoup.parse(elements.get(6).html()).text();

        //Giai bay
        String giaiBay = Jsoup.parse(elements.get(7).html()).text();

        Result result = new Result();
        result.setGiaiDB(giaiDB);
        result.setGiaiNhat(giaiNhat);
        result.setArrGiaiNhi(giaiNhi.split(" "));
        result.setArrGiaiBa(giaiBa.split(" "));
        result.setArrGiaiTu(giaiTu.split(" "));
        result.setArrGiaiNam(giaiNam.split(" "));
        result.setArrGiaiSau(giaiSau.split(" "));
        result.setArrGiaiBay(giaiBay.split(" "));
        result.setHaveFullResult();
        result.setDateMonth(strDate);
//        Gson gson = new Gson();
//        String jsonValue = gson.toJson(result);
        return result;
    }

    /**
     *
     * Retrieve lottery result from ketqua.net
     *
     * @return
     * @throws ScriptException
     * @throws IOException
     */
    public static Result getResultFromKetquaDotnet() throws ScriptException, IOException {
        Document doc = Jsoup.connect("http://ketqua.net").userAgent("Mozilla").get();
        Element element = doc.getElementById("ketqua");
        Elements es = element.children();

        Elements esResult = es.get(0).getElementsByTag("tbody").get(0).children();
        Element e;
        
        Elements resultDate = doc.getElementsByTag("h2");

        String strDate = resultDate.get(1).text();
        strDate = strDate.replaceAll("[^0-9.,]+", "");
        strDate = strDate.substring(0, 4);

        //Giai dac biet
        String giaiDB = esResult.get(0).getElementsByClass("bor").get(0).html();

        //Giai nhat
        String giaiNhat = esResult.get(1).getElementsByClass("bor").get(0).html();

        //Giai nhi
        String[] arrGiaiNhi = new String[2];
        arrGiaiNhi[0] = esResult.get(2).getElementsByClass("f2").get(0).html();
        arrGiaiNhi[1] = esResult.get(2).getElementsByClass("f2").get(1).html();

        //Giai ba
        String[] arrGiaiBa = new String[6];
        arrGiaiBa[0] = esResult.get(3).getElementsByClass("f2").get(0).html();
        arrGiaiBa[1] = esResult.get(3).getElementsByClass("f2").get(1).html();
        arrGiaiBa[2] = esResult.get(3).getElementsByClass("f2").get(2).html();
        arrGiaiBa[3] = esResult.get(4).getElementsByClass("f2").get(0).html();
        arrGiaiBa[4] = esResult.get(4).getElementsByClass("f2").get(1).html();
        arrGiaiBa[5] = esResult.get(4).getElementsByClass("f2").get(2).html();

        //Giai tu
        String[] arrGiaiTu = new String[4];
        arrGiaiTu[0] = esResult.get(5).getElementsByClass("f2").get(0).html();
        arrGiaiTu[1] = esResult.get(5).getElementsByClass("f2").get(1).html();
        arrGiaiTu[2] = esResult.get(5).getElementsByClass("f2").get(2).html();
        arrGiaiTu[3] = esResult.get(5).getElementsByClass("f2").get(3).html();

        //Giai nam
        String[] arrGiaiNam = new String[6];
        arrGiaiNam[0] = esResult.get(6).getElementsByClass("f2").get(0).html();
        arrGiaiNam[1] = esResult.get(6).getElementsByClass("f2").get(1).html();
        arrGiaiNam[2] = esResult.get(6).getElementsByClass("f2").get(2).html();
        arrGiaiNam[3] = esResult.get(7).getElementsByClass("f2").get(0).html();
        arrGiaiNam[4] = esResult.get(7).getElementsByClass("f2").get(1).html();
        arrGiaiNam[5] = esResult.get(7).getElementsByClass("f2").get(2).html();

        //Giai sau
        String[] arrGiaiSau = new String[3];
        arrGiaiSau[0] = esResult.get(8).getElementsByClass("f2").get(0).html();
        arrGiaiSau[1] = esResult.get(8).getElementsByClass("f2").get(1).html();
        arrGiaiSau[2] = esResult.get(8).getElementsByClass("f2").get(2).html();

        //Giai bay
        String[] arrGiaiBay = new String[4];
        arrGiaiBay[0] = esResult.get(9).getElementsByClass("f2").get(0).html();
        arrGiaiBay[1] = esResult.get(9).getElementsByClass("f2").get(1).html();
        arrGiaiBay[2] = esResult.get(9).getElementsByClass("f2").get(2).html();
        arrGiaiBay[3] = esResult.get(9).getElementsByClass("f2").get(3).html();

        Result result = new Result();
        result.setGiaiDB(giaiDB);
        result.setGiaiNhat(giaiNhat);
        result.setArrGiaiNhi(arrGiaiNhi);
        result.setArrGiaiBa(arrGiaiBa);
        result.setArrGiaiTu(arrGiaiTu);
        result.setArrGiaiNam(arrGiaiNam);
        result.setArrGiaiSau(arrGiaiSau);
        result.setArrGiaiBay(arrGiaiBay);
        result.setHaveFullResult();
        result.setDateMonth(strDate);
        return result;
    }
}
