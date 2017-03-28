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
import com.pasimann.app.service.ChatMessageService;

@Controller
public class YouTubeApiController {

    public class Message {

      private String receiver;
      private String msg;

      public Message(String receiver, String msg) {
        this.receiver = receiver;
        this.msg = msg;
      }

      public String getReceiver() {
        return this.receiver;
      }

      public String getMsg() {
        return this.msg;
      }

    };


    @Autowired
    YouTubeApiService service;

    @Autowired
    ChatMessageService chat;

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

        result.add(new YouTubeItem("foobar", search, "far", "czar"));
        return result;
    }

    @RequestMapping(value={"/generate-chat-message"}, method=RequestMethod.GET)
    public @ResponseBody Message getRandomChatMessage(
            @RequestParam(value="receiver", required=true) String receiver) {

        return new Message(receiver, chat.generateChatMessage(receiver));
    }
}
