/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alandk.lottery.servlet;

import com.alandk.lottery.report.object.CountItNhieu;
import com.alandk.lottery.util.Constants;
import com.alandk.lottery.util.DatabaseUtils;
import com.alandk.lottery.util.DateTimeUtils;
import com.alandk.lottery.util.LotteryUtils;
import com.alandk.lottery.util.Result;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ThongkeItNhieu extends HttpServlet {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ThongkeItNhieu.class);

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            PrintWriter out = response.getWriter();
            Map<String, CountItNhieu> mapCountItNhieuLo = new HashMap<String, CountItNhieu>();
            Map<String, CountItNhieu> mapCountItNhieuDe = new HashMap<String, CountItNhieu>();
            initListCountItNhieuValue(mapCountItNhieuLo, mapCountItNhieuDe);
            int songay = Integer.valueOf(request.getParameter("songay")).intValue();
            Date startDate = getStartDateToThongkeItNhieu(songay);
            //int soluong = Integer.parseInt(request.getParameter("soluong"));
            int startDateInt = Integer.valueOf(DateTimeUtils.convertDateToString(startDate, "yyyyMMdd")).intValue();
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement("select * from xosomienbac.lottery a where a.date > ? order by a.date desc");
            ps.setInt(1, startDateInt);
            rs = ps.executeQuery();
            String result;
            Gson gson = new Gson();
            while (rs.next()) {
                result = rs.getString("result");
                Result resultObject = gson.fromJson(result, Result.class);
                updateCountItNhieu(resultObject, mapCountItNhieuLo, mapCountItNhieuDe);
            }
            List<CountItNhieu> listCountItNhieuLo = new ArrayList<CountItNhieu>(mapCountItNhieuLo.values());
            List<CountItNhieu> listCountItNhieuDe = new ArrayList<CountItNhieu>(mapCountItNhieuDe.values());

            Collections.sort(listCountItNhieuLo);
            Collections.sort(listCountItNhieuDe);
            listCountItNhieuLo.addAll(listCountItNhieuDe);
            out.print(gson.toJson(listCountItNhieuLo));
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            logger.error("", ex);
            throw ex;
        } catch (ClassNotFoundException ex) {
            logger.error("", ex);
            throw ex;
        } catch (Exception ex) {
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

    private void initListCountItNhieuValue(Map<String, CountItNhieu> mapCountItNhieuLo, Map<String, CountItNhieu> mapCountItNhieuDe) {
        for (int i = 0; i < 100; i++) {
            if (i < 10) {
                mapCountItNhieuLo.put("0" + i, new CountItNhieu("0" + i, Constants.IT_NHIEU.MIN_IT_NHIEU, Constants.IT_NHIEU.TYPE.LO));
            } else {
                mapCountItNhieuLo.put(String.valueOf(i), new CountItNhieu(String.valueOf(i), Constants.IT_NHIEU.MIN_IT_NHIEU, Constants.IT_NHIEU.TYPE.LO));
            }
        }

        for (int i = 0; i < 100; i++) {
            if (i < 10) {
                mapCountItNhieuDe.put("0" + i, new CountItNhieu("0" + i, Constants.IT_NHIEU.MIN_IT_NHIEU, Constants.IT_NHIEU.TYPE.DE));
            } else {
                mapCountItNhieuDe.put(String.valueOf(i), new CountItNhieu(String.valueOf(i), Constants.IT_NHIEU.MIN_IT_NHIEU, Constants.IT_NHIEU.TYPE.DE));
            }
        }
    }

    private Date getStartDateToThongkeItNhieu(int songay) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -songay);
        return cal.getTime();
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
        } catch (Exception ex) {
            Logger.getLogger(ThongkeItNhieu.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(ThongkeItNhieu.class.getName()).log(Level.SEVERE, null, ex);
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

    private void updateCountItNhieu(Result resultObject, Map<String, CountItNhieu> mapCountItNhieuLo, Map<String, CountItNhieu> mapCountItNhieuDe) {
        updateCountItNhieuDe(resultObject, mapCountItNhieuDe);
        updateCountItNhieuLoto(resultObject, mapCountItNhieuLo);
    }

    private void updateCountItNhieuLoto(Result resultObject, Map<String, CountItNhieu> mapCountItNhieuLo) {
        List<String> listKetquaLo = LotteryUtils.getListResultLoto(resultObject);
        for (int i = 0; i < listKetquaLo.size(); i++) {
            String loto = listKetquaLo.get(i);
            CountItNhieu countItNhieu = mapCountItNhieuLo.get(loto);
            if (countItNhieu != null) {
                countItNhieu.setCount(countItNhieu.getCount() + 1);
            }
        }
    }

    private void updateCountItNhieuDe(Result resultObject, Map<String, CountItNhieu> mapCountItNhieuDe) {
        String de = LotteryUtils.getKetquaDe(resultObject);
        CountItNhieu countItNhieu = mapCountItNhieuDe.get(de);
        if (countItNhieu != null) {
            countItNhieu.setCount(countItNhieu.getCount() + 1);
        }
    }

}
