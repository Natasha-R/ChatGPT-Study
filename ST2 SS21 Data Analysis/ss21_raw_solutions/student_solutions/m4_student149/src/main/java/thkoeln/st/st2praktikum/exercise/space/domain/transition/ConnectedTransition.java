package thkoeln.st.st2praktikum.exercise.space.domain.transition;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.util.Map;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class ConnectedTransition extends Transition {

    @Transient
    private Map.Entry<Space, Vector2D> fromEntry;

    @Transient
    private Map.Entry<Space, Vector2D> toEntry;

    private Space fromSpace;

    private Space toSpace;

    private TransportTechnology transportTechnology;

    public ConnectedTransition(Space fromSpace, Vector2D fromVector2D, Space toSpace, Vector2D toVector2D, TransportTechnology transportTechnology) {
//        this.fromVector2D = fromVector2D;
        this.fromX = fromVector2D.getX();
        this.fromY = fromVector2D.getY();
        this.fromSpace = fromSpace;
//        this.toVector2D = toVector2D;
        this.toX = toVector2D.getX();
        this.toY = toVector2D.getY();
        this.toSpace = toSpace;
        this.transportTechnology = transportTechnology;
    }

    protected void setFromSpace(Space fromSpace) {
        this.fromSpace = fromSpace;
    }

    @ManyToOne
    public Space getFromSpace() {
        return fromSpace;
    }

    protected void setToSpace(Space toSpace) {
        this.toSpace = toSpace;
    }

    @ManyToOne
    public Space getToSpace() {
        return toSpace;
    }

    @ManyToOne
    public TransportTechnology getTransportTechnology() {
        return transportTechnology;
    }

    public void setTransportTechnology(TransportTechnology transportTechnology) {
        this.transportTechnology = transportTechnology;
    }

    public Map.Entry<Space, Vector2D> getOtherEntry(Space space, Vector2D vector2D) {
        if (!isEntryInvolved(space, vector2D)) {
            return null;
        }
        return isEntryMatching(getFromEntry(), space, vector2D) ? getToEntry() : getFromEntry();
    }

    @Override
    protected void setId(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    @Id
    public UUID getId() {
        return uuid;
    }

    private boolean isEntryInvolved(Space space, Vector2D vector2D) {
        return isEntryMatching(getFromEntry(), space, vector2D) || isEntryMatching(getToEntry(), space, vector2D);
    }

    private boolean isEntryMatching(Map.Entry<Space, Vector2D> entry, Space space, Vector2D vector2D) {
        return entry.getKey().equals(space) && entry.getValue().equals(vector2D);
    }

    @Transient
    private Map.Entry<Space, Vector2D> getFromEntry() {
        if (fromEntry == null) {
            fromEntry = Map.entry(fromSpace, getFromVector2D());
        }
        return fromEntry;
    }

    @Transient
    private Map.Entry<Space, Vector2D> getToEntry() {
        if (toEntry == null) {
            toEntry = Map.entry(toSpace, getToVector2D());
        }
        return toEntry;
    }
}
