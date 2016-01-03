package net.devcouch.domain.tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetResponse {
    public final List<Tweet> tweets;
    public final long duration;

    public TweetResponse(List<Tweet> tweets, long duration) {
        this.tweets = tweets;
        this.duration = duration;
    }

    public static class Builder {
        private List<Tweet> tweets = new ArrayList<>();
        private long duration = 0;

        public Builder tweets(List<Tweet> tweets) {
            this.tweets = tweets;
            return this;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public TweetResponse build() {
            return new TweetResponse(tweets, duration);
        }
    }
}
