package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Deck;

public interface Transportable {

    Deck getGrid();
    Coordinate getCurrentPosition();

    void transport(Deck newGrid, Coordinate coordinate, Deck oldGrid);
}
