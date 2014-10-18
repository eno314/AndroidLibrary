package jp.eno.android.library.parser;

/**
 * Created by eno314 on 2014/10/18.
 */
abstract class Parser<T> {

    /**
     * パースの実行
     * @param body APIのレスポンスボディ
     * @return パース結果
     * @throws ParseException
     */
    public T parse(byte[] body) throws ParseException {
        final T result = parseInternal(body);

        if (!isValid(result)) {
            throw new ParseException("Invalid result");
        }

        return result;
    }

    /**
     * パース処理のロジックをここに書く
     * @param body APIのレスポンスボディ
     * @return パース結果
     * @throws ParseException
     */
    abstract T parseInternal(byte[] body) throws ParseException;

    /**
     * パース結果の整合性チェック
     * @param result パース結果
     * @return true/false
     */
    abstract boolean isValid(T result);
}
