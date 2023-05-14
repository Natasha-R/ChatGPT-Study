package thkoeln.st.st2praktikum.exercise.interfaces;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Deck;

public interface Transportable {

    Deck getGrid();
    Coordinate getCurrentPosition();

    void transport(Deck newGrid, Coordinate coordinate, Deck oldGrid);
}
