package jp.eno.android.library.parser;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import jp.eno.android.library.model.RssFeed;

/**
 * GoogleFeedAPIのレスポンスパーサー
 * Created by eno314 on 2014/10/18.
 */
public class GoogleFeedLoadParser extends Parser<RssFeed> {

    RssFeed parseInternal(byte[] body) throws ParseException {
        if (body == null || body.length == 0) {
            throw new ParseException("body is empty...");
        }

        final RssFeed rssFeed;

        try {
            rssFeed = parseRoot(new String(body, HTTP.UTF_8));
        }catch (JSONException e) {
            throw new ParseException(e);
        } catch (UnsupportedEncodingException e) {
            throw new ParseException(e);
        }

        return rssFeed;
    }

    private RssFeed parseRoot(String body) throws JSONException {

        final RssFeed rssFeed = new RssFeed();

        JSONObject rootObject   = new JSONObject( body );
        JSONObject responseData = rootObject.getJSONObject( "responseData" );
        JSONObject feed         = responseData.getJSONObject( "feed" );

        return rssFeed;
    }

    @Override
    boolean isValid(RssFeed result) {
        return false;
    }
}
