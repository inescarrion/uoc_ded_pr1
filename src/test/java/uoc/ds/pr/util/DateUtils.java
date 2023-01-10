package uoc.ds.pr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DateUtils {

    public static Date createDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date ret = null;
        try {
            ret = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace(); // TODO hacer algo aquí, pero no comerse la excepción
        }
        return ret;
    }
    
    public static LocalDate createLocalDate(String date) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	return LocalDate.parse(date, formatter);
    }
}
