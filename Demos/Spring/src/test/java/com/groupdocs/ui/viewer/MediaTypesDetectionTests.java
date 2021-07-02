package com.groupdocs.ui.viewer;

import com.groupdocs.ui.viewer.util.ViewerUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;

public class MediaTypesDetectionTests {

    @Test
    public void testOtf() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".otf");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("application/x-font-otf"), actualMediaType);
    }

    @Test
    public void testSfnt() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".sfnt");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("application/font-sfnt"), actualMediaType);
    }

    @Test
    public void testTtf() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".ttf");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("application/x-font-ttf"), actualMediaType);
    }

    @Test
    public void testWoff() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".woff");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("application/x-font-woff"), actualMediaType);
    }

    @Test
    public void testWoff2() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".woff2");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("application/x-font-woff2"), actualMediaType);
    }

    @Test
    public void testEot() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".eot");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("application/vnd.ms-fontobject"), actualMediaType);
    }

    @Test
    public void testXml() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".xml");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("application/xml"), actualMediaType);
    }

    @Test
    public void testSvg() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".svg");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("image/svg+xml"), actualMediaType);
    }

    @Test
    public void testPng() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".png");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("image/png"), actualMediaType);
    }

    @Test
    public void testJpg() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".jpg");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("image/jpeg"), actualMediaType);
    }

    @Test
    public void testHtml() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".html");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("text/html"), actualMediaType);
    }

    @Test
    public void testJs() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".js");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("application/javascript"), actualMediaType);
    }

    @Test
    public void testCss() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".css");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("text/css"), actualMediaType);
    }

    @Test
    public void testGif() {
        final MediaType actualMediaType = ViewerUtils.detectMediaType(".gif");
        Assert.assertNotNull(actualMediaType);
        Assert.assertEquals(MediaType.valueOf("image/gif"), actualMediaType);
    }
}