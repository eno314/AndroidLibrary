package jp.eno.android.library.model;

import android.text.TextUtils;

import java.util.Date;

/**
 * Created by eno314 on 2014/09/20.
 */
public class RssEntry implements RequestResponseModel {

    /**
     * 記事のタイトル
     */
    public String title;

    /**
     * 記事のリンク
     */
    public String link;

    /**
     * 配信日時
     */
    public Date publishedDate;

    /**
     * 記事のスピニペット（断片）
     */
    public String contentSnippet;

    /**
     * 記事の内容
     */
    public String content;

    @Override
    public boolean isValid() {

        if (TextUtils.isEmpty(title)) {
            return false;
        }

        if (TextUtils.isEmpty(link)) {
            return false;
        }

        if (publishedDate == null) {
            return false;
        }

        if (TextUtils.isEmpty(content)) {
            return false;
        }

        return true;
    }
}
