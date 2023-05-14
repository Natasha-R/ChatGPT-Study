package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.StreamUtils;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.*;

@Entity
@NoArgsConstructor
public class MaintenanceDroid extends AbstractEntity {

    @Embedded
    @Getter
    private Coordinate coordinate;

    @Getter
    @Setter
    private String name;
    private UUID spaceShipDeckId;

    @ElementCollection
    private List<Task> taskHistory = new LinkedList<>();

    public MaintenanceDroid(String name) {
        this.name = name;
    }

    public boolean executeTask(Task task, Iterable<SpaceShipDeck> spaceShipDecks, Iterable<Connection> connections) {
        this.taskHistory.add(task);
        switch (task.getTaskType()) {
            case ENTER:
                return this.enter(task, spaceShipDecks);
            case TRANSPORT:
                return this.transport(task, spaceShipDecks, connections);
            default:
                return this.move(task, spaceShipDecks);
        }
    }

    private boolean enter(Task task, Iterable<SpaceShipDeck> spaceShipDecks) {
        if (this.ourSpaceShipDeck(spaceShipDecks).isPresent()) {
            return false;
        }
        var targetSpaceShipDeck$ = StreamUtils.createStreamFromIterator(spaceShipDecks.iterator())
                .filter(it -> it.getId().equals(task.getGridId()))
                .findAny();
        if (targetSpaceShipDeck$.isEmpty()) {
            return false;
        }
        var targetSpaceShipDeck = targetSpaceShipDeck$.orElseThrow();

        if (targetSpaceShipDeck.isExecutable(task.getTaskType(), this)) {
            var hasEntered = targetSpaceShipDeck.enter(this);
            if (hasEntered) {
                this.coordinate = new Coordinate(0, 0);
                this.spaceShipDeckId = targetSpaceShipDeck.getId();
                return true;
            }
        }
        return false;
    }

    private boolean transport(Task task, Iterable<SpaceShipDeck> spaceShipDecks, Iterable<Connection> connections) {
        if (this.ourSpaceShipDeck(spaceShipDecks).isEmpty()) {
            return false;
        }
        var ourSpaceShipDeck = this.ourSpaceShipDeck(spaceShipDecks).orElseThrow();
        var targetSpaceShipDeck$ = StreamUtils.createStreamFromIterator(spaceShipDecks.iterator())
                .filter(it -> it.getId().equals(task.getGridId()))
                .findAny();
        if (targetSpaceShipDeck$.isEmpty()) {
            return false;
        }
        var targetSpaceShipDeck = targetSpaceShipDeck$.orElseThrow();

        var connection$ = this.findSuitableConnection(connections, ourSpaceShipDeck, targetSpaceShipDeck);
        if (connection$.isEmpty()) {
            return false;
        }
        var connection = connection$.orElseThrow();

        if (targetSpaceShipDeck.isExecutable(connection, this)) {
            targetSpaceShipDeck.enter(this);
            ourSpaceShipDeck.leave(this);
            this.coordinate = connection.getDestinationCoordinate();
            this.spaceShipDeckId = connection.getDestinationSpaceShipDeck().getId();
            return true;
        }
        return false;
    }

    private Optional<Connection> findSuitableConnection(
            Iterable<Connection> connections,
            SpaceShipDeck ourSpaceShipDeck,
            SpaceShipDeck targetSpaceShipDeck) {
        return StreamUtils.createStreamFromIterator(connections.iterator())
                .filter(it -> it.getSourceSpaceShip().equals(ourSpaceShipDeck))
                .filter(it -> it.getSourceCoordinate().equals(this.getCoordinate()))
                .filter(it -> it.getDestinationSpaceShipDeck().equals(targetSpaceShipDeck))
                .findAny();
    }

    private boolean move(Task task, Iterable<SpaceShipDeck> spaceShipDecks) {
        if (this.ourSpaceShipDeck(spaceShipDecks).isEmpty()) {
            return false;
        }
        var ourSpaceShipDeck = this.ourSpaceShipDeck(spaceShipDecks).orElseThrow();
        for (int i = 0; i < task.getNumberOfSteps(); i++) {
            if (ourSpaceShipDeck.isExecutable(task.getTaskType(), this)) {
                this.coordinate = this.coordinate.move(task.getTaskType(), 1);
            } else {
                return false;
            }
        }
        return true;
    }

    private Optional<SpaceShipDeck> ourSpaceShipDeck(Iterable<SpaceShipDeck> spaceShipDecks) {
        return StreamUtils.createStreamFromIterator(spaceShipDecks.iterator())
                .filter(it -> it.getDroids().contains(this))
                .findAny();
    }

    @Deprecated
    public UUID getSpaceShipDeckId() {
        return this.spaceShipDeckId;
    }

    public List<Task> getTaskHistory() {
        return Collections.unmodifiableList(this.taskHistory);
    }

    public void deleteTaskHistory() {
        this.taskHistory = new LinkedList<>();
    }
}
