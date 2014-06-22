/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alandk.lottery.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author phucdk
 */
public class DatabaseUtils {

    public static Connection getConnection() throws ClassNotFoundException, SQLException, URISyntaxException {
        Class.forName("org.postgresql.Driver");

        // --------- Local database -------------
//            Connection connection = (Connection) DriverManager.getConnection(
//                    "jdbc:postgresql://localhost:5432/xosomienbac", "postgres",
//                    "23121988");
        // --------- Heroku databae -------------: connect from remot client
        Connection connection = (Connection) DriverManager.getConnection(
                "jdbc:postgresql://ec2-54-197-250-40.compute-1.amazonaws.com:5432/d1nufkakl1inrp?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", "fasmjmpjtjdkos",
                "V8MZTT7bm9yRbY67Q8Voc0S-v7");

        //---------- heroku databse -------------: connect from heroku server
//        URI dbUri = new URI(System.getenv("DATABASE_URL"));
//        String username = dbUri.getUserInfo().split(":")[0];
//        String password = dbUri.getUserInfo().split(":")[1];
//        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
//
//        Connection connection = DriverManager.getConnection(dbUrl, username, password);

        return connection;
    }
}
