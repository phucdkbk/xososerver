/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alandk.lottery.servlet;

import com.alandk.lottery.util.DatabaseUtils;
import com.alandk.lottery.util.LotteryUtils;
import com.alandk.lottery.util.Result;
import com.alandk.lottery.util.StaticCrawler;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phucdk
 */
public class UpdateResult extends HttpServlet {
    

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(StaticCrawler.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PreparedStatement ps;
        ResultSet rs;
        try {
            /* TODO output your page here. You may use following sample code. */
            String type = request.getParameter("type");
            if (type != null && "show".equals(type)) {
                String date = request.getParameter("date");
                if (date != null && !date.isEmpty()) {
                    int dateInt = LotteryUtils.getDateIntFromString(date);
                    Connection conn = DatabaseUtils.getConnection();
                    ps = conn.prepareStatement("select * from xosomienbac.lottery a where a.date =?");
                    ps.setInt(1, dateInt);
                    rs = ps.executeQuery();
                    String result;
                    while (rs.next()) {
                        result = rs.getString("result");
                        Gson gson = new Gson();
                        Result resultObject = gson.fromJson(result, Result.class);
                        request.setAttribute("resultObject", resultObject);
                    }
                    request.setAttribute("date", date);
                    ps.close();
                    conn.close();
                }
            } else if (type != null && "update".equals(type)) {
                String date = request.getParameter("currentDate");
                String meessage;
                if (date != null && !date.isEmpty()) {
                    int dateInt = LotteryUtils.getDateIntFromString(date);
                    Gson gson = new Gson();
                    Result result = getResultObjectFromRequest(request);

                    Connection conn = DatabaseUtils.getConnection();
                    ps = conn.prepareStatement("select * from xosomienbac.lottery a where a.date =?");
                    ps.setInt(1, dateInt);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        String sqlQuery = "update xosomienbac.lottery  set result = ? where date = ?";
                        PreparedStatement prepareStatement = conn
                                .prepareStatement(sqlQuery);
                        prepareStatement.setString(1, gson.toJson(result));
                        prepareStatement.setInt(2, dateInt);
                        prepareStatement.execute();
                        meessage = "Cập nhật kết quả thành công";
                    } else {
                        String sqlQuery = "insert into xosomienbac.lottery values(?,?)";
                        PreparedStatement prepareStatement = conn
                                .prepareStatement(sqlQuery);
                        prepareStatement.setInt(1, dateInt);
                        prepareStatement.setString(2, gson.toJson(result));
                        prepareStatement.execute();
                        meessage = "Thêm mới kết quả thành công";
                    }
                    request.setAttribute("date", date);
                    request.setAttribute("meessage", meessage);
                    request.setAttribute("resultObject", result);
                    ps.close();
                    conn.close();
                }
            }
            RequestDispatcher dis = getServletContext().getRequestDispatcher("/updateResult.jsp");
            dis.forward(request, response);
        } catch (ClassNotFoundException ex) {
            logger.error("", ex);
        } catch (SQLException ex) {
            logger.error("", ex);
        } catch (URISyntaxException ex) {
            logger.error("", ex);
        }
    }

    private Result getResultObjectFromRequest(HttpServletRequest request) {
        Result result = new Result();
        String giaiDB = request.getParameter("giaiDB");
        String giaiNhat = request.getParameter("giaiNhat");
        String giaiNhi = request.getParameter("giaiNhi");
        String giaiBa = request.getParameter("giaiBa");
        String giaiTu = request.getParameter("giaiTu");
        String giaiNam = request.getParameter("giaiNam");
        String giaiSau = request.getParameter("giaiSau");
        String giaiBay = request.getParameter("giaiBay");
        result.setGiaiDB(giaiDB);
        result.setGiaiNhat(giaiNhat);
        result.setArrGiaiNhi(giaiNhi.split("-"));
        result.setArrGiaiBa(giaiBa.split("-"));
        result.setArrGiaiTu(giaiTu.split("-"));
        result.setArrGiaiNam(giaiNam.split("-"));
        result.setArrGiaiSau(giaiSau.split("-"));
        result.setArrGiaiBay(giaiBay.split("-"));
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
