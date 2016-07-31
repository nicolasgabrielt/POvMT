package com.projectles.povmt.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String getStringOfDateHoraDiaAno(Date date) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String strDate = sdfDate.format(date);
        return strDate;
    }

    public static  String getStringOfDateDiaAno(Date date){
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdfDate.format(date);
        return strDate;
    }

    public static String getStringOfDate(Date date) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdfDate.format(date);
        return strDate;
    }

    public static Date getDateOfString(String date) throws ParseException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = sdfDate.parse(date);
        return parsedDate;
    }
}
