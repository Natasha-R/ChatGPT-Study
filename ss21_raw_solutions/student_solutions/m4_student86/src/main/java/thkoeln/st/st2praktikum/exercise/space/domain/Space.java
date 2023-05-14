package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract public class Space {
    @Id
    @Getter
    private UUID id = UUID.randomUUID();

    //@Transient
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    protected List<Blocking> blockerList = new ArrayList<>();

    public void addBlocker(Blocking blocker) {
        blocker.buildUp(this);
        blockerList.add(blocker);
    }

    public void removeBlocker(Blocking blocker) {
        blocker.tearDown(this);
        blockerList.remove(blocker);
    }

    public Tile getTile (Vector2D position) {
        return getTile(position.getX(), position.getY());
    }

    abstract public Tile getTile (Integer x, Integer y);
}
