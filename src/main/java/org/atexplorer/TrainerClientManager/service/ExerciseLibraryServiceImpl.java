package org.atexplorer.TrainerClientManager.service;

import org.atexplorer.TrainerClientManager.entity.Exercise;
import org.atexplorer.TrainerClientManager.repository.ExerciseLibraryRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class ExerciseLibraryServiceImpl implements ExerciseLibraryService{

    private final ExerciseLibraryRepository exerciseLibraryRepository;

    public ExerciseLibraryServiceImpl(ExerciseLibraryRepository exerciseLibraryRepository) {
        this.exerciseLibraryRepository = exerciseLibraryRepository;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseLibraryRepository.findAll();
    }

    @Override
    public Exercise getExerciseByExerciseName(String exerciseName) {
        return exerciseLibraryRepository.getExerciseByExerciseName(exerciseName).orElseThrow(() -> new NoSuchElementException("Could not find exercise: " + exerciseName ));
    }
}
