package jp.eno.android.library.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日付操作用のユーティリティクラス
 * Created by eno314 on 2014/10/19.
 */
public class DateUtils {

    /**
     * 日付文字列からDateオブジェクトに変換
     *
     * @param dateString 日付文字列
     * @param format     dateStringのフォーマット
     * @param locale     時間の基準の場所
     * @return dateオブジェクト
     * @throws ParseException
     */
    public static Date stringToDate(String dateString, String format, Locale locale)
            throws ParseException {
        final SimpleDateFormat dateFormat;

        if (locale == null) {
            dateFormat = new SimpleDateFormat(format);
        } else {
            dateFormat = new SimpleDateFormat(format, locale);
        }

        return dateFormat.parse(dateString);
    }

    /**
     * 日付文字列からDateオブジェクトに変換
     *
     * @param dateString 日付文字列
     * @param format     dateStringのフォーマット
     * @return dateオブジェクト
     * @throws ParseException
     */
    public static Date stringToDate(String dateString, String format) throws ParseException {
        return stringToDate(dateString, format, null);
    }

    /**
     * Dateオブジェクトから日付文字列に変換
     *
     * @param date   文字列にしたいdateオブジェクト
     * @param format 返り値のフォーマット
     * @param locale 時間の基準の場所
     * @return 日付文字列
     */
    public static String dateToString(Date date, String format, Locale locale) {
        final SimpleDateFormat dateFormat;

        if (locale == null) {
            dateFormat = new SimpleDateFormat(format);
        } else {
            dateFormat = new SimpleDateFormat(format, locale);
        }

        return dateFormat.format(date);
    }

    /**
     * Dateオブジェクトから日付文字列に変換
     *
     * @param date   文字列にしたいdateオブジェクト
     * @param format 返り値のフォーマット
     * @return 日付文字列
     */
    public static String dateToString(Date date, String format) {
        return dateToString(date, format, null);
    }
}
