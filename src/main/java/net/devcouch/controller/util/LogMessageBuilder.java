package net.devcouch.controller.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static net.devcouch.controller.util.LogMessageBuilder.LogLevel.ERROR;

public class LogMessageBuilder {

    public enum LogLevel {
        ERROR(3), WARNING(2), INFO(1), DEBUG(0);

        private int value;

        private LogLevel(int value) {
            this.value = value;
        }

        public static LogLevel getLevel(int level) {
            switch (level) {
                case 3:
                    return ERROR;
                case 2:
                    return WARNING;
                case 1:
                    return INFO;
                default:
                    return DEBUG;
            }
        }
    }

    private static Random random = new Random();

    private static final List<String> reasons = new ArrayList<>(Arrays.asList(
            "inserting the data",
            "updating the profile",
            "connecting to the database",
            "connecting to the server",
            "connecting to the service",
            "writing to the file system",
            "reading from the file system",
            "opening the file",
            "closing the file",
            "sending data",
            "attaching the file",
            "sending the message",
            "reading the message",
            "receiving the message",
            "validating the data",
            "parsing the document",
            "validating the document",
            "rendering the objects",
            "calculating the score",
            "calculating the sum",
            "calculating the average",
            "calculating the median",
            "measuring the distance",
            "sending the file",
            "inserting the object",
            "mapping the data",
            "closing the connection",
            "commiting the transaction"
    ));

    private static final List<String> errors = new ArrayList<>(Arrays.asList(
            "Error while ",
            "Error when ",
            "An Error occurred while ",
            "An Exception occurred while ",
            "There was an error while ",
            "Ups, error while ",
            "Fatal error when "
    ));

    private static final List<String> warnings = new ArrayList<>(Arrays.asList(
            "Warning while ",
            "There was a warning when ",
            "Not good. Something happend while ",
            "Check this! "
    ));

    private static final List<String> infos = new ArrayList<>(Arrays.asList(
            "Info: ",
            "Note: ",
            "",
            "Entering ",
            "Exiting "
    ));

    private static final List<String> debugs = new ArrayList<>(Arrays.asList(
            "Blah ",
            "Blubb ",
            "Here ",
            "Debug: ",
            "Was here: "
    ));

    public static String buildLogMessage(LogLevel logLevel) {
        String reason = getRandomReason();
        String level;
        switch (logLevel) {
            case ERROR:
                level = getRandomLevel(errors);
                break;
            case WARNING:
                level = getRandomLevel(warnings);
                break;
            case INFO:
                level = getRandomLevel(infos);
                break;
            default:
                level = getRandomLevel(debugs);
                break;
        }
        return level + reason;
    }

    private static String getRandomReason() {
        int index = random.nextInt(reasons.size());
        return reasons.get(index);
    }

    private static String getRandomLevel(List<String> levelStrings) {
        int index = random.nextInt(levelStrings.size());
        return levelStrings.get(index);
    }
}
