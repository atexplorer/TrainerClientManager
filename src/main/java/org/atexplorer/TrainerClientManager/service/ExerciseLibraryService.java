package org.atexplorer.TrainerClientManager.service;

import org.atexplorer.TrainerClientManager.entity.Exercise;

import java.util.List;

public interface ExerciseLibraryService {
    List<Exercise> getAllExercises();
    Exercise getExerciseByExerciseName(String exerciseName);
}
