package com.projectles.povmt.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nicolas on 26/07/2016.
 */
public class Util {

    public static String getStringofDateHoraDiaAno(Date date) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm dd/MM/yyyy");//dd/MM/yyyy
        String strDate = sdfDate.format(date);
        return strDate;
    }

    public static  String getStringofDateDiaAno(Date date){
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");//dd/MM/yyyy
        String strDate = sdfDate.format(date);
        return strDate;
    }

    public static String getStringofDate(Date date) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        String strDate = sdfDate.format(date);
        return strDate;
    }


    public static Date getDateofString(String date) throws ParseException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date parsedDate = sdfDate.parse(date);
        return parsedDate;
    }
}
