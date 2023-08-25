package thkoeln.st.st2praktikum.exercise.device;

import thkoeln.st.st2praktikum.exercise.command.Commandable;
import thkoeln.st.st2praktikum.exercise.command.MyCommand;
import thkoeln.st.st2praktikum.exercise.environment.Environment;
import thkoeln.st.st2praktikum.exercise.environment.Space;
import thkoeln.st.st2praktikum.exercise.environment.position.EnvironmentPosition;
import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.exception.*;
import thkoeln.st.st2praktikum.exercise.execution.Enterable;
import thkoeln.st.st2praktikum.exercise.execution.Movable;
import thkoeln.st.st2praktikum.exercise.execution.Traversable;
import thkoeln.st.st2praktikum.exercise.execution.movement.Movement;
import thkoeln.st.st2praktikum.exercise.observer.Observable;
import thkoeln.st.st2praktikum.exercise.uuid.UUIDElement;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Device extends UUIDElement implements Commandable, Enterable, Movable, Traversable, Observable<EnvironmentPosition> {

    private final Environment environment;
    protected String name;
    protected EnvironmentPosition currentPosition;
    protected Sensor sensor;

    public Device(Environment environment, String name) {
        this.environment = environment;
        this.name = name;
        this.sensor = new Sensor(environment, this);
    }

    public String getName() {
        return name;
    }

    public EnvironmentPosition getCurrentPosition() {
        return currentPosition;
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
    public void enter(UUID spaceId, Position entryPosition) throws FailedExecutionException {
        EnvironmentPosition entryEnvironmentPosition = EnvironmentPosition.of(environment.getSpace(spaceId), entryPosition);

        if (!sensor.isPositionFree(entryEnvironmentPosition)) {
            throw new BlockedEnteringException(entryEnvironmentPosition);
        }
        changeCurrentPosition(entryEnvironmentPosition);
    }

    @Override
    public void move(Movement movement) throws BlockedMoveException {
        Position positionAfterStep = movement.makeStep(currentPosition);
        if (!Objects.equals(positionAfterStep, currentPosition)) {
            if (!sensor.getFreeNeighborPositions().contains(positionAfterStep)) {
                throw new BlockedMoveException(currentPosition, positionAfterStep);
            }
            changeCurrentPosition(EnvironmentPosition.of(currentPosition.getSpace(), positionAfterStep));
            move(movement);
        }
    }

    @Override
    public void traverse(UUID destinationSpaceId) throws NoConnectedTransitionException, BlockedMoveException, PositionOutOfSpaceException {
        Space destinationSpace = environment.getSpace(destinationSpaceId);
        Optional<EnvironmentPosition> connectedPositionOptional = sensor.getConnectedSpacePositions().stream()
                .filter(position -> destinationSpace.equals(position.getSpace()))
                .findAny();

        if (connectedPositionOptional.isEmpty()) {
            throw new NoConnectedTransitionException(currentPosition, destinationSpace);
        } else if (!sensor.isPositionFree(connectedPositionOptional.get())) {
            throw new BlockedMoveException(currentPosition, connectedPositionOptional.get());
        }
        changeCurrentPosition(connectedPositionOptional.get());
    }

    private void changeCurrentPosition(EnvironmentPosition newPosition) {
        EnvironmentPosition oldPosition = currentPosition;
        currentPosition = newPosition;
        notifyObservers(oldPosition, currentPosition);
    }
}
