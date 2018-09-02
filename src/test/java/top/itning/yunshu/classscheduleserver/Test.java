package top.itning.yunshu.classscheduleserver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Test {
    @org.junit.Test
    public void test() throws ParseException {
        SimpleDateFormat DF = new SimpleDateFormat("HH:mm", Locale.CHINESE);
        String end = "16:10";
        long time = DF.parse(end).getTime();
        long time1 = DF.parse(DF.format(new Date())).getTime();
        System.out.println(time);
        System.out.println(time1);
    }

}
