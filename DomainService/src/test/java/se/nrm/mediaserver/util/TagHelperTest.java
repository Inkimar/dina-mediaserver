/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.mediaserver.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import se.nrm.mediaserver.media3.domain.Image;
import se.nrm.mediaserver.media3.domain.Media;

/**
 *
 * @author ingimar
 */
public class TagHelperTest {

    @Test
    public void testAdding_VALID_Tags() {
        Media media = new Image("ingimar");
        String tags = "key:value&key1:value1";
        List<String> expected = Arrays.asList("key:value", "key1:value1");
        TagHelper instance = new TagHelper();
        List<String> addingTags = instance.addingTags(media, tags);
        assertEquals(expected, addingTags);
    }

    @Test
    public void testAdding_ONLY_KEY_SPLITTER_Tag() {
        Media media = new Image("ingimar");
        String tags = "key:value&";
        List<String> expected = Arrays.asList("key:value");
        TagHelper instance = new TagHelper();
        List<String> addingTags = instance.addingTags(media, tags);
        assertEquals(expected, addingTags);
    }

    @Test
    public void testAdding_ONLY_KEY_Tag() {
        Media media = new Image("ingimar");
        String tags = "key:";
        List<String> expected = Collections.EMPTY_LIST;
        TagHelper instance = new TagHelper();
        List<String> addingTags = instance.addingTags(media, tags);
        assertEquals(expected, addingTags);
    }

    @Test
    public void testAdding_STARTS_WITH_DELIM_Tag() {
        Media media = new Image("ingimar");
        String tags = ":value";
        List<String> expected = Collections.EMPTY_LIST;
        TagHelper instance = new TagHelper();
        List<String> addingTags = instance.addingTags(media, tags);
        assertEquals(expected, addingTags);
    }

    @Test
    public void testAdding_DELIM_Tag() {
        Media media = new Image("ingimar");
        String tags = ":::::";
        TagHelper instance = new TagHelper();
        List<String> expected = Collections.EMPTY_LIST;
        List<String> addingTags = instance.addingTags(media, tags);
        assertEquals(expected, addingTags);
    }

    @Test
    public void testAdding_DELIM_AND_SPLITTER_1_Tag() {
        Media media = new Image("ingimar");
        String tags = "&:&:&";
        TagHelper instance = new TagHelper();
        List<String> expected = Collections.EMPTY_LIST;
        List<String> addingTags = instance.addingTags(media, tags);
        assertEquals(expected, addingTags);
    }

    @Test
    public void testAdding_DELIM_AND_SPLITTER_2_Tag() {
        Media media = new Image("ingimar");
        String tags = ":&:&";
        TagHelper instance = new TagHelper();
        List<String> expected = Collections.EMPTY_LIST;
        List<String> addingTags = instance.addingTags(media, tags);
        assertEquals(expected, addingTags);
    }

    @Test
    public void testAdding_STARTS_WITH_NO_DELIM_Tag() {
        Media media = new Image("ingimar");
        String tags = "string_with_no__delim";
        TagHelper instance = new TagHelper();
        List<String> expected = Collections.EMPTY_LIST;
        List<String> addingTags = instance.addingTags(media, tags);
        assertEquals(expected, addingTags);
    }

    @Test
    public void testAdding_EMPTY_Tag() {
        Media media = new Image("ingimar");
        String tags = "";
        TagHelper instance = new TagHelper();
        List<String> expected = Collections.EMPTY_LIST;
        List<String> addingTags = instance.addingTags(media, tags);
        assertEquals(expected, addingTags);
    }

    @Test
    public void testParseRegexp() {
        String tags = "view:sitting&country:sweden";
        String expResult = "m.TAGS REGEXP ('view:sitting') AND m.TAGS REGEXP ('country:sweden')".trim();
        TagHelper instance = new TagHelper();
        String result = instance.sqlUsageParseWithRegexp(tags);

        assertEquals(expResult, result);
    }

    @Test
    public void testLongParse_ALL_SHOULD_MATCH_Regexp() {
        String tags = "view:sitting&country:sweden&photo:diginatour&source:wiki";
        String expResult = "m.TAGS REGEXP ('view:sitting') AND m.TAGS REGEXP ('country:sweden') AND m.TAGS REGEXP ('photo:diginatour') AND m.TAGS REGEXP ('source:wiki')".trim();

        TagHelper instance = new TagHelper();
        String result = instance.sqlUsageParseWithRegexp(tags);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseJPQL() {
        String tags = "view:sitting&country:sweden";
        String expResult = "m.taggar LIKE '%view:sitting%' AND m.taggar LIKE '%country:sweden%'".trim();
        TagHelper instance = new TagHelper();
        String result = instance.sqlUsageParseWithLike(tags);

        assertEquals(expResult, result);
    }
}
