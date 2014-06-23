/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alandk.lottery.util;

/**
 *
 * @author phucdk
 */
public class LotteryUtils {

    public static int getDateIntFromString(String strDate) {
        String[] arrDate = strDate.split("/");
        return Integer.parseInt(arrDate[2] + arrDate[1] + arrDate[0]);
    }
}
