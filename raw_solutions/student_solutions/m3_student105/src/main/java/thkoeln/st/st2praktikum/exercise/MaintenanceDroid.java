package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.interfaces.*;

import javax.persistence.*;
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
        return this.grid.getId();
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
