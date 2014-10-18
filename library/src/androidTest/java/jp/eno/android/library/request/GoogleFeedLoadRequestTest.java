package jp.eno.android.library.request;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import jp.eno.android.library.model.RssFeed;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
}
