package com.capgemini.wsb.fitnesstracker.training.internal;


import com.capgemini.wsb.fitnesstracker.training.model.CreateTrainingRequest;
import com.capgemini.wsb.fitnesstracker.training.model.Training;
import com.capgemini.wsb.fitnesstracker.training.model.TrainingTO;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {


    public static TrainingTO toTraining(Training training) {
        return new TrainingTO(training.getId(),
                    UserMapper.toDto(training.getUser()),
                    training.getStartTime(),
                    training.getEndTime(),
                    training.getActivityType(),
                    training.getDistance(),
                    training.getAverageSpeed() );
    }

    public static Training fromCommand(CreateTrainingRequest command) {
        return Training.builder()
                .startTime(command.getStartTime())
                .endTime(command.getEndTime())
                .activityType(command.getActivityType())
                .distance(command.getDistance())
                .averageSpeed(command.getAverageSpeed())
                .build();
    }

}
