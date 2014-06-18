package com.alandk.lottery.util;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class App {

    public static boolean fullInfo;
    public static final int PERIOD = 2 * 60 * 1000;

    public void main(String[] args) throws IOException, ScriptException {
        System.setProperty("http.proxyHost", "10.2.0.8");
        System.setProperty("http.proxyPort", "8080");
        Result result = RealtimeCrawler.getResultFromXSKTDotComDotvn();
        Result result2 = RealtimeCrawler.getResultFromKetquaDotnet();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new RealtimeCrawler(), new Date(), PERIOD);

        //Deserializing
//        Gson gson = new Gson();
//        Result result = gson.fromJson(jsonValue, Result.class);
//        System.out.println(result);
    }
}
