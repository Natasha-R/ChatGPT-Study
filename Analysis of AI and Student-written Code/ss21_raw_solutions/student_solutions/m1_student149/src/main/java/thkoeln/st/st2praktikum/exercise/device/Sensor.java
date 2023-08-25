package thkoeln.st.st2praktikum.exercise.device;

import thkoeln.st.st2praktikum.exercise.environment.Environment;
import thkoeln.st.st2praktikum.exercise.environment.position.EnvironmentPosition;
import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.observer.Observable;
import thkoeln.st.st2praktikum.exercise.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sensor implements Observer<EnvironmentPosition> {

    private final Environment environment;
    private EnvironmentPosition devicePosition;

    private final List<Position> freeNeighborPositions = new ArrayList<>();
    private final List<EnvironmentPosition> connectedSpacePositions = new ArrayList<>();

    public Sensor(Environment environment, Observable<EnvironmentPosition> observable) {
        this.environment = environment;
        observable.registerObserver(this);
    }

    public void scanSurroundings() {
        scanConnectedEnvironmentPositions();
        scanFreeNeighborPositions();
    }

    public boolean isPositionFree(Position position) {
        return isPositionFree(EnvironmentPosition.of(devicePosition.getSpace(), position));
    }

    public boolean isPositionFree(EnvironmentPosition environmentPosition) {
        return environment.getDevicePositions(environmentPosition.getSpace()).stream()
                .noneMatch(position -> position.equals(environmentPosition));
    }

    public boolean isOnSpace(Position position) {
        return isOnSpace(EnvironmentPosition.of(devicePosition.getSpace(), position));
    }

    public boolean isOnSpace(EnvironmentPosition position) {
        return position.getSpace().isOnSpace(position);
    }

    public List<Position> getFreeNeighborPositions() {
        return freeNeighborPositions;
    }

    public List<EnvironmentPosition> getConnectedSpacePositions() {
        return connectedSpacePositions;
    }

    @Override
    public void update(EnvironmentPosition oldValue, EnvironmentPosition newValue) {
        devicePosition = newValue;
        scanSurroundings();
    }

    private void scanConnectedEnvironmentPositions() {
        connectedSpacePositions.clear();
        connectedSpacePositions.addAll(environment.getConnectedTransitions(devicePosition.getSpace()).stream()
                .filter(transition -> transition.isInvolved(devicePosition))
                .map(transition -> transition.getOtherPosition(devicePosition))
                .collect(Collectors.toList()));
    }

    private void scanFreeNeighborPositions() {
        freeNeighborPositions.clear();

        int x = devicePosition.getX();
        int y = devicePosition.getY();

        scanPosition(Position.of(x - 1, y));
        scanPosition(Position.of(x + 1, y));
        scanPosition(Position.of(x, y - 1));
        scanPosition(Position.of(x, y + 1));

    }

    private void scanPosition(Position position) {
        if (isPositionFree(position)
                && !isTransitionBlocked(position)
                && isOnSpace(position)) {

            freeNeighborPositions.add(position);
        }
    }

    private boolean isTransitionBlocked(Position destination) {
        return devicePosition.getSpace().getBlockedTransitions().stream()
                .anyMatch(blockedTransition -> blockedTransition.isInvolved(devicePosition) &&
                        blockedTransition.isInvolved(destination));
    }
}
