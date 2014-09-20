package jp.eno.android.library.model;

/**
 * リクエストで使うレスポンスのモデル
 * Created by eno314 on 2014/09/20.
 */
public interface RequestResponseModel {

    /**
     * 有効なモデルかどうか
     * @return true : 有効 / false : 無効
     */
    public boolean isValid();
}
