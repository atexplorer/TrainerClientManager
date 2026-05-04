package org.atexplorer.TrainerClientManager.service;

import org.atexplorer.TrainerClientManager.entity.Exercise;
import org.atexplorer.TrainerClientManager.repository.ExerciseLibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class ExerciseLibraryServiceImplTest {

    @Mock
    private ExerciseLibraryRepository exerciseLibraryRepository;

    @InjectMocks
    private ExerciseLibraryServiceImpl exerciseLibraryService;

    private Exercise exercise;

    @BeforeEach
    public void setup(){
        exercise = new Exercise("Barbell Bench Press", "Weight", "Reps", "Chest movement");
        exercise.setId(1L);
    }

    @Test
    public void givenExerciseList_whenGetAllExercises_thenReturnExerciseList(){
        Exercise exercise1 = new Exercise("Pull-ups", "Weight", "Reps", "Back movement");
        exercise.setId(2L);

        given(exerciseLibraryRepository.findAll()).willReturn(List.of(exercise,exercise1));

        List<Exercise> exerciseList = exerciseLibraryService.getAllExercises();

        assertThat(exerciseList).isNotNull();
        assertThat(exerciseList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmptyExerciseList_whenGetAllExercises_thenReturnEmptyList(){
        given(exerciseLibraryRepository.findAll()).willReturn(List.of());

        List<Exercise> exerciseList = exerciseLibraryService.getAllExercises();

        assertThat(exerciseList.size()).isEqualTo(0);
    }

    @Test
    public void givenExerciseName_whenGetExerciseByName_thenReturnExercise(){
        given(exerciseLibraryRepository.getExerciseByExerciseName("Barbell Bench Press")).willReturn(Optional.of(exercise));

        Exercise exercise1 = exerciseLibraryService.getExerciseByExerciseName("Barbell Bench Press");

        assertThat(exercise1).isNotNull();
        assertThat(exercise1.getExerciseName()).isEqualTo("Barbell Bench Press");
        assertThat(exercise1.getMetric1()).isEqualTo("Weight");
        assertThat(exercise1.getMetric2()).isEqualTo("Reps");
    }

    @Test
    public void givenExerciseName_whenGetExerciseByName_thenThrowException(){
        given(exerciseLibraryRepository.getExerciseByExerciseName("Pull-ups")).willReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseLibraryService.getExerciseByExerciseName("Pull-ups"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Could not find exercise: Pull-ups");
    }
}
