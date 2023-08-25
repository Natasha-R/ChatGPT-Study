package thkoeln.st.st2praktikum.exercise.device;

import thkoeln.st.st2praktikum.exercise.environment.Environment;
import thkoeln.st.st2praktikum.exercise.environment.position.EnvironmentPosition;
import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.exception.PositionOutOfSpaceException;
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

    public boolean isPositionFree(EnvironmentPosition environmentPosition) {
        return environment.getDevicePositions(environmentPosition.getSpace()).stream()
                .noneMatch(position -> position.equals(environmentPosition));
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

        scanPosition(x - 1, y);
        scanPosition(x + 1, y);
        scanPosition(x, y - 1);
        scanPosition(x, y + 1);

    }

    private void scanPosition(int x, int y) {
        try {
            EnvironmentPosition position = EnvironmentPosition.of(devicePosition.getSpace(), x, y);
            if (isPositionFree(position) && !isTransitionBlocked(position)) {
                freeNeighborPositions.add(position);
            }
        } catch (PositionOutOfSpaceException ignore) {
        }
    }

    private boolean isTransitionBlocked(Position destination) {
        return devicePosition.getSpace().getBlockedTransitions().stream()
                .anyMatch(blockedTransition -> blockedTransition.isInvolved(devicePosition) &&
                        blockedTransition.isInvolved(destination));
    }
}
