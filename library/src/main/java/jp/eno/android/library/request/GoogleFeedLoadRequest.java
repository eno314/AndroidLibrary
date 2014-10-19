package jp.eno.android.library.request;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.apache.http.HttpStatus;

import java.io.UnsupportedEncodingException;

import jp.eno.android.library.model.RssFeed;
import jp.eno.android.library.volley.VolleyCache;

/**
 * GoogleFeedAPIのリクエストクラス
 * Created by eno314 on 2014/09/20.
 */
public class GoogleFeedLoadRequest extends Request<RssFeed> {

    private final Response.Listener<RssFeed> mListener;

    private final long mCacheDurationMs;

    public GoogleFeedLoadRequest(Builder builder) {

        super(Method.GET, buildRequestUrl(builder), builder.mErrorListener);

        mListener = builder.mListener;
        mCacheDurationMs = builder.mCacheDurationMs;
    }

    /**
     * リクエストURLの生成
     */
    private static String buildRequestUrl(Builder builder) {

        Uri.Builder uriBuilder = Uri.parse(Builder.API_URL).buildUpon();
        uriBuilder.appendQueryParameter(Builder.PARAM_QUERY, builder.mQuery);
        uriBuilder.appendQueryParameter(Builder.PARAM_VERSION, builder.mVersion);
        uriBuilder.appendQueryParameter(Builder.PARAM_HL, builder.mHl);
        uriBuilder.appendQueryParameter(Builder.PARAM_NUM, String.valueOf(builder.mNum));

        Log.d("AAAAA", uriBuilder.build().toString());

        return uriBuilder.build().toString();
    }

    @Override
    protected Response<RssFeed> parseNetworkResponse(NetworkResponse response) {

        if (response.statusCode != HttpStatus.SC_OK) {
            return Response.error(new VolleyError("Unexpected status code " + response.statusCode));
        }

        try {
            Log.d("BBBBBBB", new String(response.data, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final RssFeed feed = null;
        Cache.Entry cacheEntry = VolleyCache.createEntry(response, mCacheDurationMs, true);
        Response<RssFeed> parsed = Response.success(feed, cacheEntry);

        return parsed;
    }

    @Override
    protected void deliverResponse(RssFeed response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    /**
     * リクエスト生成用のクラス
     */
    public static class Builder {

        /**
         * リクエストAPIのURL
         */
        private static final String API_URL = "https://ajax.googleapis.com/ajax/services/feed/load";

        private static final String PARAM_QUERY = "q";
        private static final String PARAM_VERSION = "v";
        private static final String PARAM_HL = "hl";
        private static final String PARAM_NUM = "num";

        /**
         * rssのURL
         */
        private final String mQuery;

        /**
         * バージョン（デフォルト１件）
         */
        private String mVersion = "1.0";

        /**
         * 言語（デフォルト日本語）
         */
        private String mHl = "ja";

        /**
         * 記事取得件数（デフォルト10件）
         */
        private int mNum = 10;

        /**
         * 通信成功時のリスナー
         */
        private Response.Listener<RssFeed> mListener;

        /**
         * 通信エラー時のリスナー
         */
        private Response.ErrorListener mErrorListener;

        /**
         * キャッシュ時間（デフォルト１０分）
         */
        private long mCacheDurationMs = 10 * 60 * 1000;

        public Builder(String query) {
            mQuery = query;
        }

        public Builder setVersion(String version) {
            mVersion = version;
            return this;
        }

        public Builder setHl(String hl) {
            mHl = hl;
            return this;
        }

        public Builder setNum(int num) {
            mNum = num;
            return this;
        }

        public Builder setListener(Response.Listener<RssFeed> listener) {
            mListener = listener;
            return this;
        }

        public Builder setErrorListener(Response.ErrorListener errorListener) {
            mErrorListener = errorListener;
            return this;
        }

        public Builder setCacheDurationMs(long cacheDurationMs) {
            mCacheDurationMs = cacheDurationMs;
            return this;
        }

        public GoogleFeedLoadRequest build() {
            return new GoogleFeedLoadRequest(this);
        }
    }
}
