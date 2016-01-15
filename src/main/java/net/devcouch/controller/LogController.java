package net.devcouch.controller;

import io.crate.shade.com.fasterxml.jackson.core.JsonProcessingException;
import io.crate.shade.com.fasterxml.jackson.databind.ObjectMapper;
import net.devcouch.controller.util.LogMessageBuilder;
import net.devcouch.dao.LogDAO;
import net.devcouch.domain.log.LogMessage;
import net.devcouch.domain.log.GenerateLogsResponse;
import net.devcouch.domain.log.LogSearchRequest;
import net.devcouch.domain.log.QueryLogsResponse;
import net.devcouch.exception.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogDAO logDAO;

    private Random random = new Random();
    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/generate")
    public String generateLogMessage(@RequestParam(value = "amount", required = false, defaultValue = "1000") String
                                             amountStr) {
        int amount = 1000;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            LogMessage.Builder builder = new LogMessage.Builder();
            int id = UUID.randomUUID().hashCode();
            int level = random.nextInt(4);
            String message = LogMessageBuilder.buildLogMessage(LogMessageBuilder.LogLevel.getLevel(level));
            Date date = getRandomDate();
            builder.id(id).level(level).message(message).createDate(date);
            logDAO.save(builder.build());
        }
        long duration = System.currentTimeMillis() - start;
        GenerateLogsResponse response = new GenerateLogsResponse.Builder().message("Generated " + amount + " log " +
                "messages").duration
                (duration).build();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    String queryLogs(@RequestBody String query, @RequestParam(value = "limit", required = false, defaultValue =
            "1000") String
            limitStr) {
        int limit = 0;
        try {
            limit = Integer.parseInt(limitStr);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        try {
            LogSearchRequest logSearchRequest = objectMapper.readValue(query, LogSearchRequest.class);
            long start = System.currentTimeMillis();
            List<LogMessage> logMessages = logDAO.query(logSearchRequest.query, logSearchRequest.level, limit);
            long duration = System.currentTimeMillis() - start;
            QueryLogsResponse response = new QueryLogsResponse.Builder()
                    .messages(logMessages)
                    .duration(duration)
                    .build();
            return objectMapper.writeValueAsString(response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    private Date getRandomDate() {
        int year = 2014 + random.nextInt(2);
        int month = random.nextInt(12);
        int day = 1 + random.nextInt(31);
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }
}
