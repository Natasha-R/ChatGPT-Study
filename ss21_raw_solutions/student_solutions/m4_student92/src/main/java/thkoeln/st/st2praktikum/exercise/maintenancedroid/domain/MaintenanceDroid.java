package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
//@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceDroid {

    @Id
    @Getter
    private UUID id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private UUID spaceShipDeckId;

    @Getter
    @Embedded
    private Vector2D vector2D;

    @Getter
    @Embedded
    private List<Task> tasks;

    public void setVector2D(Vector2D vector2D) {
        this.vector2D = vector2D;
    }
/*
    public List<Task> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        return tasks;
    }*/

    public void addTasks(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    public void removeTasks() {
        tasks.removeAll(tasks);
    }

    public MaintenanceDroid() {
        this.id = UUID.randomUUID();
    }
}
