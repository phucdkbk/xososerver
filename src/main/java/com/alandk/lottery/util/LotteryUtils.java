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
        if (resultObject.isHasFullValue()) {
            listResult.add(resultObject.getGiaiDB().substring(resultObject.getGiaiDB().length() - 2, resultObject.getGiaiDB().length()));
            listResult.add(resultObject.getGiaiNhat().substring(resultObject.getGiaiNhat().length() - 2, resultObject.getGiaiNhat().length()));
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
        if (resultObject.isHasFullValue()) {
            de = resultObject.getGiaiDB().substring(resultObject.getGiaiDB().length() - 2, resultObject.getGiaiDB().length());
        }
        return de;
    }

    private static Collection<? extends String> getListResultByArrResult(String[] arrResult) {
        List<String> listResult = new ArrayList<String>();
        for (String result : arrResult) {
            listResult.add(result.substring(result.length() - 2, result.length()));
        }
        return listResult;
    }
}
