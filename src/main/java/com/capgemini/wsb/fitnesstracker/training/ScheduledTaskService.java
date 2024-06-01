package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.training.internal.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ScheduledTaskService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);

    private final EmailUtils emailUtils;

    @Scheduled(fixedRate = 30000)
    public void logPeriodicMessage() {
        String lastMessage = emailUtils.getLastSentMessage();
        if (!lastMessage.isEmpty()) {
            logger.info("Sent an email message.");
            emailUtils.setLastSentMessage();
        } else {
            logger.info("No email sent yet.");
        }
    }
}