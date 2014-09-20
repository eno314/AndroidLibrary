package jp.eno.android.library.volley;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;

/**
 * Created by eno314 on 2014/09/20.
 */
public class VolleyCache {

    /**
     * キャッシュエントリの生成
     * @param response   ネットワークレスポンス
     * @param durationMS 有効期限(ミリ秒)
     * @param cacheFirst trueの場合は取得待ち中にキャッシュを使用
     * @return キャッシュエントリー
     */
    public static Cache.Entry createEntry(NetworkResponse response,
                                          long durationMS,
                                          boolean cacheFirst) {

        Cache.Entry entry     = new Cache.Entry();
        entry.data            = response.data;
        entry.etag            = null;
        entry.softTtl         = System.currentTimeMillis() + durationMS;
        entry.ttl             = (cacheFirst) ? Long.MAX_VALUE : entry.softTtl;
        entry.serverDate      = 0;
        entry.responseHeaders = response.headers;

        return entry;
    }
}
