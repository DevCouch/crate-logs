package net.devcouch.domain.tweet;

import java.sql.Timestamp;

public class Tweet {
    public final String id;
    public final boolean retweeted;
    public final String source;
    public final String text;
    public final Timestamp createdAt;
    public final User user;

    public Tweet(String id, boolean retweeted, String source, String text, Timestamp createdAt, User user) {
        this.id = id;
        this.retweeted = retweeted;
        this.source = source;
        this.text = text;
        this.createdAt = createdAt;
        this.user = user;
    }

    public static class Builder {
        private String id = "";
        private boolean retweeted = false;
        private String source = "";
        private String text = "";
        private Timestamp createdAt = null;
        private User user = null;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder retweeted(boolean retweeted) {
            this.retweeted = retweeted;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Tweet build() {
            return new Tweet(id, retweeted, source, text, createdAt, user);
        }
    }
}
