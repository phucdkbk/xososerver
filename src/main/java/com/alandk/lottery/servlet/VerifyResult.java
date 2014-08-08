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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author phucdk
 */
public class VerifyResult extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(StaticCrawler.class);

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
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
                request.setAttribute("fromDate", fromDate);
                request.setAttribute("toDate", toDate);
                Connection conn = DatabaseUtils.getConnection();
                ps = conn.prepareStatement("select * from xosomienbac.lottery a where a.date >=? and a.date<=?");
                int fromDateInt = LotteryUtils.getDateIntFromString(fromDate);
                int toDateInt = LotteryUtils.getDateIntFromString(toDate);
                ps.setInt(1, fromDateInt);
                ps.setInt(2, toDateInt);
                rs = ps.executeQuery();
                String result;
                int date;
                
                List<String> listErrorDate = new ArrayList<String>();
                while (rs.next()) {
                    result = rs.getString("result");
                    date = rs.getInt("date");
                    Gson gson = new Gson();
                    Result resultObject = gson.fromJson(result, Result.class);
                    if (!resultObject.isHasFullValue()) {
                        listErrorDate.add(String.valueOf(date));
                    }
                }
                Gson gson = new Gson();
                request.setAttribute("errorDate", gson.toJson(listErrorDate));
                ps.close();
                conn.close();
            }
            RequestDispatcher dis = getServletContext().getRequestDispatcher("/verifyResult.jsp");
            dis.forward(request, response);
        } catch (IOException ex) {
            logger.error("", ex);
        } catch (ClassNotFoundException ex) {
            logger.error("", ex);
        } catch (SQLException ex) {
            logger.error("", ex);
        } catch (URISyntaxException ex) {
            logger.error("", ex);
        }
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
