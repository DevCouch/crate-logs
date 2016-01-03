package net.devcouch.domain.tweet;

import java.sql.Timestamp;

public class User {
    public final String id;
    public final String description;
    public final int followerCount;
    public final int friendsCount;
    public final String location;
    public final int statusesCount;
    public final boolean verified;
    public final Timestamp createdAt;

    public User(String id, String description, int followerCount, int friendsCount, String location, int
            statusesCount, boolean verified, Timestamp createdAt) {
        this.id = id;
        this.description = description;
        this.followerCount = followerCount;
        this.friendsCount = friendsCount;
        this.location = location;
        this.statusesCount = statusesCount;
        this.verified = verified;
        this.createdAt = createdAt;
    }

    public static class Builder {
        private String id = "";
        private String description = "";
        private int followerCount = 0;
        private int friendsCount = 0;
        private String location = "";
        private int statusesCount = 0;
        private boolean verified = false;
        private Timestamp createdAt = null;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder followerCount(int followerCount) {
            this.followerCount = followerCount;
            return this;
        }

        public Builder friendsCount(int friendsCount) {
            this.friendsCount = friendsCount;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder statusesCount(int statusesCount) {
            this.statusesCount = statusesCount;
            return this;
        }

        public Builder verified(boolean verified) {
            this.verified = verified;
            return this;
        }

        public Builder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public User build() {
            return new User(id, description, followerCount, friendsCount, location, statusesCount, verified, createdAt);
        }
    }
}
