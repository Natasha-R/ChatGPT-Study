package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Portals {

    UUID portalID;
    UUID SourceLvl;
    UUID DestinationLvl;
    UUID TransType;
    int SourceCoordX;
    int SourceCoordY;
    int DestinationCoordX;
    int DestinationCoordY;

    public Portals (UUID type, UUID portalID, UUID SourseLvl, UUID DestinationLvl, Vector2D SourceCoords, Vector2D DestinationCoords) {

        // assign all values to object
        this.portalID = portalID;
        this.SourceLvl = SourseLvl;
        this.DestinationLvl = DestinationLvl;
        this.TransType = type;
        this.SourceCoordX = SourceCoords.getX();
        this.SourceCoordY = SourceCoords.getY();
        this.DestinationCoordX = DestinationCoords.getX();
        this.DestinationCoordY = DestinationCoords.getY();

    }

}