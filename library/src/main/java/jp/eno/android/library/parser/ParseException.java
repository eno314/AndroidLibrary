package jp.eno.android.library.parser;

/**
 * パーサー用の例外クラス
 * Created by eno314 on 2014/10/18.
 */
public class ParseException extends Exception {

    public ParseException(Throwable throwable) {
        super(throwable);
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
