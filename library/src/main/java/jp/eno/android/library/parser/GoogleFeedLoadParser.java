package jp.eno.android.library.parser;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import jp.eno.android.library.model.RssEntry;
import jp.eno.android.library.model.RssFeed;
import jp.eno.android.library.util.DateUtils;

/**
 * GoogleFeedAPIのレスポンスパーサー
 * Created by eno314 on 2014/10/18.
 */
public class GoogleFeedLoadParser extends Parser<RssFeed> {

    private static final String NODE_RESPONSE_DATA = "responseData";
    private static final String NODE_FEED = "feed";
    private static final String NODE_FEED_URL = "feedUrl";
    private static final String NODE_TITLE = "title";
    private static final String NODE_LINK = "link";
    private static final String NODE_DESCRIPTION = "description";
    private static final String NODE_ENTRIES = "entries";
    private static final String NODE_PUBLISHED_DATE = "publishedDate";
    private static final String NODE_CONTENT_SNIPPET = "contentSnippet";
    private static final String NODE_CONTENT = "content";

    private static final String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";

    RssFeed parseInternal(byte[] body) throws ParseException {
        if (body == null || body.length == 0) {
            throw new ParseException("body is empty...");
        }

        final RssFeed rssFeed;

        try {
            rssFeed = parseRoot(new String(body, HTTP.UTF_8));
        } catch (JSONException e) {
            throw new ParseException(e);
        } catch (UnsupportedEncodingException e) {
            throw new ParseException(e);
        }

        return rssFeed;
    }

    @Override
    boolean isValid(RssFeed result) {

        if (result == null) {
            return false;
        }

        return result.isValid();
    }

    private RssFeed parseRoot(String body) throws JSONException {

        final JSONObject rootObject = new JSONObject(body);
        final JSONObject responseData = rootObject.getJSONObject(NODE_RESPONSE_DATA);
        final JSONObject feed = responseData.getJSONObject(NODE_FEED);

        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = feed.getString(NODE_FEED_URL);
        rssFeed.title = feed.getString(NODE_TITLE);
        rssFeed.link = feed.getString(NODE_LINK);
        rssFeed.description = feed.getString(NODE_DESCRIPTION);
        rssFeed.entryList = new ArrayList<RssEntry>();

        final JSONArray entries = feed.getJSONArray(NODE_ENTRIES);
        final int entriesLength = entries.length();

        for (int i = 0; i < entriesLength; i++) {
            rssFeed.entryList.add(parseEntry(entries.getJSONObject(i)));
        }

        return rssFeed;
    }

    private RssEntry parseEntry(JSONObject entry) throws JSONException {

        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = entry.getString(NODE_TITLE);
        rssEntry.link = entry.getString(NODE_LINK);
        rssEntry.contentSnippet = entry.getString(NODE_CONTENT_SNIPPET);
        rssEntry.content = entry.getString(NODE_CONTENT);

        final String dateString = entry.getString(NODE_PUBLISHED_DATE);
        try {
            rssEntry.publishedDate = DateUtils.stringToDate(dateString, DATE_FORMAT);
        } catch (java.text.ParseException e) {
            rssEntry.publishedDate = null;
        }

        return rssEntry;
    }
}
