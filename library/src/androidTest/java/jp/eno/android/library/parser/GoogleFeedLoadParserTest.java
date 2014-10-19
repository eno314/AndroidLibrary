package jp.eno.android.library.parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import jp.eno.android.library.model.RssEntry;
import jp.eno.android.library.model.RssFeed;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by eno314 on 2014/10/19.
 */
@Config(manifest = "build/intermediates/manifests/debug/AndroidManifest.xml", resourceDir = "../../../../build/intermediates/res/debug", emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class GoogleFeedLoadParserTest {

    /**
     * parseはnullが与えられた時にParseExceptionを発生させる
     *
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void parse_shouldThrowExceptionWithNull() throws ParseException {
        final GoogleFeedLoadParser parser = new GoogleFeedLoadParser();
        parser.parse(null);
    }

    /**
     * parseは空のバイト配列が与えられた時にParseExceptionを発生させる
     *
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void parse_shouldThrowExceptionWithEmptyByte() throws ParseException {
        final GoogleFeedLoadParser parser = new GoogleFeedLoadParser();
        parser.parse("".getBytes());
    }

    /**
     * parseは不正なJSONが与えられた時にParseExceptionを発生させる
     *
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void parse_shouldThrowExceptionWithInvalidJson() throws ParseException {
        final GoogleFeedLoadParser parser = new GoogleFeedLoadParser();
        parser.parse("Invalid Json object!".getBytes());
    }

    /**
     * parseは必須項目が無い時にParseExceptionを発生させる
     *
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void parse_shouldThrowExceptionWithNoRequiredElementBody() throws ParseException {
        final GoogleFeedLoadParser parser = new GoogleFeedLoadParser();
        parser.parse(NO_REQUIRED_ELEMENT_BODY.getBytes());
    }

    /**
     * parseはentryが0件の場合にパース結果を返却する
     *
     * @throws ParseException
     */
    @Test
    public void parse_shouldThrowExceptionWithEmptyEntryBody() throws ParseException {
        final GoogleFeedLoadParser parser = new GoogleFeedLoadParser();

        final RssFeed rssFeed = parser.parse(EMPTY_ENTRY_BODY.getBytes());

        assertThat(rssFeed.feedUrl).isEqualTo("http://keiba.jp/rss/all.xml");
        assertThat(rssFeed.title).isEqualTo("競馬JAPAN～競馬予想をさらなる高みに導く～");
        assertThat(rssFeed.link).isEqualTo("http://keiba.jp/");
        assertThat(rssFeed.description).isEqualTo("競馬予想に命をかけた男たちが集結する競馬JAPAN。プロの予想哲学が詰まった読み応え満点の競馬コラムや、買い方や資金配分までこだわりつくした渾身の週末予想をなんと無料公開。さらに日本の予想界をリードし続けている清水成駿や上田琢己ほか、大御所予想家の最終結論を凝縮した最強のWEB競馬新聞=競馬成駿も公開しています。");
        assertThat(rssFeed.entryList).hasSize(0);
    }

    /**
     * parseはentryが１件の場合にパース結果を返却する
     *
     * @throws ParseException
     */
    @Test
    public void parse_shouldReturnParsedResultWithSingleEntryBody() throws ParseException, java.text.ParseException {
        final GoogleFeedLoadParser parser = new GoogleFeedLoadParser();

        final RssFeed rssFeed = parser.parse(SINGLE_ENTRY_BODY.getBytes());

        assertThat(rssFeed.feedUrl).isEqualTo("http://keiba.jp/rss/all.xml");
        assertThat(rssFeed.title).isEqualTo("競馬JAPAN～競馬予想をさらなる高みに導く～");
        assertThat(rssFeed.link).isEqualTo("http://keiba.jp/");
        assertThat(rssFeed.description).isEqualTo("競馬予想に命をかけた男たちが集結する競馬JAPAN。プロの予想哲学が詰まった読み応え満点の競馬コラムや、買い方や資金配分までこだわりつくした渾身の週末予想をなんと無料公開。さらに日本の予想界をリードし続けている清水成駿や上田琢己ほか、大御所予想家の最終結論を凝縮した最強のWEB競馬新聞=競馬成駿も公開しています。");
        assertThat(rssFeed.entryList).hasSize(1);
        assertThat(rssFeed.entryList.get(0).title).isEqualTo("ワンアンドオンリー対複数出しの池江、友道勢 - 競馬コラム");
        assertThat(rssFeed.entryList.get(0).link).isEqualTo("http://keiba.jp/column/keiba-wiki/?cid=30374");
        assertThat(rssFeed.entryList.get(0).contentSnippet).isEqualTo("\n　皐月賞馬イスラボニータ、4戦無敗のエイシンヒカリが菊花賞を回避したため、ワンアンドオンリーの存在が断然となってきた菊花賞。");
        assertThat(rssFeed.entryList.get(0).content).isEqualTo("<img src=\"http://keiba.jp/img/top/thumb/column/thumb_ura_01.jpg\" alt=\"競馬ウィキリークス\" width=\"100\" height=\"80\" align=\"left\">\n　皐月賞馬イスラボニータ、4戦無敗のエイシンヒカリが菊花賞を回避したため、ワンアンドオンリーの存在が断然となってきた菊花賞。");

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        assertThat(rssFeed.entryList.get(0).publishedDate).isEqualTo(dateFormat.parse("Sun, 19 Oct 2014 01:09:16 -0700"));
    }

    /**
     * parseはentryが複数件の場合にパース結果を返却する
     *
     * @throws ParseException
     */
    @Test
    public void parse_shouldReturnParsedResultWithMulchEntryResponse() throws ParseException, java.text.ParseException {
        final GoogleFeedLoadParser parser = new GoogleFeedLoadParser();

        final RssFeed rssFeed = parser.parse(MULCH_ENTRY_BODY.getBytes());

        assertThat(rssFeed.feedUrl).isEqualTo("http://keiba.jp/rss/all.xml");
        assertThat(rssFeed.title).isEqualTo("競馬JAPAN～競馬予想をさらなる高みに導く～");
        assertThat(rssFeed.link).isEqualTo("http://keiba.jp/");
        assertThat(rssFeed.description).isEqualTo("競馬予想に命をかけた男たちが集結する競馬JAPAN。プロの予想哲学が詰まった読み応え満点の競馬コラムや、買い方や資金配分までこだわりつくした渾身の週末予想をなんと無料公開。さらに日本の予想界をリードし続けている清水成駿や上田琢己ほか、大御所予想家の最終結論を凝縮した最強のWEB競馬新聞=競馬成駿も公開しています。");
        assertThat(rssFeed.entryList).hasSize(2);

        assertThat(rssFeed.entryList.get(0).title).isEqualTo("ワンアンドオンリー対複数出しの池江、友道勢 - 競馬コラム");
        assertThat(rssFeed.entryList.get(0).link).isEqualTo("http://keiba.jp/column/keiba-wiki/?cid=30374");
        assertThat(rssFeed.entryList.get(0).contentSnippet).isEqualTo("\n　皐月賞馬イスラボニータ、4戦無敗のエイシンヒカリが菊花賞を回避したため、ワンアンドオンリーの存在が断然となってきた菊花賞。");
        assertThat(rssFeed.entryList.get(0).content).isEqualTo("<img src=\"http://keiba.jp/img/top/thumb/column/thumb_ura_01.jpg\" alt=\"競馬ウィキリークス\" width=\"100\" height=\"80\" align=\"left\">\n　皐月賞馬イスラボニータ、4戦無敗のエイシンヒカリが菊花賞を回避したため、ワンアンドオンリーの存在が断然となってきた菊花賞。");

        assertThat(rssFeed.entryList.get(1).title).isEqualTo("アンバルブライベンが逃げ切りOP初勝利…福島民友C - 競馬ニュース");
        assertThat(rssFeed.entryList.get(1).link).isEqualTo("http://keiba.jp/news/?cid=30373");
        assertThat(rssFeed.entryList.get(1).contentSnippet).isEqualTo("\n10月19日(日)、3回福島2日目11Rで福島民友カップ(芝1200m)が行なわれ、田中健騎手騎乗の2番人気・アンバルブライベン(牝5、栗東・福島信厩舎)が優勝。");
        assertThat(rssFeed.entryList.get(1).content).isEqualTo("<img src=\"http://keiba.jp/img/top/thumb/news/thumb_news_01.jpg\" alt=\"ニュース記事\" width=\"100\" height=\"80\" align=\"left\">\n10月19日(日)、3回福島2日目11Rで福島民友カップ(芝1200m)が行なわれ、田中健騎手騎乗の2番人気・アンバルブライベン(牝5、栗東・福島信厩舎)が優勝。");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        assertThat(rssFeed.entryList.get(0).publishedDate).isEqualTo(dateFormat.parse("Sun, 19 Oct 2014 01:09:16 -0700"));
        assertThat(rssFeed.entryList.get(1).publishedDate).isEqualTo(dateFormat.parse("Sun, 19 Oct 2014 00:16:00 -0700"));
    }

    private static final String NO_REQUIRED_ELEMENT_BODY = "{\n" +
            "  \"responseData\": {\n" +
            "    \"feed\": {\n" +
            "      \"title\": \"競馬JAPAN～競馬予想をさらなる高みに導く～\",\n" +
            "      \"link\": \"http://keiba.jp/\",\n" +
            "      \"author\": \"\",\n" +
            "      \"description\": \"競馬予想に命をかけた男たちが集結する競馬JAPAN。プロの予想哲学が詰まった読み応え満点の競馬コラムや、買い方や資金配分までこだわりつくした渾身の週末予想をなんと無料公開。さらに日本の予想界をリードし続けている清水成駿や上田琢己ほか、大御所予想家の最終結論を凝縮した最強のWEB競馬新聞=競馬成駿も公開しています。\",\n" +
            "      \"type\": \"rss20\",\n" +
            "      \"entries\": [\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  \"responseDetails\": null,\n" +
            "  \"responseStatus\": 200\n" +
            "}";

    private static final String EMPTY_ENTRY_BODY = "{\n" +
            "  \"responseData\": {\n" +
            "    \"feed\": {\n" +
            "      \"feedUrl\": \"http://keiba.jp/rss/all.xml\",\n" +
            "      \"title\": \"競馬JAPAN～競馬予想をさらなる高みに導く～\",\n" +
            "      \"link\": \"http://keiba.jp/\",\n" +
            "      \"author\": \"\",\n" +
            "      \"description\": \"競馬予想に命をかけた男たちが集結する競馬JAPAN。プロの予想哲学が詰まった読み応え満点の競馬コラムや、買い方や資金配分までこだわりつくした渾身の週末予想をなんと無料公開。さらに日本の予想界をリードし続けている清水成駿や上田琢己ほか、大御所予想家の最終結論を凝縮した最強のWEB競馬新聞=競馬成駿も公開しています。\",\n" +
            "      \"type\": \"rss20\",\n" +
            "      \"entries\": [\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  \"responseDetails\": null,\n" +
            "  \"responseStatus\": 200\n" +
            "}";

    private static final String SINGLE_ENTRY_BODY = "{\n" +
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
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  \"responseDetails\": null,\n" +
            "  \"responseStatus\": 200\n" +
            "}";

    private static final String MULCH_ENTRY_BODY = "{\n" +
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
