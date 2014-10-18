package jp.eno.android.library.parser;

import jp.eno.android.library.model.RssFeed;

/**
 * Created by eno314 on 2014/10/18.
 */
public class GoogleFeedLoadParser extends Parser<RssFeed> {

    public GoogleFeedLoadParser() {

    }

    RssFeed parseInternal(byte[] body) {
        if (body == null || body.length == 0) {

        }

        return null;
    }

    @Override
    boolean isValid(RssFeed result) {
        return false;
    }
}
