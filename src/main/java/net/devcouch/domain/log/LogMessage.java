package net.devcouch.domain.log;

import java.util.Date;

public class LogMessage {
    public final int id;
    public final int level;
    public final String message;
    public final Date createDate;

    public LogMessage(int id, int level, String message, Date createDate) {
        this.id = id;
        this.level = level;
        this.message = message;
        this.createDate = createDate;
    }

    public static class Builder {
        private int id;
        private int level;
        private String message;
        private Date createDate;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder createDate(Date createDate) {
            this.createDate = createDate;
            return this;
        }


        public LogMessage build() {
            return new LogMessage(id, level, message, createDate);
        }
    }
}
