package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

class Connection extends TidyUpRobotService implements IdReturnableInterface {

    private UUID uuidConnection;
    private Coordinate sourceCoordinate;
    private UUID sourceRoomId;
    private UUID destinationRoomId;
    private Coordinate destinationCoordinate;

    public Connection(UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate){
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
        this.uuidConnection = UUID.randomUUID();
    }


    @Override
    public UUID returnUUID() {
        return this.uuidConnection;
    }


    public Coordinate getSourceCoordinate(){
        return this.sourceCoordinate;
    }
    public void setSourceCoordinate(Coordinate newCoordinate){
        sourceCoordinate=newCoordinate;
    }

    public Coordinate getDestinationCoordinate(){
        return destinationCoordinate;
    }
    public void setDestinationCoordinate(Coordinate newCoordinate){
        destinationCoordinate=newCoordinate;
    }

    public UUID getDestinationRoomId(){
        return destinationRoomId;
    }
    public void setDestinationRoomId(UUID id){
        destinationRoomId=id;
    }

    public String getSourceCoordinatAsString(){
        return sourceCoordinate.toString();
    }
    public String getDestinationCoordinatAsString(){
        return destinationCoordinate.toString();
    }


    @Override
    public String toString() {
        return "(" + sourceCoordinate.getX() + "," + sourceCoordinate.getY() + ")";
    }

}