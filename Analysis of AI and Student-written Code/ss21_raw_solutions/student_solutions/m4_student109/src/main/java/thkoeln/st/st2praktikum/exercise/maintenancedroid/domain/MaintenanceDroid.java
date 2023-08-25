package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaintenanceDroid {

    @ElementCollection(targetClass = Task.class, fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();
    
    private String name;
    @Id
    private UUID maintenanceDroidsId = UUID.randomUUID();


    private UUID spaceShipDeckId;

    @Embedded
    private Vector2D vector2D;

    public MaintenanceDroid(String name, UUID maintenanceDroidsId) {
        this.name = name;
        this.maintenanceDroidsId = maintenanceDroidsId;
        this.tasks = new ArrayList<>();
    }

    public MaintenanceDroid(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }



    public void addTask(Task task){
        tasks.add(task);
    }

}
