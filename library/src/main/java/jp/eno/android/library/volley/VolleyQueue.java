package jp.eno.android.library.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * VolleyのQueueを管理するクラス
 * <p/>
 * Created by eno314 on 2014/08/09.
 */
public class VolleyQueue {

    /**
     * Volleyのリクエストキュー
     * アプリ内で１つしか生成させないようにstaticで持つ
     */
    private static RequestQueue sRequestQueue;

    /**
     * キューの取得
     *
     * @param context
     * @return Volleyのリクエストキュー
     */
    public static RequestQueue getQueue(Context context) {

        if (sRequestQueue == null) {
            sRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return sRequestQueue;
    }
}
