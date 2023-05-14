package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.MoveCommand;
import thkoeln.st.st2praktikum.exercise.interfaces.*;


@NoArgsConstructor
@Getter
@Setter
public class MaintenanceDroid extends AbstractEntity implements Moveable, Transportable, Spawnable, Blocker {

    public Grid grid = null;

    public Coordinate currentPosition = null;

    private String name;

    public MaintenanceDroid(String name) {
        this.name = name;
    }

    public boolean blocks(MoveCommand moveCommand) {
        return moveCommand.endPosition.equals(currentPosition);
    }

    public void spawn(Grid grid, Coordinate coordinate) {
        this.grid = grid;
        this.currentPosition = coordinate;
        grid.addBlocker(this);
    }

    public void transport(Grid newGrid, Coordinate coordinate, Grid oldGrid) {
        newGrid.removeBlocker(this);
        this.grid = newGrid;
        this.currentPosition = coordinate;
        newGrid.addBlocker(this);
    }

    public Grid getGrid() {
        return this.grid;
    }
}
