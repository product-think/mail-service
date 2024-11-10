package pthink.mailservice.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SU {
    public static boolean empty(String value) {
        if (null == value || 0 == value.length()) {
            return true;
        }
        return false;
    }
    public static boolean notEmpty(String value) {
        return !empty(value);
    }

    public static String randomNumber(int digit) {
        double d = 10;
        double sum = Math.pow(10, digit);
        int num = new Random().nextInt((int)sum-1);
        return String.format(Integer.toString(num), digit);
    }

    public static String randomNumberAndString(int digit, boolean small) {
        String theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        if (small) {
            theAlphaNumericS += "abcdefghijklmnopqrstuvwxyz";
        }

        StringBuilder builder = new StringBuilder(digit);

        for (int m = 0; m < digit; m++) {

            // generate numeric
            int myindex
                    = (int)(theAlphaNumericS.length()
                    * Math.random());

            // add the characters
            builder.append(theAlphaNumericS
                    .charAt(myindex));
        }

        return builder.toString();
    }

    public static String format3keta(Integer i) {
        if (null == i) {
            return null;
        }
        return String.format("%,d", i);
    }
    public static String format3keta(Double d) {
        if (null == d) {
            return null;
        }

        String doubleAsString = String.valueOf(d);
        int indexOfDecimal = doubleAsString.indexOf(".");

        String integerPart = doubleAsString.substring(0, indexOfDecimal);
        String decimalPart = doubleAsString.substring(indexOfDecimal);
        if (".0".equals(decimalPart)) {
            decimalPart = "";
        }

        return SU.format3keta(Integer.valueOf(integerPart)) + decimalPart;
    }

    public static String cut(String str, int i) {
        if (SU.empty(str)) {
            return null;
        }
        if (30 >= str.length()) {
            return str;
        }
        return str.substring(0, 29) + "…";
    }
    public static String trimDoubleQuot(String str) {
        if (SU.empty(str)) {
            return null;
        }
        char c = '"';
        if(str.charAt(0) == c && str.charAt(str.length()-1) == c) {
            return str.substring(1, str.length()-1);
        }else {
            return str;
        }
    }
    public static String yyyyMMddSlash(Date date) {
        if (null == date) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.format(date);
    }
    public static String yyyyMMddSlashHHmmColon(Date date) {
        if (null == date) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return df.format(date);
    }
    public static String yyyyMMddSlashHHmmssColon(Date date) {
        if (null == date) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return df.format(date);
    }
    public static String yyyyMMddTHHmmHyphen(Date date) {
        if (null == date) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return df.format(date);
    }
    public static String HHmmColon(Date date) {
        if (null == date) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(date);
    }

}
