package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.entities.Coordinate;

public interface Transporter {
    Coordinate getSourceCoordinate();
    Coordinate getDestinationCoordinate();
    Grid getSource();
    Grid getDestination();
}
