package com.pasimann.app;

import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.pasimann.app.model.YouTubeItem;
import com.pasimann.app.service.YouTubeApiService;

@Controller
public class YouTubeApiController {

    @Autowired
    YouTubeApiService service;

    @RequestMapping(value={"/google-youtube-api"}, method=RequestMethod.GET)
    public @ResponseBody List<YouTubeItem> searchYouTube(
            @RequestParam(value="search", required=true) String search,
            @RequestParam(value="items", required=false, defaultValue="25") String items) {

        int max = Integer.parseInt(items);
        List<YouTubeItem> result = service.youTubeSearch(search, max);
        return result;
    }

    @RequestMapping(value={"/test-dummy-api"}, method=RequestMethod.GET)
    public @ResponseBody List<YouTubeItem> testSearchYouTube(
            @RequestParam(value="search", required=true) String search,
            @RequestParam(value="items", required=false, defaultValue="1") String items) {

        int max = Integer.parseInt(items);
        List<YouTubeItem> result = new ArrayList<>();

        result.add(new YouTubeItem("foo","bar","far","czar"));
        return result;
    }
}
