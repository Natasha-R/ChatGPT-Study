package thkoeln.st.st2praktikum.exercise.device;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Vector2D;
import thkoeln.st.st2praktikum.exercise.environment.Environment;
import thkoeln.st.st2praktikum.exercise.environment.Space;
import thkoeln.st.st2praktikum.exercise.environment.transition.ConnectedTransition;
import thkoeln.st.st2praktikum.exercise.exception.Vector2DOutOfSpaceException;
import thkoeln.st.st2praktikum.exercise.observer.Observable;
import thkoeln.st.st2praktikum.exercise.observer.Observer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
public class Sensor implements Observer {

    @Id
    private UUID uuid = UUID.randomUUID();

    @Transient
    private Environment spaceController;

    @Transient
    private Trackable trackable;

    @Transient
    private List<Vector2D> freeNeighborVector2Ds = new ArrayList<>();

    @Transient
    private List<ConnectedTransition> connectedTransitions = new ArrayList<>();

    public Sensor(Trackable trackable, Environment environment, Observable observable) {
        this.trackable = trackable;
        this.spaceController = environment;
        observable.registerObserver(this);

        if (trackable.getSpace() != null && trackable.getVector2D() != null) {
            scanSurroundings();
        }
    }

    public void scanSurroundings() {
        scanConnectedVector2Ds();
        scanFreeNeighborVector2Ds();
    }

    public boolean isVector2DFree(Space newSpace, Vector2D vector2D) {
        return spaceController.getDeviceVector2Ds(newSpace).stream()
                .noneMatch(otherDeviceVector2D -> otherDeviceVector2D.equals(vector2D));
    }

    public List<Vector2D> getFreeNeighborVector2Ds() {
        return freeNeighborVector2Ds;
    }

    public List<ConnectedTransition> getConnectedTransitions() {
        return connectedTransitions;
    }

    @Override
    public void update() {
        scanSurroundings();
    }

    private void scanConnectedVector2Ds() {
        connectedTransitions.clear();
        connectedTransitions.addAll(spaceController.getConnectedTransitions(trackable.getSpace()).stream()
                .filter(transition -> transition.isInvolved(trackable.getVector2D()))
                .collect(Collectors.toList()));
    }

    private void scanFreeNeighborVector2Ds() {
        freeNeighborVector2Ds.clear();

        int x = trackable.getVector2D().getX();
        int y = trackable.getVector2D().getY();

        scanVector2D(x - 1, y);
        scanVector2D(x + 1, y);
        scanVector2D(x, y - 1);
        scanVector2D(x, y + 1);

    }

    private void scanVector2D(int x, int y) {
        try {
            Vector2D vector2D = new Vector2D(x, y);
            if (isVector2DFree(trackable.getSpace(), vector2D) && !isTransitionBlocked(vector2D)) {
                freeNeighborVector2Ds.add(vector2D);
            }
        } catch (Vector2DOutOfSpaceException ignore) {
        }
    }

    private boolean isTransitionBlocked(Vector2D destination) {
        return trackable.getSpace().getBlockedTransitions().stream()
                .anyMatch(blockedTransition -> blockedTransition.isInvolved(trackable.getVector2D()) &&
                        blockedTransition.isInvolved(destination));
    }
}
