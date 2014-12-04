/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.mediaserver.util;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import se.nrm.mediaserver.media3.domain.Attachment;
import se.nrm.mediaserver.media3.domain.Image;
import se.nrm.mediaserver.media3.domain.Media;
import se.nrm.mediaserver.media3.domain.Sound;
import se.nrm.mediaserver.media3.domain.Video;

/**
 *
 * @author ingimar
 */
public class SupportedMediaTest {

    public SupportedMediaTest() {
    }

    @Test @Ignore
    public void testFactoryImage_With_JPEG() {
        Class expectedClazz = Image.class;
        Media result = SupportedMedia.IMAGE.getMedia("image/jpeg");
        assertEquals(expectedClazz, result.getClass());
    }

    @Test @Ignore
    public void testFactoryImage_With_GIF() {
        Class expectedClazz = Image.class;
        Media result = SupportedMedia.IMAGE.getMedia("image/gif");
        assertEquals(expectedClazz, result.getClass());
    }
    
    @Test @Ignore
    public void testFactoryVideo_With_MP4() {
        Class expectedClazz = Video.class;
        Media result = SupportedMedia.VIDEO.getMedia("video/mp4");
        assertEquals(expectedClazz, result.getClass());
    }
    
    @Test @Ignore
    public void testFactoryAudio_With_OGG() {
        Class expectedClazz = Sound.class;
        Media result = SupportedMedia.SOUND.getMedia("audio/ogg");
        assertEquals(expectedClazz, result.getClass());
    }
    
    @Test @Ignore
    public void testFactoryAttachment_With_PDF() {
        Class expectedClazz = Attachment.class;
        Media result = SupportedMedia.ATTACHMENT.getMedia("application/pdf");
        assertEquals(expectedClazz, result.getClass());
    }

}
