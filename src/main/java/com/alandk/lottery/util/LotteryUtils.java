/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alandk.lottery.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author phucdk
 */
public class LotteryUtils {

    public static int getDateIntFromString(String strDate) {
        String[] arrDate = strDate.split("/");
        return Integer.parseInt(arrDate[2] + arrDate[1] + arrDate[0]);
    }

    public static List<String> getListResultLoto(Result resultObject) {
        List<String> listResult = new ArrayList<String>();
        if (resultObject != null && resultObject.isHasFullValue()) {
            listResult.add(resultObject.getGiaiDB().trim().substring(resultObject.getGiaiDB().trim().length() - 2, resultObject.getGiaiDB().trim().length()));
            listResult.add(resultObject.getGiaiNhat().trim().substring(resultObject.getGiaiNhat().trim().length() - 2, resultObject.getGiaiNhat().trim().length()));
            listResult.addAll(getListResultByArrResult(resultObject.getArrGiaiNhi()));
            listResult.addAll(getListResultByArrResult(resultObject.getArrGiaiBa()));
            listResult.addAll(getListResultByArrResult(resultObject.getArrGiaiTu()));
            listResult.addAll(getListResultByArrResult(resultObject.getArrGiaiNam()));
            listResult.addAll(getListResultByArrResult(resultObject.getArrGiaiSau()));
            listResult.addAll(getListResultByArrResult(resultObject.getArrGiaiBay()));
        }
        return listResult;
    }

    public static String getKetquaDe(Result resultObject) {
        String de = "";
        if (resultObject != null && resultObject.isHasFullValue()) {
            de = resultObject.getGiaiDB().substring(resultObject.getGiaiDB().length() - 2, resultObject.getGiaiDB().length());
        }
        return de;
    }

    private static Collection<? extends String> getListResultByArrResult(String[] arrResult) {
        List<String> listResult = new ArrayList<String>();
        for (String result : arrResult) {
            listResult.add(result.trim().substring(result.trim().length() - 2, result.trim().length()));
        }
        return listResult;
    }
}
