package com.pasimann.app.service;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.pasimann.app.service.YouTubeApiService;
import com.pasimann.app.model.YouTubeItem;


public class YouTubeApiServiceTest extends TestCase {

    public YouTubeApiServiceTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite( YouTubeApiServiceTest.class );
    }

    public void testSearch() {

        YouTubeApiService service = new YouTubeApiService();
        List<YouTubeItem> result = service.youTubeSearch("Meshuggah", 5);

        assertTrue(result.size() == 5);

        for (YouTubeItem item : result) {
           assertTrue(item.getUrl() != null);
           assertTrue(item.getTitle() != null);
        }
    }
}
