package net.devcouch.controller;

import io.crate.shade.com.fasterxml.jackson.core.JsonProcessingException;
import io.crate.shade.com.fasterxml.jackson.databind.ObjectMapper;
import net.devcouch.dao.TweetDAO;
import net.devcouch.domain.tweet.TweetResponse;
import net.devcouch.domain.tweet.SearchRequest;
import net.devcouch.domain.tweet.Tweet;
import net.devcouch.exception.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private TweetDAO tweetDAO;

    @Autowired
    public TweetController(TweetDAO tweetDAO) {
        this.tweetDAO = tweetDAO;
    }

    @CrossOrigin
    @RequestMapping("/")
    public String findAll(@RequestParam(value = "limit", required = false, defaultValue = "-1") String limitStr) {
        TweetResponse.Builder response = new TweetResponse.Builder();
        int limit = -1;
        try {
            limit = Integer.parseInt(limitStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();
        List<Tweet> tweets = tweetDAO.findAll(limit);
        long duration = System.currentTimeMillis() - start;
        response.tweets(tweets);
        response.duration(duration);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(response.build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/query", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String search(@RequestBody String query) {
        TweetResponse.Builder response = new TweetResponse.Builder();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SearchRequest searchRequest = objectMapper.readValue(query, SearchRequest.class);
            long start = System.currentTimeMillis();
            List<Tweet> tweets = tweetDAO.findByKeyword(searchRequest.keyword);
            long duration = System.currentTimeMillis() - start;
            response.tweets(tweets);
            response.duration(duration);
            return objectMapper.writeValueAsString(response.build());
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }

    }
}
