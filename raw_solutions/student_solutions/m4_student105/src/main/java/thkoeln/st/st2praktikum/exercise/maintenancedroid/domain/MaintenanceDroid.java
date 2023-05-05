package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Deck;
import thkoeln.st.st2praktikum.exercise.MoveCommand;
import thkoeln.st.st2praktikum.exercise.interfaces.Blocker;
import thkoeln.st.st2praktikum.exercise.interfaces.Moveable;
import thkoeln.st.st2praktikum.exercise.interfaces.Spawnable;
import thkoeln.st.st2praktikum.exercise.interfaces.Transportable;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class MaintenanceDroid extends AbstractEntity implements Moveable, Transportable, Spawnable, Blocker {

    @ManyToOne
    public Deck grid;

    @Embedded
    public Coordinate currentPosition;

    @ElementCollection
    private List<Order> orders = new ArrayList<>();

    private String name;

    public MaintenanceDroid(String name) {
        this.name = name;
    }

    public boolean blocks(MoveCommand moveCommand) {
        return moveCommand.endPosition.equals(currentPosition);
    }

    public void spawn(Deck grid, Coordinate coordinate) {
        this.grid = grid;
        this.currentPosition = coordinate;
        grid.addBlocker(this);
    }

    public void transport(Deck newGrid, Coordinate coordinate, Deck oldGrid) {
        newGrid.removeBlocker(this);
        this.grid = newGrid;
        this.currentPosition = coordinate;
        newGrid.addBlocker(this);
    }

    public UUID getSpaceShipDeckId() {
        if (this.grid != null)
            return this.grid.getId();
        return null;
    }

    public Coordinate getCoordinate() {
        return this.currentPosition;
    }

    public Deck getGrid() {
        return this.grid;
    }

    @Override
    public void setId(UUID id) {
        return;
    }
}
