package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection implements ConnectionInterface{
    UUID id;

    UUID sourceSpaceShipDeckId;
    String sourceCoordinate;

    UUID destinationSpaceShipDeckId;
    String destinationCoordinate;
}
