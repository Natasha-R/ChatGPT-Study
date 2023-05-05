package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    private UUID connectionId;
    private UUID spaceShipDeckId;
    private UUID destinationSpaceShipDeckId;
    private int xSourceCoordinate;
    private int ySourceCoordinate;
    private int xDestinationCoordinate;
    private int yDestinationCoordinate;

    public Connection(UUID spaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate, UUID connectionId) {
        this.connectionId = connectionId;
        this.spaceShipDeckId = spaceShipDeckId;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        String sourceCoordinateSplit[] = sourceCoordinate.split(",");
        sourceCoordinateSplit[0] = sourceCoordinateSplit[0].replace("(", " ").trim();
        sourceCoordinateSplit[1] = sourceCoordinateSplit[1].replace(")", " ").trim();
        String destinationCoordinateSplit[] = destinationCoordinate.split(",");
        destinationCoordinateSplit[0] = destinationCoordinateSplit[0].replace("(", " ").trim();
        destinationCoordinateSplit[1] = destinationCoordinateSplit[1].replace(")", " ").trim();
        this.xSourceCoordinate = Integer.parseInt(sourceCoordinateSplit[0]);
        this.ySourceCoordinate = Integer.parseInt(sourceCoordinateSplit[1]);
        this.xDestinationCoordinate = Integer.parseInt(destinationCoordinateSplit[0]);
        this.yDestinationCoordinate = Integer.parseInt(destinationCoordinateSplit[1]);
    }


    public int getxSourceCoordinate() {
        return xSourceCoordinate;
    }

    public int getySourceCoordinate() {
        return ySourceCoordinate;
    }

    public int getxDestinationCoordinate() {
        return xDestinationCoordinate;
    }

    public int getyDestinationCoordinate() {
        return yDestinationCoordinate;
    }

    public UUID getDestinationSpaceShipDeckId() {
        return destinationSpaceShipDeckId;
    }
}
