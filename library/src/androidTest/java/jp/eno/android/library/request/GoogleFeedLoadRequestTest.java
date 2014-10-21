package jp.eno.android.library.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.apache.http.HttpStatus;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import jp.eno.android.library.model.RssEntry;
import jp.eno.android.library.model.RssFeed;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by eno314 on 2014/10/18.
 */
@Config(manifest = "build/intermediates/manifests/debug/AndroidManifest.xml", resourceDir = "../../../../build/intermediates/res/debug", emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class GoogleFeedLoadRequestTest {

    private Response.Listener<RssFeed> mListener;
    private Response.ErrorListener mErrorListener;

    @Before
    public void setUp() {
        mListener = new NullListener();
        mErrorListener = new NullErrorListener();
    }

    /**
     * コンストラクタはビルダーから正常にインスタンスを生成する
     */
    @Test
    public void constructor_shouldReturnInstanceByBuilder() {
        final GoogleFeedLoadRequest request = createRequestBuilder().build();

        assertNotNull(request);
    }

    /**
     * parseNetworkResponseは200を返した時にパース結果を返す
     */
    @Test
    public void parseNetworkResponse_shouldReturnResultWith200Response() {
        final GoogleFeedLoadRequest request = createRequestBuilder().build();

        final Map<String, String> headers = Collections.emptyMap();
        final NetworkResponse networkResponse =
                new NetworkResponse(HttpStatus.SC_OK, MULTI_ENTRY_BODY.getBytes(), headers, true);

        final Response<RssFeed> response = request.parseNetworkResponse(networkResponse);

        Assertions.assertThat(response.isSuccess()).isTrue();
    }

    /**
     * parseNetworkResponseはパース失敗時にエラーを返す
     */
    @Test
    public void parseNetworkResponse_shouldReturnErrorWithParseError() {
        final GoogleFeedLoadRequest request = createRequestBuilder().build();

        final Map<String, String> headers = Collections.emptyMap();
        final NetworkResponse networkResponse =
                new NetworkResponse(HttpStatus.SC_OK, "Invalid Json".getBytes(), headers, true);

        final Response<RssFeed> response = request.parseNetworkResponse(networkResponse);

        Assertions.assertThat(response.isSuccess()).isFalse();
    }

    /**
     * parseNetworkResponseは200以外を返却時にエラーを返す
     */
    @Test
    public void parseNetworkResponse_shouldReturnErrorWithNon200Response() {
        final GoogleFeedLoadRequest request = createRequestBuilder().build();

        final Map<String, String> headers = Collections.emptyMap();
        final NetworkResponse networkResponse =
                new NetworkResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, MULTI_ENTRY_BODY.getBytes(), headers, true);

        final Response<RssFeed> response = request.parseNetworkResponse(networkResponse);

        Assertions.assertThat(response.isSuccess()).isFalse();
    }

    /**
     * deliverResponseはListenerが設定されているときにコールする
     */
    @Test
    public void deliverResponse_shouldCallListenerWhenSetListener() {
        final Response.Listener<RssFeed> listener = spy(mListener);
        final GoogleFeedLoadRequest request = new GoogleFeedLoadRequest.Builder("http://hogehoge")
                .setListener(listener)
                .setErrorListener(mErrorListener)
                .build();

        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "aaa";
        rssFeed.link = "aaa";
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();

        request.deliverResponse(rssFeed);

        verify(listener).onResponse(eq(rssFeed));
    }

    /**
     * deliverResponseはListenerが設定されていないときはコールされない
     */
    @Test
    public void deliverResponse_shouldNotCallListenerWhenNotSetListener() {
        final Response.Listener<RssFeed> listener = spy(mListener);
        final GoogleFeedLoadRequest request = new GoogleFeedLoadRequest.Builder("http://hogehoge")
                .setListener(null)
                .setErrorListener(mErrorListener)
                .build();

        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "aaa";
        rssFeed.link = "aaa";
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();

        request.deliverResponse(rssFeed);
    }

    /**
     * テスト用のリクエストBuildクラスを生成する
     */
    private GoogleFeedLoadRequest.Builder createRequestBuilder() {
        return new GoogleFeedLoadRequest.Builder("http://example.com")
                .setListener(mListener)
                .setErrorListener(mErrorListener);
    }


    /**
     * 成功時の何も実行しないダミーのリスナー
     */
    private class NullListener implements Response.Listener<RssFeed> {

        @Override
        public void onResponse(RssFeed response) {
            // no operation
        }
    }

    /**
     * 失敗時に何もしないダミーのリスナー
     */
    private class NullErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            // no operation
        }
    }

    private static final String MULTI_ENTRY_BODY = "{\n" +
            "  \"responseData\": {\n" +
            "    \"feed\": {\n" +
            "      \"feedUrl\": \"http://keiba.jp/rss/all.xml\",\n" +
            "      \"title\": \"競馬JAPAN～競馬予想をさらなる高みに導く～\",\n" +
            "      \"link\": \"http://keiba.jp/\",\n" +
            "      \"author\": \"\",\n" +
            "      \"description\": \"競馬予想に命をかけた男たちが集結する競馬JAPAN。プロの予想哲学が詰まった読み応え満点の競馬コラムや、買い方や資金配分までこだわりつくした渾身の週末予想をなんと無料公開。さらに日本の予想界をリードし続けている清水成駿や上田琢己ほか、大御所予想家の最終結論を凝縮した最強のWEB競馬新聞=競馬成駿も公開しています。\",\n" +
            "      \"type\": \"rss20\",\n" +
            "      \"entries\": [\n" +
            "        {\n" +
            "          \"title\": \"ワンアンドオンリー対複数出しの池江、友道勢 - 競馬コラム\",\n" +
            "          \"link\": \"http://keiba.jp/column/keiba-wiki/?cid=30374\",\n" +
            "          \"author\": \"\",\n" +
            "          \"publishedDate\": \"Sun, 19 Oct 2014 01:09:16 -0700\",\n" +
            "          \"contentSnippet\": \"\\n　皐月賞馬イスラボニータ、4戦無敗のエイシンヒカリが菊花賞を回避したため、ワンアンドオンリーの存在が断然となってきた菊花賞。\",\n" +
            "          \"content\": \"<img src=\\\"http://keiba.jp/img/top/thumb/column/thumb_ura_01.jpg\\\" alt=\\\"競馬ウィキリークス\\\" width=\\\"100\\\" height=\\\"80\\\" align=\\\"left\\\">\\n　皐月賞馬イスラボニータ、4戦無敗のエイシンヒカリが菊花賞を回避したため、ワンアンドオンリーの存在が断然となってきた菊花賞。\",\n" +
            "          \"categories\": [\n" +
            "\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"title\": \"アンバルブライベンが逃げ切りOP初勝利…福島民友C - 競馬ニュース\",\n" +
            "          \"link\": \"http://keiba.jp/news/?cid=30373\",\n" +
            "          \"author\": \"\",\n" +
            "          \"publishedDate\": \"Sun, 19 Oct 2014 00:16:00 -0700\",\n" +
            "          \"contentSnippet\": \"\\n10月19日(日)、3回福島2日目11Rで福島民友カップ(芝1200m)が行なわれ、田中健騎手騎乗の2番人気・アンバルブライベン(牝5、栗東・福島信厩舎)が優勝。\",\n" +
            "          \"content\": \"<img src=\\\"http://keiba.jp/img/top/thumb/news/thumb_news_01.jpg\\\" alt=\\\"ニュース記事\\\" width=\\\"100\\\" height=\\\"80\\\" align=\\\"left\\\">\\n10月19日(日)、3回福島2日目11Rで福島民友カップ(芝1200m)が行なわれ、田中健騎手騎乗の2番人気・アンバルブライベン(牝5、栗東・福島信厩舎)が優勝。\",\n" +
            "          \"categories\": [\n" +
            "\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  \"responseDetails\": null,\n" +
            "  \"responseStatus\": 200\n" +
            "}";
}
