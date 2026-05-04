package org.atexplorer.TrainerClientManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String exerciseName;
    private String metric1;
    private String metric2;
    private String description;

    public Exercise(String exerciseName, String metric1, String metric2, String description) {
        this.exerciseName = exerciseName;
        this.metric1 = metric1;
        this.metric2 = metric2;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getMetric1() {
        return metric1;
    }

    public void setMetric1(String metric1) {
        this.metric1 = metric1;
    }

    public String getMetric2() {
        return metric2;
    }

    public void setMetric2(String metric2) {
        this.metric2 = metric2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
