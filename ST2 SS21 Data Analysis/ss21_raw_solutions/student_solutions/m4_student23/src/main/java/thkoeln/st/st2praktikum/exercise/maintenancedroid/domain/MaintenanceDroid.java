package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Moveable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class MaintenanceDroid implements Moveable {

    @Id
    private UUID uuid;
    @Setter
    private String name;
    private Point point;
    private UUID spaceShipDeckId;

    @ElementCollection
    private final List<Task> receivedTasks = new ArrayList<>();

    protected MaintenanceDroid() {
    }

    public MaintenanceDroid(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public void moveTo(Point position) {
        this.point = position;
    }

    public void spawnOnDeck(UUID deckId, Point position) {
        this.spaceShipDeckId = deckId;
        this.point = position;
    }

    public void addTaskToList (Task task) {
        this.receivedTasks.add(task);
    }

    public void deleteTaskList () {
        this.receivedTasks.clear();
    }
}
