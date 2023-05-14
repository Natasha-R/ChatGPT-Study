package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.command.Commandable;
import thkoeln.st.st2praktikum.exercise.command.MyCommand;
import thkoeln.st.st2praktikum.exercise.device.Sensor;
import thkoeln.st.st2praktikum.exercise.device.Trackable;
import thkoeln.st.st2praktikum.exercise.environment.Environment;
import thkoeln.st.st2praktikum.exercise.environment.Space;
import thkoeln.st.st2praktikum.exercise.exception.*;
import thkoeln.st.st2praktikum.exercise.execution.Enterable;
import thkoeln.st.st2praktikum.exercise.execution.Movable;
import thkoeln.st.st2praktikum.exercise.execution.Traversable;
import thkoeln.st.st2praktikum.exercise.execution.movement.Movement;
import thkoeln.st.st2praktikum.exercise.observer.Observable;
import thkoeln.st.st2praktikum.exercise.observer.Observer;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
public class CleaningDevice implements Trackable, Commandable, Enterable, Movable, Traversable, Observable {

    protected UUID uuid = UUID.randomUUID();

    protected String name;

    @Transient
    private Environment environment;

    protected Vector2D vector2D;

    protected Space space;

    @Transient
    private Sensor sensor;

    @Transient
    private List<Observer> observers = new ArrayList<>();

    public CleaningDevice(Environment environment, String name) {
        this.name = name;
        init(environment);
    }

    public void init(Environment environment) {
        this.environment = environment;
        this.sensor = new Sensor(this, environment, this);
    }

    @Override
    public Boolean executeCommand(MyCommand command) {
        boolean successful = true;

        try {
            command.execute(this);
        } catch (StoppedExecutionException ex) {
        } catch (FailedExecutionException ex) {
            successful = false;
        }
        return successful;
    }

    @Override
    public void enter(UUID spaceId, Vector2D entryVector2D) throws FailedExecutionException {
        Space entrySpace = environment.getSpace(spaceId);
        if (!sensor.isVector2DFree(entrySpace, entryVector2D)) {
            throw new BlockedEnteringException(entrySpace, entryVector2D);
        }
        changeCurrentPosition(entrySpace, entryVector2D);
    }

    @Override
    public void move(Movement movement) throws BlockedMoveException {
        Vector2D positionAfterStep = movement.makeStep(vector2D);
        if (!Objects.equals(positionAfterStep, vector2D)) {
            if (!sensor.getFreeNeighborVector2Ds().contains(positionAfterStep)) {
                throw new BlockedMoveException(vector2D, positionAfterStep);
            }
            changeCurrentPosition(space, positionAfterStep);
            move(movement);
        }
    }

    @Override
    public void traverse(UUID destinationSpaceId) throws NoConnectedTransitionException, BlockedMoveException, Vector2DOutOfSpaceException {
        Map.Entry<Space, Vector2D> connectedPosition = sensor.getConnectedTransitions().stream()
                .map(connectedTransition -> connectedTransition.getOtherEntry(space, vector2D))
                .filter(connectedEntries -> destinationSpaceId.equals(connectedEntries.getKey().getId()))
                .findAny()
                .orElse(null);

        if (connectedPosition == null) {
            throw new NoConnectedTransitionException(vector2D, destinationSpaceId);
        } else if (!sensor.isVector2DFree(connectedPosition.getKey(), connectedPosition.getValue())) {
            throw new BlockedMoveException(vector2D, connectedPosition.getValue());
        }
        changeCurrentPosition(connectedPosition.getKey(), connectedPosition.getValue());
    }

    @Override
    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    @Id
    @Override
    public UUID getId() {
        return uuid;
    }

    @Transient
    public UUID getSpaceId() {
        return space.getId();
    }

    @ManyToOne
    @Override
    public Space getSpace() {
        return space;
    }

    @Override
    public void setSpace(Space space) {
        this.space = space;
    }

    @Embedded
    @Override
    public Vector2D getVector2D() {
        return vector2D;
    }

    @Override
    public void setVector2D(Vector2D vector2D) {
        this.vector2D = vector2D;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update());
    }

    private void changeCurrentPosition(Space newSpace, Vector2D newVector2D) {
        this.vector2D = newVector2D;
        this.space = newSpace;
        environment.updateCleaningDevice(this);
        notifyObservers();
    }
}
