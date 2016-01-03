package net.devcouch.domain.log;

import java.util.ArrayList;
import java.util.List;

public class QueryLogsResponse {

    public final List<LogMessage> logMessages;
    public final long duration;

    public QueryLogsResponse(List<LogMessage> logMessages, long duration) {
        this.logMessages = logMessages;
        this.duration = duration;
    }

    public static class Builder {
        private List<LogMessage> messages = new ArrayList<>();
        private long duration = 0;

        public Builder messages(List<LogMessage> messages) {
            this.messages = messages;
            return this;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public QueryLogsResponse build() {
            return new QueryLogsResponse(messages, duration);
        }
    }
}
