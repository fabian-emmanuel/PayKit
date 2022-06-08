package com.codewithfibbee.paykit.utils;


import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CommonUtils {


    public static boolean fieldChanged(String val1, String val2) {
        return !val1.equals(val2);
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    public static boolean checkDuplicateList(Collection inputList) {

        Set inputSet = new HashSet(inputList);

        return inputSet.size() < inputList.size();
    }

    public static boolean isLong(String str) {
        try {
            Long.parseLong(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static int round(double d) {
        double dAbs = Math.abs(d);
        int i = (int) dAbs;
        double result = dAbs - i;
        if (result < 0.5) {
            return d < 0 ? -i : i;
        } else {
            return d < 0 ? -(i + 1) : i + 1;
        }
    }

    public static double roundWithPrecision(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }


    public static int getRandomInt(int low,int high){
        Random r=new Random();
        return r.nextInt((high)-low) + low;
    }
}



