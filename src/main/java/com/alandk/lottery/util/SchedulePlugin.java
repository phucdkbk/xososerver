package com.alandk.lottery.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Web application lifecycle listener.
 *
 * @author phucdk
 */
public class SchedulePlugin implements ServletContextListener {

    public static final int PERIOD = 1 * 40 * 1000;
    private Timer timer;
    private Timer staticCrawlerTimer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 30);
//        staticCrawlerTimer = new Timer();
//        staticCrawlerTimer.schedule(new StaticCrawler(), cal.getTime());
        
//        cal.add(Calendar.DATE, 1);
//        cal.set(Calendar.HOUR_OF_DAY, 11);
//        cal.set(Calendar.MINUTE, 15);
        
        cal.add(Calendar.MINUTE, 3);
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new RealtimeCrawler(), cal.getTime(), PERIOD);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        if (timer != null) {
            timer.cancel();
        }
    }
}
