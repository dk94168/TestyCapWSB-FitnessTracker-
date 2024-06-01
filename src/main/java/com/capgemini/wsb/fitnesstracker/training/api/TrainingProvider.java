package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.model.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.model.CreateTrainingRequest;
import com.capgemini.wsb.fitnesstracker.training.model.Training;
import com.capgemini.wsb.fitnesstracker.training.model.TrainingTO;
import com.capgemini.wsb.fitnesstracker.user.model.User;

import java.util.Optional;
import java.util.List;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    TrainingTO getTraining(Long trainingId);

    List<TrainingTO> getTrainings();

    List<TrainingTO> getTrainingsByUserId(Long userId);

    List<TrainingTO> getTrainingsByActivity(ActivityType activity);

    List<TrainingTO> getTrainingsByAfterDate(String dateString);

    TrainingTO addNewTraining(CreateTrainingRequest training);
}
