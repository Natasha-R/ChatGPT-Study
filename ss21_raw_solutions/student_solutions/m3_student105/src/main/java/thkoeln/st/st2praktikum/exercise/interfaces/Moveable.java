package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Deck;

public interface Moveable {
    Coordinate getCurrentPosition();
    void setCurrentPosition(Coordinate coordinate);
    Deck getGrid();
}
