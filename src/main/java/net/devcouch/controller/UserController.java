package net.devcouch.controller;

import io.crate.shade.com.fasterxml.jackson.core.JsonProcessingException;
import io.crate.shade.com.fasterxml.jackson.databind.ObjectMapper;
import net.devcouch.dao.TweetDAO;
import net.devcouch.dao.UserDAO;
import net.devcouch.domain.tweet.Tweet;
import net.devcouch.domain.tweet.User;
import net.devcouch.exception.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private UserDAO userDAO;
    private TweetDAO tweetDAO;

    @Autowired
    public UserController(UserDAO userDAO, TweetDAO tweetDAO) {
        this.userDAO = userDAO;
        this.tweetDAO = tweetDAO;
    }

    @RequestMapping("/")
    public String findAll(@RequestParam(value = "limit", required = false, defaultValue = "100") String limitStr) {
        int limit = -1;
        try {
            limit = Integer.parseInt(limitStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        List<User> users = userDAO.findAll(limit);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @RequestMapping("/{id}")
    public String findById(@PathVariable String id) {
        User user = userDAO.findById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @RequestMapping("/{id}/tweets")
    public String tweets(@PathVariable String id) {
        List<Tweet> tweets = tweetDAO.getByUserId(id);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(tweets);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }
}
