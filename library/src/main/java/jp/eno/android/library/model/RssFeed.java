package jp.eno.android.library.model;

import android.text.TextUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by eno314 on 2014/09/20.
 */
public class RssFeed implements RequestResponseModel {

    /**
     * rssのURL
     */
    public String feedUrl;

    /**
     * サイトのタイトル
     */
    public String title;

    /**
     * サイトのリンク
     */
    public String linkUrl;

    /**
     * サイト説明
     */
    public String description;

    /**
     * RSS内のエントリー一覧
     */
    public List<RssEntry> entryList;

    @Override
    public boolean isValid() {

        if ( TextUtils.isEmpty( feedUrl ) ) {
            return false;
        }

        if ( TextUtils.isEmpty( title ) ) {
            return false;
        }

        if ( TextUtils.isEmpty( linkUrl ) ) {
            return false;
        }

        if ( TextUtils.isEmpty( description ) ) {
            return false;
        }

        if ( !isValidEntryList( entryList ) ) {
            return false;
        }

        return true;
    }

    private boolean isValidEntryList(List<RssEntry> entryList) {

        if ( entryList == null ) {
            return false;
        }

        for ( RssEntry rssEntry : entryList ) {

            if ( !rssEntry.isValid() ) {
                return false;
            }
        }

        return true;
    }
}
