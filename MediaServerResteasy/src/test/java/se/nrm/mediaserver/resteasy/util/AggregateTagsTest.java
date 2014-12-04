package se.nrm.mediaserver.resteasy.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ingimar
 */
public class AggregateTagsTest {

    public AggregateTagsTest() {
    }

    @Test
    public void testAggregateTags() {
        FileUploadForm form = new FileUploadForm();
        String expResult = "view:above";
        form.setTags(expResult);
        AggregateTags instance = new AggregateTags();
        String result = instance.aggregateTags(form);
        assertEquals(expResult, result);
    }

    @Test
    public void testAggregateTags_when_null() {
        FileUploadForm form = new FileUploadForm();
        String expResult = "";
        form.setTags(null);
        AggregateTags instance = new AggregateTags();
        String result = instance.aggregateTags(form);
        assertEquals(expResult, result);
    }

    @Test
    public void testAggregateTags2() {
        FileUploadForm form = new FileUploadForm();
        String expResult = "view:above";
        form.setTags(expResult);
        String[] mocktaglist = {"tag1:value1", "tag2:value2", "tag3:value3"};
        form.setTaglist(mocktaglist);
        AggregateTags instance = new AggregateTags();
        String result = instance.aggregateTags(form);
        expResult = expResult.concat("&tag1:value1&tag2:value2&tag3:value3");
        assertEquals(expResult, result);
    }

    @Test
    public void testAggregateTags3() {
        FileUploadForm form = new FileUploadForm();
        String expResult = "tag1:value1&tag2:value2&tag3:value3";
        String[] mocktaglist = {"tag1:value1", "tag2:value2", "tag3:value3"};
        form.setTaglist(mocktaglist);
        AggregateTags instance = new AggregateTags();
        String result = instance.aggregateTags(form);
        assertEquals(expResult, result);
    }

    @Test
    public void testAggregateTags_list_is_null() {
        FileUploadForm form = new FileUploadForm();
        String expResult = "";
        String[] mocktaglist = null;
        form.setTaglist(mocktaglist);
        AggregateTags instance = new AggregateTags();
        String result = instance.aggregateTags(form);
        assertEquals(expResult, result);
    }

}
