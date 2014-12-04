/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.mediaserver.util;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ingimar
 */
public class SupportedMediaMapTest {

    public SupportedMediaMapTest() {
    }

    @Test
    public void testImageGIF() {
        SupportedMediaMap instance = new SupportedMediaMap();
        String expResult = SupportedMedia.IMAGE.toString();
        Map<String, String> result = instance.getSupported();
        assertEquals(expResult, result.get("image/gif"));
    }

    @Test
    public void testImageJPEG() {
        SupportedMediaMap instance = new SupportedMediaMap();
        String expResult = SupportedMedia.IMAGE.toString();
        Map<String, String> result = instance.getSupported();
        assertEquals(expResult, result.get("image/jpeg"));
    }

    @Test
    public void testVideoMP4() {
        SupportedMediaMap instance = new SupportedMediaMap();
        String expResult = SupportedMedia.VIDEO.toString();
        Map<String, String> result = instance.getSupported();
        assertEquals(expResult, result.get("video/mp4"));
    }

    @Test
    public void testSoundOGG() {
        SupportedMediaMap instance = new SupportedMediaMap();
        String expResult = SupportedMedia.SOUND.toString();
        Map<String, String> result = instance.getSupported();
        assertEquals(expResult, result.get("audio/ogg"));
    }
    @Test
    public void testAttachmentPDF() {
        SupportedMediaMap instance = new SupportedMediaMap();
        String expResult = SupportedMedia.ATTACHMENT.toString();
        Map<String, String> result = instance.getSupported();
        assertEquals(expResult, result.get("application/pdf"));
    }

}
