package org.atexplorer.TrainerClientManager.controller;

import org.atexplorer.TrainerClientManager.entity.Exercise;
import org.atexplorer.TrainerClientManager.service.ExerciseLibraryService;
import org.atexplorer.TrainerClientManager.service.ExerciseLibraryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ExerciseLibraryController.class)
public class ExerciseLibraryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    private ExerciseLibraryServiceImpl exerciseLibraryService;

    private Exercise exercise;

    @BeforeEach
    public void setup(){


        exercise = new Exercise("Barbell Bench Press", "Weight", "Reps", "Chest movement");
        exercise.setId(1L);
    }

    @WithMockUser (value = "spring")
    @Test
    public void givenExercises_whenGetAllExercises_thenReturnJsonArray() throws Exception {
        List<Exercise> exerciseList = Collections.singletonList(exercise);

        given(exerciseLibraryService.getAllExercises()).willReturn(exerciseList);

        mvc.perform(get("/api/exercise/library")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].exerciseName",is("Barbell Bench Press")));

    }

    @WithMockUser(value = "spring")
    @Test
    public void getExerciseByName_exerciseFound() throws Exception{
        given(exerciseLibraryService.getExerciseByExerciseName("Barbell Bench Press")).willReturn(exercise);

        mvc.perform(get("/api/exercise/library/Barbell Bench Press")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exerciseName",is("Barbell Bench Press")));
    }

}
