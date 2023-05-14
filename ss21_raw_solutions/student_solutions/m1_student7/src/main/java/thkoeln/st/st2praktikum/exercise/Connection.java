package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    Room sourceRoom;
    Room destinationRoom;
    int[] sourceCoordinates = new int[2];
    int[] destinationCoordinates  = new int[2];


    UUID id = UUID.randomUUID();

    public Connection(Room givenSourceRoom, String givenSourceCoordinate, Room givenDestinationRoom, String givenDestinationCoordinate){
        sourceRoom = givenSourceRoom;
        destinationRoom = givenDestinationRoom;

        String[] splitGivenSourceCoordinate = givenSourceCoordinate.replace("(","").replace(")","").split(",");
        String[] splitGivenDestinationCoordinate = givenDestinationCoordinate.replace("(","").replace(")","").split(",");

        sourceCoordinates[0] = Integer.parseInt(splitGivenSourceCoordinate[0]);
        sourceCoordinates[1] = Integer.parseInt(splitGivenSourceCoordinate[1]);
        destinationCoordinates[0] = Integer.parseInt(splitGivenDestinationCoordinate[0]);
        destinationCoordinates[1] = Integer.parseInt(splitGivenDestinationCoordinate[1]);
    }

    public UUID getId(){
        return id;
    }

    public int[] getSourceCoordinates() {
        return sourceCoordinates;
    }public int[] getDestinationCoordinates() {
        return destinationCoordinates;
    }public Room getSourceRoom() {
        return sourceRoom;
    }public Room getDestinationRoom() {
        return destinationRoom;
    }
}
