package se.nrm.mediaserver.util;

import se.nrm.mediaserver.media3.domain.Image;

import se.nrm.mediaserver.media3.domain.Media;
import se.nrm.mediaserver.media3.domain.Video;
import se.nrm.mediaserver.media3.domain.Sound;
import se.nrm.mediaserver.media3.domain.Attachment;

/**
 *
 * @author ingimar
 */
public class MediaFactory {

    public static Media createMedia(MediaMimeType type) {
        try {
            switch (type) {
                case IMAGE:
                    return new Image();
                case VIDEO:
                    return new Video();
                case SOUND:
                    return new Sound();
                case ATTACHMENT:
                    return new Attachment();
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Media createImage(boolean isExported) {
        Image image = new Image();
        image.setIsExported(isExported);
        return image;
    }

    public static Media createImage2(boolean isExported, String exif) {
        Image image = new Image();
        image.setIsExported(isExported);
        image.setExif(exif);
        return image;
    }

    public static Media createVideo(int startTime, int endTime) {
        Video video = new Video();
        video.setStartTime(startTime);
        video.setEndTime(endTime);
        return video;
    }

    public static Media createSound(int startTime, int endTime) {
        Sound sound = new Sound();
        sound.setStartTime(startTime);
        sound.setEndTime(endTime);
        return sound;
    }

    public static Media createAttachement() {
        Media createdMedia = (Attachment) createMedia(MediaMimeType.ATTACHMENT);
        return createdMedia;
    }
}
