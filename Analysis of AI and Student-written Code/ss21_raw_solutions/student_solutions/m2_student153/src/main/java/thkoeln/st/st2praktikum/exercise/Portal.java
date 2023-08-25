package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Portal {

    UUID portalID;
    UUID startMapID;
    UUID endMapID;
    Vector2D start;
    Vector2D ende;


    public Portal(UUID portalID, UUID sourceSpaceShipDeckId, UUID destinationSpaceShipDeckId, Vector2D sourceCoordinate,Vector2D destinationCoordinate){
        this.portalID=portalID;
        this.startMapID=sourceSpaceShipDeckId;
        this.endMapID=destinationSpaceShipDeckId;
       this.start=sourceCoordinate;
       this.ende=destinationCoordinate;

    }
}

