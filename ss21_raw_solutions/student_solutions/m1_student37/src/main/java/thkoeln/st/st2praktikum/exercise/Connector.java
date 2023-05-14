package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connector {
    private UUID spacedestination,connectorid;
    private Point location1, location2;

    public Connector(String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate){
        connectorid=UUID.randomUUID();
        sourceCoordinate=sourceCoordinate.substring(1,sourceCoordinate.length()-1);
        destinationCoordinate=destinationCoordinate.substring(1,destinationCoordinate.length()-1);
        location1=new Point(sourceCoordinate);
        location2= new Point(destinationCoordinate);
        spacedestination=destinationSpaceId;
    }

    public Point getLocation1() {
        return location1;
    }

    public UUID getSpacedestination() {
        return spacedestination;
    }

    public Point getLocation2() {
        return location2;
    }

    public UUID getId(){
        return connectorid;
    }
}
