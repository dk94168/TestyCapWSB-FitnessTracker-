package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.model.Training;
import com.capgemini.wsb.fitnesstracker.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Slf4j
public class EmailUtils {

    private String lastSentMessage = "";

    public void sendEmailAboutFinishTraining(Training training,String additionalText) {
        Duration duration = Duration.between(training.getStartTime().toInstant(), training.getEndTime().toInstant());

        String message = String.format("Training completed by: %s\nActivity: %s\nDuration: %s\n%s",
                training.getUser().getEmail(), training.getActivityType(), duration,  additionalText);

        log.info(message);
        lastSentMessage = message;
    }

    public String getLastSentMessage() {
        return lastSentMessage;
    }
}
