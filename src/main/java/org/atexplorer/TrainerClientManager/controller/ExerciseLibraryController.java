package org.atexplorer.TrainerClientManager.controller;

import org.atexplorer.TrainerClientManager.entity.Exercise;
import org.atexplorer.TrainerClientManager.service.ExerciseLibraryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExerciseLibraryController {

    private final ExerciseLibraryService exerciseLibraryService;

    public ExerciseLibraryController(ExerciseLibraryService exerciseLibraryService) {
        this.exerciseLibraryService = exerciseLibraryService;
    }

    @GetMapping("/api/exercise/library")
    public List<Exercise> getAllExercises(){
        return exerciseLibraryService.getAllExercises();
    }

    @GetMapping("/api/exercise/library/{exerciseName}")
    public Exercise getExerciseByName(@PathVariable String exerciseName){
        return exerciseLibraryService.getExerciseByExerciseName(exerciseName);
    }
}
