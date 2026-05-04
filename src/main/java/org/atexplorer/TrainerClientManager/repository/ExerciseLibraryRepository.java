package org.atexplorer.TrainerClientManager.repository;


import org.atexplorer.TrainerClientManager.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseLibraryRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> getExerciseByExerciseName(String exerciseName);
}
