package com.example.orvilleclarke.testfrag.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Orville Clarke on 7/2/2016.
 */
public class Helpers {
   public static String getDateTime(){
       SimpleDateFormat dateFormat = new SimpleDateFormat(
               "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
       Date date = new Date();
       return dateFormat.format(date);

   }


}
