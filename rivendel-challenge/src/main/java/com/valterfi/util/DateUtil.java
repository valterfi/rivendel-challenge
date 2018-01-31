package com.valterfi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    
    private DateUtil() {
        
    }
    
    public static String convertDateToFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
        
    }

}
