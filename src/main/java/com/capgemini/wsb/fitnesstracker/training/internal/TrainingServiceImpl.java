package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.exception.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.exception.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.model.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.model.CreateTrainingRequest;
import com.capgemini.wsb.fitnesstracker.training.model.Training;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.model.TrainingTO;
import com.capgemini.wsb.fitnesstracker.user.model.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    @Override
    public TrainingTO getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId)
                .map(TrainingMapper::toTraining)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));
    }

    @Override
    public List<TrainingTO> getTrainings() {
        return trainingRepository.findAll()
                .stream()
                .map(TrainingMapper::toTraining)
                .toList();
    }

    @Override
    public List<TrainingTO> getTrainingsByUserId(Long userId) {
        log.info("Pobrano dane użytkownika o ID " + userId);

        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .map(TrainingMapper::toTraining)
                .toList();
    }

    @Override
    public List<TrainingTO> getTrainingsByActivity(ActivityType activity) {
        log.info("Pobrano treningi według aktywności: " + activity);
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getActivityType().equals(activity))
                .map(TrainingMapper::toTraining)
                .toList();
    }

    @Override
    public List<TrainingTO> getTrainingsByAfterDate(String dateString) {
        final Date parsedDate;
        try {
            // Trying to parse the dateString with 'yyyy-MM-dd' format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            parsedDate = sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date: " + dateString, e);
        }

        log.info("Pobrano wszystkie treningi po zadanej dacie: " + parsedDate);

        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getEndTime().after(parsedDate))
                .map(TrainingMapper::toTraining)
                .toList();
    }

    @Transactional
    public TrainingTO updateTraining(Long trainingId, CreateTrainingRequest command) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));
        log.info("Pobrano właściwy trening");

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException(command.getUserId()));

        if (command.getActivityType() != null) {
            training.setActivityType(command.getActivityType());
        }

        if (command.getDistance() != 0.0) {
            training.setDistance(command.getDistance());
        }

        if (command.getAverageSpeed() != 0.0) {
            training.setAverageSpeed(command.getAverageSpeed());
        }

        if (command.getStartTime() != null) {
            training.setStartTime(command.getStartTime());
        }

        if (command.getEndTime() != null) {
            training.setEndTime(command.getEndTime());
        }

        training.setUser(user);

        log.info("Aktualizacja treningu o ID {}", trainingId);
        return TrainingMapper.toTraining(trainingRepository.save(training));
    }


    @Override
    @Transactional
    public TrainingTO addNewTraining(CreateTrainingRequest training) {
        log.info("Dodanie nowego treningu");
        User user = userRepository.findById(training.getUserId())
                .orElseThrow(() -> new UserNotFoundException(training.getUserId()));

        Training newTraining = new Training(user, training.getStartTime(), training.getEndTime(), training.getActivityType(), training.getDistance(), training.getAverageSpeed());

        log.info("Dodano nowy trening");
        return TrainingMapper.toTraining(trainingRepository.save(newTraining));
    }
}
