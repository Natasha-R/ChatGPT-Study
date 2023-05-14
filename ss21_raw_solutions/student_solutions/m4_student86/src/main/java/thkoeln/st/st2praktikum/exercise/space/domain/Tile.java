package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.space.domain.Blocking;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Tile {

    @Id
    @Getter
    private UUID id = UUID.randomUUID();

    public enum Direction {
        WEST, EAST, SOUTH, NORTH
    }

    @OneToOne
    private Tile westNeighbor, eastNeighbor, southNeighbor, northNeighbor;

    @Embedded
    private Vector2D coordinate;

    @Transient
    private Blocking occupier = null;

    protected Tile () {}

    public Tile (Vector2D coordinate) {
        this.coordinate = coordinate;
    }

    public Tile getNeighbor (Direction direction) {
        switch (direction) {
            case WEST: return westNeighbor;
            case EAST: return eastNeighbor;
            case SOUTH: return southNeighbor;
            case NORTH: return northNeighbor;
        }
        return null;
    }

    public void setNeighbor(Direction direction, Tile tile) {
        switch (direction) {
            case WEST: westNeighbor = tile; break;
            case EAST: eastNeighbor = tile; break;
            case SOUTH: southNeighbor = tile; break;
            case NORTH: northNeighbor = tile; break;
        }
    }

    public Boolean isOccupied() {
        return occupier != null;
    }
    public void removeOccupier() {
        occupier = null;
    }
}
