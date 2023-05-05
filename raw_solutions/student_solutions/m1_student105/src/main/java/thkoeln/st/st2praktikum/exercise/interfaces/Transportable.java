package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.entities.Coordinate;

public interface Transportable {

    Grid getGrid();
    Coordinate getCurrentPosition();

    void transport(Grid newGrid, Coordinate coordinate, Grid oldGrid);
}
