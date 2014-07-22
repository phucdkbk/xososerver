/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alandk.lottery.servlet;

import com.alandk.lottery.report.object.CountLoGan;
import com.alandk.lottery.util.Constants;
import com.alandk.lottery.util.DatabaseUtils;
import com.alandk.lottery.util.DateTimeUtils;
import com.alandk.lottery.util.Result;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
public class ThongkeLogan extends HttpServlet {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ThongkeLogan.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws java.net.URISyntaxException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, URISyntaxException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            PrintWriter out = response.getWriter();
            List<CountLoGan> listCountLoGan = new ArrayList<CountLoGan>();
            initListLoganValue(listCountLoGan);
            Date startDate = getStartDateToThongkeLogan();
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
                if (!isAllExistResult(listCountLoGan)) {
                    Result resultObject = gson.fromJson(result, Result.class);
                    updateLoganCount(resultObject, listCountLoGan);
                }
            }
            Collections.sort(listCountLoGan);
            out.print(gson.toJson(listCountLoGan));
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

    private void initListLoganValue(List<CountLoGan> listCountLoGan) {
        for (int i = 0; i < 100; i++) {
            if (i < 10) {
                listCountLoGan.add(new CountLoGan("0" + i, Constants.LO_GAN.MIN_DATE_LO_GAN, false));
            } else {
                listCountLoGan.add(new CountLoGan(String.valueOf(i), Constants.LO_GAN.MIN_DATE_LO_GAN, false));
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
        } catch (SQLException ex) {
            Logger.getLogger(ThongkeLogan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThongkeLogan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ThongkeLogan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ThongkeLogan.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(ThongkeLogan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThongkeLogan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ThongkeLogan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ThongkeLogan.class.getName()).log(Level.SEVERE, null, ex);
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

    private Date getStartDateToThongkeLogan() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -Constants.LO_GAN.DATE_TO_GET_LO_GAN);
        return cal.getTime();
    }

    private void updateLoganCount(Result resultObject, List<CountLoGan> listCountLoGan) {
        List<String> listResult = new ArrayList<String>();
        if (resultObject.isHasFullValue()) {
            listResult.add(resultObject.getGiaiDB().substring(resultObject.getGiaiDB().length() - 2, resultObject.getGiaiDB().length()));
            listResult.add(resultObject.getGiaiNhat().substring(resultObject.getGiaiNhat().length() - 2, resultObject.getGiaiNhat().length()));
            listResult.addAll(getListResultByArrResult(resultObject.getArrGiaiNhi()));
        }
        for (CountLoGan countLoGan : listCountLoGan) {
            if (!countLoGan.isExisted()) {
                if (listResult.contains(countLoGan.getResult())) {
                    countLoGan.setExisted(true);
                } else {
                    countLoGan.setCount(countLoGan.getCount() + 1);
                }
            }
        }
    }

    private Collection<? extends String> getListResultByArrResult(String[] arrResult) {
        List<String> listResult = new ArrayList<String>();
        for (String result : arrResult) {
            listResult.add(result.substring(result.length() - 2, result.length()));
        }
        return listResult;
    }

    private boolean isAllExistResult(List<CountLoGan> listCountLoGan) {
        boolean isAllExistResult = true;
        for (CountLoGan countLogan : listCountLoGan) {
            if (!countLogan.isExisted()) {
                isAllExistResult = false;
                break;
            }
        }
        return isAllExistResult;
    }

}
