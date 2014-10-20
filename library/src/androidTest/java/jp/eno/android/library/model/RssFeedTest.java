package jp.eno.android.library.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by eno314 on 2014/10/19.
 */
@Config(manifest = "build/intermediates/manifests/debug/AndroidManifest.xml", resourceDir = "../../../../build/intermediates/res/debug", emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class RssFeedTest {

    /**
     * isValidは全ての値が揃っているの場合にtrueを返す
     */
    @Test
    public void isValid_shouldReturnTrueWithAllValues() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "aaa";
        rssFeed.link = "aaa";
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();
        rssFeed.entryList.add(createValidRssEntry());

        assertThat(rssFeed.isValid()).isTrue();
    }

    /**
     * isValidはFeedUrlが空の場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithEmptyFeedUrl() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "";
        rssFeed.title = "aaa";
        rssFeed.link = "aaa";
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();
        rssFeed.entryList.add(createValidRssEntry());

        assertThat(rssFeed.isValid()).isFalse();
    }

    /**
     * isValidはFeedUrlがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithNullFeedUrl() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = null;
        rssFeed.title = "aaa";
        rssFeed.link = "aaa";
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();
        rssFeed.entryList.add(createValidRssEntry());

        assertThat(rssFeed.isValid()).isFalse();
    }

    /**
     * isValidはタイトルが空の場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithEmptyTitle() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "";
        rssFeed.link = "aaa";
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();
        rssFeed.entryList.add(createValidRssEntry());

        assertThat(rssFeed.isValid()).isFalse();
    }

    /**
     * isValidはタイトルがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithNullTitle() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = null;
        rssFeed.link = "aaa";
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();
        rssFeed.entryList.add(createValidRssEntry());

        assertThat(rssFeed.isValid()).isFalse();
    }

    /**
     * isValidはlinkが空の場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithEmptyLink() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "aaa";
        rssFeed.link = "";
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();
        rssFeed.entryList.add(createValidRssEntry());

        assertThat(rssFeed.isValid()).isFalse();
    }

    /**
     * isValidはlinkがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithNullLink() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "aaa";
        rssFeed.link = null;
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();
        rssFeed.entryList.add(createValidRssEntry());

        assertThat(rssFeed.isValid()).isFalse();
    }

    /**
     * isValidはdescriptionが空の場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithEmptyDescription() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "aaa";
        rssFeed.link = "aaa";
        rssFeed.description = "";
        rssFeed.entryList = new ArrayList<RssEntry>();
        rssFeed.entryList.add(createValidRssEntry());

        assertThat(rssFeed.isValid()).isFalse();
    }

    /**
     * isValidはdescriptionがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithNullDescription() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "aaa";
        rssFeed.link = "aaa";
        rssFeed.description = null;
        rssFeed.entryList = new ArrayList<RssEntry>();
        rssFeed.entryList.add(createValidRssEntry());

        assertThat(rssFeed.isValid()).isFalse();
    }

    /**
     * isValidはentryListが空の場合にtrueを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithEmptyEntryList() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "aaa";
        rssFeed.link = "aaa";
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();

        assertThat(rssFeed.isValid()).isTrue();
    }

    /**
     * isValidはFeedUrlがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithNullEntryList() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "aaa";
        rssFeed.link = "aaa";
        rssFeed.description = "aaa";
        rssFeed.entryList = null;

        assertThat(rssFeed.isValid()).isFalse();
    }

    /**
     * isValidはFeedUrlがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithInValidEntryList() {
        final RssFeed rssFeed = new RssFeed();
        rssFeed.feedUrl = "aaa";
        rssFeed.title = "aaa";
        rssFeed.link = "aaa";
        rssFeed.description = "aaa";
        rssFeed.entryList = new ArrayList<RssEntry>();
        rssFeed.entryList.add(createInValidRssEntry());

        assertThat(rssFeed.isValid()).isFalse();
    }

    private RssEntry createValidRssEntry() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "aaa";
        rssEntry.link = "aaa";
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = "aaa";
        rssEntry.content = "aaa";

        return rssEntry;
    }

    private RssEntry createInValidRssEntry() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "";
        rssEntry.link = "aaa";
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = "aaa";
        rssEntry.content = "aaa";

        return rssEntry;
    }
}
