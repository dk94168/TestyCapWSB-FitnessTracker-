package com.capgemini.wsb.fitnesstracker.training.internal;



import com.capgemini.wsb.fitnesstracker.exception.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.model.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.model.CreateTrainingRequest;
import com.capgemini.wsb.fitnesstracker.training.model.Training;
import com.capgemini.wsb.fitnesstracker.training.model.TrainingTO;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
@Slf4j
public class TrainingController {
    private final UserRepository userRepository;

    private final TrainingServiceImpl trainingService;
    private final EmailUtils emailUtils;
    private final TrainingRepository trainingRepository;

    @GetMapping
    public List<TrainingTO> getTrainings() {
        return trainingService.getTrainings();
    }

    @GetMapping("/{userId}")
    public List<TrainingTO> getTrainingsByUserId(@PathVariable Long userId){

        return trainingService.getTrainingsByUserId(userId);
    }

    @GetMapping("/activityType")
    public List<TrainingTO> getTrainingsByActivity(@RequestParam("activityType") ActivityType activity){
        return trainingService.getTrainingsByActivity(activity);
    }

    @GetMapping("/finished/{dateString}")
    public List<TrainingTO> getTrainingsByAfterDate(@PathVariable String dateString){
        return trainingService.getTrainingsByAfterDate(dateString);
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingTO> updateTraining(@PathVariable Long trainingId, @RequestBody CreateTrainingRequest updateTraining){
        try {
            TrainingTO trainingTO = trainingService.updateTraining(trainingId, updateTraining);
            return ResponseEntity.ok(trainingTO);
        }
        catch (Exception e){
            log.info("Błąd w trakcie aktualizacji treningu");
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping({"/{trainingId}/completed"})
    public ResponseEntity<String> sendCompletedNotificationEmail(@PathVariable Long trainingId) {
        Training traininig = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));

        emailUtils.sendEmailAboutFinishTraining(traininig, "Congratulations! You have completed the training.");

        return ResponseEntity.ok("Log entry created successfully.");
    }

    @PostMapping
    public ResponseEntity<TrainingTO> addNewTraining(@RequestBody CreateTrainingRequest training) {
        try {
            TrainingTO newTraining = trainingService.addNewTraining(training);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTraining);
        }
        catch (Exception e) {
            log.info("Błąd podczas dodawania nowego treningu");
            return ResponseEntity.badRequest().body(null);
        }
    }

}
