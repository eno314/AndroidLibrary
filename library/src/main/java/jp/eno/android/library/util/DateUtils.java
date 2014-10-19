package jp.eno.android.library.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * @return dateオブジェクト
     * @throws ParseException
     */
    public static Date stringToDate(String dateString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }

    /**
     * Dateオブジェクトから日付文字列に変換
     *
     * @param date   文字列にしたいdateオブジェクト
     * @param format 返り値のフォーマット
     * @return 日付文字列
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
