package com.alandk.lottery.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.alandk.lottery.util.DatabaseUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phucdk
 */
public class LotteryResult extends HttpServlet {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LotteryResult.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.net.URISyntaxException
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, URISyntaxException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            PrintWriter out = response.getWriter();
            int date = Integer.parseInt(request.getParameter("date"));

            conn = DatabaseUtils.getConnection();

            ps = conn.prepareStatement("select * from xosomienbac.lottery a where a.date =?");
            ps.setInt(1, date);
            rs = ps.executeQuery();

            String result = "";
            while (rs.next()) {
                result = rs.getString("result");
            }

            out.print(result);
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            logger.error("", ex);
            throw ex;
        } catch (ClassNotFoundException ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */

                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* ignored */

                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* ignored */

                }
            }
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
        try {
            processRequest(request, response);
        } catch (URISyntaxException ex) {
            Logger.getLogger(LotteryResult.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().print(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(LotteryResult.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().print(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LotteryResult.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().print(ex.getMessage());
        }
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
        try {
            processRequest(request, response);

        } catch (URISyntaxException ex) {
            Logger.getLogger(LotteryResult.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().print(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(LotteryResult.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().print(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LotteryResult.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().print(ex.getMessage());
        }
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
