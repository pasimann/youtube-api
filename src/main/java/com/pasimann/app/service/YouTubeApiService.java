package com.pasimann.app.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.model.SearchListResponse;

import com.pasimann.app.model.YouTubeItem;


/*
 *  Implementation example from:
 *  https://github.com/youtube/api-samples/blob/master/java/src/main/java/com/google/api/services/samples/youtube/cmdline/data/Search.java
 *
 */
@Component
public class YouTubeApiService {

  private static Logger log = LoggerFactory.getLogger(YouTubeApiService.class);

  public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
  public static final JsonFactory JSON_FACTORY     = new JacksonFactory();

  private static final String PROPERTIES_FILENAME     = "GOOGLEYOUTUBE";

  private static final String GOOGLE_YOUTUBE_URL      = "https://www.youtube.com/watch?v=";
  private static final String YOUTUBE_SEARCH_TYPE     = "video";
  private static final String YOUTUBE_SEARCH_FIELDS   = "items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)";
  private static final String YOUTUBE_API_APPLICATION = "google-youtube-api-search";
  private static final String YOUTUBE_APIKEY_ENV      = "YOUTUBE_APIKEY";

  private static final long NUMBER_OF_VIDEOS_RETURNED  = 25;

  private static YouTube youtube;

  private static ResourceBundle propertiesBundle;

  static {

    propertiesBundle = ResourceBundle.getBundle(PROPERTIES_FILENAME);

    // This object is used to make YouTube Data API requests. The last
    // argument is required, but since we don't need anything
    // initialized when the HttpRequest is initialized, we override
    // the interface and provide a no-op function.

    youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY,
      new HttpRequestInitializer() {
          public void initialize(HttpRequest request) throws IOException {
            // intentionally left empty
          }
        }).setApplicationName(YOUTUBE_API_APPLICATION).build();
  }


  /**
  	 * Makes YouTube search into video library with given keywords.
  	 *
  	 */
  	public List<YouTubeItem> youTubeSearch(String searchQuery, int maxSearch) {
  		  log.info("Starting YouTube search... " +searchQuery);

  		  List<YouTubeItem> rvalue = new ArrayList<YouTubeItem>();

  		  try {

  	         if (youtube != null) {

  			        // Define the API request for retrieving search results.
  	            YouTube.Search.List search = youtube.search().list("id,snippet");

                // Set your developer key from the {{ Google Cloud Console }} for
                // non-authenticated requests. See:
                // {{ https://cloud.google.com/console }}

                String apiKey = System.getenv(YOUTUBE_APIKEY_ENV);

                if ( apiKey == null ) {
    	            apiKey = propertiesBundle.getString("youtube.apikey");
                }

  	            search.setKey(apiKey);
  	            search.setQ(searchQuery);

  	            // Restrict the search results to only include videos. See:
  	            // https://developers.google.com/youtube/v3/docs/search/list#type

  	            search.setType(YOUTUBE_SEARCH_TYPE);

  	            // To increase efficiency, only retrieve the fields that the
  	            // application uses.

  	            String youTubeFields = propertiesBundle.getString("youtube.fields");

  	            if (youTubeFields != null && !youTubeFields.isEmpty()) {
  	            	search.setFields(youTubeFields);
  	            } else {
  	            	search.setFields(YOUTUBE_SEARCH_FIELDS);
  	            }

  	            if (maxSearch < 1) {
  		            String maxVideosReturned = propertiesBundle.getString("youtube.maxvid");

  		            if (maxVideosReturned != null && !maxVideosReturned.isEmpty()) {
  		            	search.setMaxResults(Long.valueOf(maxVideosReturned));
  		            } else {
  		            	search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
  		            }
  	            } else {
  	            	search.setMaxResults((long) maxSearch);
  	            }

  	            // Call the API and print results.
  	            SearchListResponse searchResponse = search.execute();
  	            List<SearchResult> searchResultList = searchResponse.getItems();

  	            if (searchResultList != null && searchResultList.size() > 0) {

  	            	for (SearchResult r : searchResultList) {
                    YouTubeItem item = new YouTubeItem(
                          GOOGLE_YOUTUBE_URL + r.getId().getVideoId(),
                          r.getSnippet().getTitle(),
                          r.getSnippet().getThumbnails().getDefault().getUrl(),
                          r.getSnippet().getDescription());
  	            		rvalue.add(item);
  	            	}

  	            } else {
  	            	log.info("No search results got from YouTube API");
  	            }

  	         } else {
  	        	 log.warn("YouTube API not initialized correctly!");
  	         }

  	      } catch (GoogleJsonResponseException e) {
  	    	  	log.warn("There was a service error: " + e.getDetails().getCode() + " : "
  	                    + e.getDetails().getMessage());
  	      } catch (IOException e) {
  	    	  	log.warn("There was an IO error: " + e.getCause() + " : " + e.getMessage());
  	      } catch (Throwable t) {
  	    	    log.warn("Severe errors!", t);
  	          t.printStackTrace();
  	      }

  		  return rvalue;
  	}
}
