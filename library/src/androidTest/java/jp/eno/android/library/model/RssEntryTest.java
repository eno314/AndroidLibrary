package jp.eno.android.library.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by eno314 on 2014/10/19.
 */
@Config(manifest = "build/intermediates/manifests/debug/AndroidManifest.xml", resourceDir = "../../../../build/intermediates/res/debug", emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class RssEntryTest {

    /**
     * isValidは全ての値が揃っているの場合にtrueを返す
     */
    @Test
    public void isValid_shouldReturnTrueWithAllValues() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "aaa";
        rssEntry.link = "aaa";
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = "aaa";
        rssEntry.content = "aaa";

        assertThat(rssEntry.isValid()).isTrue();
    }

    /**
     * isValidはタイトルが空の場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithEmptyTitle() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "";
        rssEntry.link = "aaa";
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = "aaa";
        rssEntry.content = "aaa";

        assertThat(rssEntry.isValid()).isFalse();
    }

    /**
     * isValidはタイトルがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithNullTitle() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = null;
        rssEntry.link = "aaa";
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = "aaa";
        rssEntry.content = "aaa";

        assertThat(rssEntry.isValid()).isFalse();
    }

    /**
     * isValidはリンクURLが空の場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithEmptyLink() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "aaa";
        rssEntry.link = "";
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = "aaa";
        rssEntry.content = "aaa";

        assertThat(rssEntry.isValid()).isFalse();
    }

    /**
     * isValidはリンクURLがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithNullLink() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "aaa";
        rssEntry.link = null;
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = "aaa";
        rssEntry.content = "aaa";

        assertThat(rssEntry.isValid()).isFalse();
    }

    /**
     * isValidはpublishedDateがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithNullPublishDate() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "aaa";
        rssEntry.link = "aaa";
        rssEntry.publishedDate = null;
        rssEntry.contentSnippet = "aaa";
        rssEntry.content = "aaa";

        assertThat(rssEntry.isValid()).isFalse();
    }

    /**
     * isValidはcontentSnippetが空の場合にtrueを返す
     */
    @Test
    public void isValid_shouldReturnTrueWithEmptyContentSnippet() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "aaa";
        rssEntry.link = "aaa";
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = "";
        rssEntry.content = "aaa";

        assertThat(rssEntry.isValid()).isTrue();
    }

    /**
     * isValidはcontentSnippetがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnTrueWithNullTitle() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "aaa";
        rssEntry.link = "aaa";
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = null;
        rssEntry.content = "aaa";

        assertThat(rssEntry.isValid()).isTrue();
    }

    /**
     * isValidはContentが空の場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithEmptyContent() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "aaa";
        rssEntry.link = "aaa";
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = "aaa";
        rssEntry.content = "";

        assertThat(rssEntry.isValid()).isFalse();
    }

    /**
     * isValidはContentがnullの場合にfalseを返す
     */
    @Test
    public void isValid_shouldReturnFalseWithNullContent() {
        final RssEntry rssEntry = new RssEntry();
        rssEntry.title = "aaa";
        rssEntry.link = "aaa";
        rssEntry.publishedDate = new Date();
        rssEntry.contentSnippet = "aaa";
        rssEntry.content = null;

        assertThat(rssEntry.isValid()).isFalse();
    }
}
