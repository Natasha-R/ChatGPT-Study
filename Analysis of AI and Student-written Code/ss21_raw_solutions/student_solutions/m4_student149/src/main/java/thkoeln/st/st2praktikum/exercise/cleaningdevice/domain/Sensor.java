package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.application.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.Vector2DOutOfSpaceException;
import thkoeln.st.st2praktikum.exercise.observer.Observable;
import thkoeln.st.st2praktikum.exercise.observer.Observer;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceService;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.transition.ConnectedTransition;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
public class Sensor implements Observer {

    @Id
    private UUID uuid = UUID.randomUUID();

    @Transient
    private SpaceService spaceService;

    @Transient
    private CleaningDeviceService cleaningDeviceService;

    @Transient
    private Trackable trackable;

    @Transient
    private List<Vector2D> freeNeighborVector2Ds = new ArrayList<>();

    @Transient
    private List<ConnectedTransition> connectedTransitions = new ArrayList<>();

    public Sensor(Trackable trackable, CleaningDeviceService cleaningDeviceService, SpaceService spaceService, Observable observable) {
        this.trackable = trackable;
        this.cleaningDeviceService = cleaningDeviceService;
        this.spaceService = spaceService;
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
        return cleaningDeviceService.getDeviceVector2Ds(newSpace).stream()
                .noneMatch(otherDeviceVector2D -> Objects.equals(otherDeviceVector2D, vector2D));
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
        connectedTransitions.addAll(spaceService.getConnectedTransitions(trackable.getSpace()).stream()
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
