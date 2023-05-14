package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Portal {

    UUID portalID;
    UUID startMapID;
    UUID endMapID;
    int startCoordX;
    int startCoordY;
    int endCoordX;
    int endCoordY;


    public Portal(UUID portalID, UUID sourceSpaceShipDeckId, UUID destinationSpaceShipDeckId, String sourceCoordinate,String destinationCoordinate){
        this.portalID=portalID;
        this.startMapID=sourceSpaceShipDeckId;
        this.endMapID=destinationSpaceShipDeckId;
        this.startCoordX=Integer.parseInt(sourceCoordinate.substring(1,2));
        this.startCoordY=Integer.parseInt(sourceCoordinate.substring(3,4));
        this.endCoordX= Integer.parseInt(destinationCoordinate.substring(1,2));
        this.endCoordY= Integer.parseInt(destinationCoordinate.substring(3,4));




    }
}
