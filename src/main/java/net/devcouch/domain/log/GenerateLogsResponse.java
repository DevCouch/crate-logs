package net.devcouch.domain.log;

public class GenerateLogsResponse {

    public final String message;
    public final long duration;

    public GenerateLogsResponse(String message, long duration) {
        this.message = message;
        this.duration = duration;
    }

    public static class Builder {
        private String message = "";
        private long duration = 0;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public GenerateLogsResponse build() {
            return new GenerateLogsResponse(message, duration);
        }
    }
}
