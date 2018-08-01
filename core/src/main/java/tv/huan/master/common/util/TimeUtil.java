package tv.huan.master.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间的工具类
 *
 */
public class TimeUtil {
    private static final Calendar cal = Calendar.getInstance();

    /**
     * 当前时间
     *
     * @return
     */
    public static Date currentTime() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 时间转换字符串:yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String getYYYYMMDD(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        return simpledateformat.format(date);
    }
   /**
     * 时间转换字符串:yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String getYYYYMM(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
        return simpledateformat.format(date);
    }

    /**
     * 时间转换字符串:format
     *
     * @param date
     * @param format
     * @return
     */
    public static String getCustom(Date date, String format) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        return simpledateformat.format(date);
    }

    /**
     * 时间转换字符串:yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String getYYYYMMDDHHMMSS(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        return simpledateformat.format(date);
    }

    /**
     * 时间转换字符串:yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getYYYYMMDDHHMMSSLiuPC(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return simpledateformat.format(date);
    }

    /**
     * 时间转换字符串:yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getYYYY_MM_DD(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        return simpledateformat.format(date);
    }

    /**
     * 时间转换字符串:yyyy-MM
     *
     * @param date
     * @return
     */
    public static String getYYYY_MM(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM");
        return simpledateformat.format(date);
    }

    /**
     * 取时间年
     *
     * @param date
     * @return
     */
    public static String getYYYY(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
        return simpledateformat.format(date);
    }

    /**
     * 取时间月
     *
     * @param date
     * @return
     */
    public static String getMM(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("MM");
        return simpledateformat.format(date);
    }

    /**
     * 取时间日
     *
     * @param date
     * @return
     */
    public static String getDD(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd");
        return simpledateformat.format(date);
    }

    /**
     * 字符串转时间:YYYY-MM-DD
     *
     * @param dateString
     * @return
     */
    public static Date controlTime(String dateString) {
        String s1 = dateString.substring(0, 4);
        String s2;
        String s3;
        String s4 = dateString.substring(5);
        int i = s4.indexOf("-");
        if (i == 1) {
            s2 = s4.substring(0, 1);
            s3 = s4.substring(2);
        } else {
            s2 = s4.substring(0, 2);
            s3 = s4.substring(3, 5);
        }
        cal.set(new Integer(s1), new Integer(s2) - 1,
                new Integer(s3));

        return cal.getTime();
    }

    /**
     * 字符串转时间:YYYY-MM
     *
     * @param dateString
     * @return
     */
   public static Date controlTime_(String dateString) {
        String s1 = dateString.substring(0, 4);
        String s2;
        String s4 = dateString.substring(5);
        int i = s4.indexOf("-");
        if (i == 1) {
            s2 = s4.substring(0, 1);
        } else {
            s2 = s4.substring(0, 2);
        }
        cal.set(new Integer(s1), new Integer(s2) - 1, 1);
        return cal.getTime();
    }


    /**
     * 字符串转时间:YYYY-MM-DD hh:mm:ss
     *
     * @param dateString
     * @return
     */
    public static Date controlTimeHHMMSS(String dateString) {
        String s1 = dateString.substring(0, 4);
        String s2;
        String s3;
        String s4 = dateString.substring(5);
        int i = s4.indexOf("-");
        if (i == 1) {
            s2 = s4.substring(0, 1);
            s3 = s4.substring(2);
        } else {
            s2 = s4.substring(0, 2);
            s3 = s4.substring(3, 5);
        }
        cal.set((new Integer(s1)), (new Integer(s2)) - 1,
                (new Integer(s3)), new Integer(dateString.substring(11, 13)),
                new Integer(dateString.substring(14, 16)),
                new Integer(dateString.substring(17, 19)));

        return cal.getTime();
    }

    /**
     * 字符串转时间:
     *
     * @param dateString
     * @return
     */
    public static Date controlTimeStartHHMMSS(String dateString) {
        String s1 = dateString.substring(0, 4);
        String s2;
        String s3;
        String s4 = dateString.substring(5);
        int i = s4.indexOf("-");
        if (i == 1) {
            s2 = s4.substring(0, 1);
            s3 = s4.substring(2);
        } else {
            s2 = s4.substring(0, 2);
            s3 = s4.substring(3, 5);
        }
        cal.set((new Integer(s1)), (new Integer(s2)) - 1,
                (new Integer(s3)), 0, 0, 0);

        return cal.getTime();
    }

    /**
     * 字符串转时间:
     *
     * @param dateString
     * @return
     */
    public static Date controlTimeEndHHMMSS(String dateString) {
        String s1 = dateString.substring(0, 4);
        String s2;
        String s3;
        String s4 = dateString.substring(5);
        int i = s4.indexOf("-");
        if (i == 1) {
            s2 = s4.substring(0, 1);
            s3 = s4.substring(2);
        } else {
            s2 = s4.substring(0, 2);
            s3 = s4.substring(3, 5);
        }
        cal.set((new Integer(s1)), (new Integer(s2)) - 1,
                (new Integer(s3)), 23, 59, 59);

        return cal.getTime();
    }


    /**
     * 取年份
     *
     * @param date
     * @return
     */
    public static String getYear(Date date) {
        cal.setTime(date);
        return String.valueOf(cal.get(1));
    }

    /**
     * 取年月
     *
     * @param date
     * @return
     */
    public static String getMonth(Date date) {
        String s = "";
        cal.setTime(date);
        if (cal.get(2) < 9)
            s = "0";
        return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(2) + 1);
    }

    /**
     * 取月份
     *
     * @param date
     * @return
     */
    public static String getMonthNumber(Date date) {
        String s = "";
        cal.setTime(date);
        if (cal.get(2) < 9)
            s = "0";
        return s + String.valueOf(cal.get(2) + 1);
    }

    /**
     * 取年月日
     *
     * @param date
     * @return
     */
    public static String getDay(Date date) {
        String s = "";
        String s1 = "";
        cal.setTime(date);
        if (cal.get(2) < 9)
            s = "0";
        if (cal.get(5) < 10)
            s1 = "0";
        return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(2) + 1)
                + s1 + String.valueOf(cal.get(5));
    }

    /**
     * 取现在时间完整字符串
     *
     * @return
     */
    public static String getNowFormatTimeString() {
        Date date = new Date();
        SimpleDateFormat simpledateformat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return simpledateformat.format(date);
    }

    /**
     * 转换完整的时间字符串
     *
     * @param date
     * @return
     */
    public static String getFormatTimeString(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return simpledateformat.format(date);
    }

    /**
     * 取给定时间的下一天
     *
     * @param date
     * @return
     */
    public static Date getAfterDay(Date date) {

        cal.setTime(date);
        cal.set(cal.get(1), cal.get(2), cal.get(5) + 1);
        return cal.getTime();
    }

    /**
     * 取给定时间的下几天
     * rocLiu
     *
     * @param date
     * @return
     */
    public static Date rocLiu_getcalDay(Date date, int num) {

        cal.setTime(date);
        cal.set(cal.get(1), cal.get(2), cal.get(5) + num);
        return cal.getTime();
    }

    /**
     * 取给定时间向前一个月
     *
     * @param date
     * @return
     */
    public static Date getBeforeMonth(Date date) {
        cal.setTime(date);
        cal.set(cal.get(1), cal.get(2) - 1, cal.get(5));
        return cal.getTime();
    }

    /**
     * 取给定时间向前一个月
     *
     * @param date 时间
     * @param num  负数 前num月  正数 后num月
     * @return Date
     */
    public static Date getBeforeMonth_(Date date, int num) {
        cal.setTime(date);
        cal.set(cal.get(1), cal.get(2) + num, cal.get(5));
        return cal.getTime();
    }

    /**
     * 取给定时间当月的第一天
     *
     * @param date
     * @return
     */
    public static Date getBeforeMonthDay(Date date) {
        cal.setTime(date);
        cal.set(cal.get(1), cal.get(2), 1);
        return cal.getTime();
    }

    /**
     * 取给定时间当月的第一天
     *
     * @param date
     * @return
     */
    public static Date getBeforeMonthYear(Date date) {
        cal.setTime(date);
        cal.set(cal.get(1) - 1, 0, 1);
        return cal.getTime();
    }

    /**
     * 某时间的num月后
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getcalMonth(Date date, int num) {

        cal.setTime(date);
        cal.set(cal.get(1), cal.get(2) + num, cal.get(5));
        return cal.getTime();
    }

    /**
     * 某时间的num年后
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getcalYear(Date date, int num) {

        cal.setTime(date);
        cal.set(cal.get(1) + num, cal.get(2), cal.get(5));
        return cal.getTime();
    }


    /**
     * 取给定时间向前一个年
     *
     * @param date
     * @return
     */
    public static Date getBeforeYear(Date date) {

        cal.setTime(date);
        cal.set(cal.get(1) - 1, cal.get(2), cal.get(5));
        return cal.getTime();
    }


    /**
     * 取给定时间的下一天
     *
     * @param date
     * @return
     */
    public static String getStringAfterDay(Date date) {

        cal.setTime(date);
        cal.set(cal.get(1), cal.get(2), cal.get(5) + 1);
        return getYYYY_MM_DD(cal.getTime());
    }


    /**
     * 取给定时间的前num天
     *
     * @param date
     * @return
     */
    public static String getStringBeforeDay(Date date, int num) {

        cal.setTime(date);
        cal.set(cal.get(1), cal.get(2), cal.get(5) + num);
        return getYYYY_MM_DD(cal.getTime());
    }

    /**
     * 月有几天
     *
     * @param year
     * @param mon
     * @return
     */
    public static int getDaysInMonth(int year, int mon) {
        GregorianCalendar date = new GregorianCalendar(year, mon - 1, 1);
        return (date.getActualMaximum(Calendar.DATE));
    }

    /**
     * 现在的是几月
     *
     * @param date
     * @return
     */
    public static int getNowTimeMonth(Date date) {
        cal.setTime(date);
        return cal.get(2) + 1;
    }


    /**
     * 字符串转时间:YYYY/M/D h:m:s
     *
     * @param dateString
     * @return
     */
    public static Date zhao0p_controlTime_M_D(String dateString) {
        String[] time, NYR, SFM;
        time = dateString.split(" ");
        NYR = time[0].split("/");
        SFM = time[1].split(":");

        String N, Y, R, S, F, M;
        N = NYR[0];
        if (NYR[1].length() == 1) Y = "0" + NYR[1];
        else Y = NYR[1];
        if (NYR[2].length() == 1) R = "0" + NYR[2];
        else R = NYR[2];
        if (SFM[0].length() == 1) S = "0" + SFM[0];
        else S = SFM[0];
        if (SFM[1].length() == 1) F = "0" + SFM[1];
        else F = SFM[1];
        if (SFM[2].length() == 1) M = "0" + SFM[2];
        else M = SFM[2];
        cal.set((new Integer(N)), (new Integer(Y)) - 1,
                (new Integer(R)), (new Integer(S)), (new Integer(F)), (new Integer(M)));

        return cal.getTime();
    }

    /**
     * 字符串转时间:YYYY-M-D h:m:s
     *
     * @param dateString
     * @return
     */
    public static Date zhao0p_controlTimeMD(String dateString) {
        String[] time, NYR, SFM;
        time = dateString.split(" ");
        NYR = time[0].split("-");
        SFM = time[1].split(":");

        String N, Y, R, S, F, M;
        N = NYR[0];
        if (NYR[1].length() == 1) Y = "0" + NYR[1];
        else Y = NYR[1];
        if (NYR[2].length() == 1) R = "0" + NYR[2];
        else R = NYR[2];
        if (SFM[0].length() == 1) S = "0" + SFM[0];
        else S = SFM[0];
        if (SFM[1].length() == 1) F = "0" + SFM[1];
        else F = SFM[1];
        if (SFM[2].length() == 1) M = "0" + SFM[2];
        else M = SFM[2];
        cal.set((new Integer(N)), (new Integer(Y)) - 1,
                (new Integer(R)), (new Integer(S)), (new Integer(F)), (new Integer(M)));

        return cal.getTime();
    }
     
    /**
     * 前一月最后一天
     *
     * @param date 日期
     * @return Date
     */
    public static Date cfMonthLastDay(Date date) {
        cal.setTime(date);
        cal.set(cal.get(1), cal.get(2) - 1, cal.get(5));
        cal.setTime(cal.getTime());
        cal.set(cal.get(1), cal.get(2), cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

}
