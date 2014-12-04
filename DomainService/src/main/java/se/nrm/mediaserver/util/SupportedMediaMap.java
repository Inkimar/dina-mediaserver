/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.mediaserver.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ingimar
 */
public class SupportedMediaMap {

    public Map<String, String> getSupported() {
        Map<String, String> map = new HashMap<>();
        SupportedMedia[] values = SupportedMedia.values();
        for (SupportedMedia media : values) {
            map.putAll(media.getMap());
        }
        return map;
    }

}
