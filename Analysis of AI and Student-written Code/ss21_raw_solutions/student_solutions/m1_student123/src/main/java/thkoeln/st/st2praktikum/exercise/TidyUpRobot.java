package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TidyUpRobot {

    private final String robotName;
    private final UUID tidyUpRobotID;
    private UUID roomID;
    private final Coordinate coordinates;

    public TidyUpRobot( String name){
        this.tidyUpRobotID = UUID.randomUUID();
        this.robotName = name;
        this.coordinates = new Coordinate();
    }

    /**
     * This method is responsible for the traverse/teleport of a Tidy Up Robot
     * @param connectionHashMap HashMap of all Connections
     * @param destinationRoomId the Room ID, to which the Robot should be traversed
     * @return if the traversing of the Tidy Up Robot to the desired Room was successful
     */
    public Boolean traverseRobot(HashMap<UUID, Connection> connectionHashMap, UUID destinationRoomId) {
        for (Map.Entry<UUID, Connection> connectionEntry : connectionHashMap.entrySet()) {
            if (connectionEntry.getValue().getSourceRoomId().compareTo(this.getRoomID()) == 0 &&
                    connectionEntry.getValue().getDestinationRoomId().compareTo(destinationRoomId) == 0) {

                if (this.getCoordinateString().equals(connectionEntry.getValue().getSourceCoordinate())) {
                    Coordinate newCoordinates = Coordinate.fromString(connectionEntry.getValue().getDestinationCoordinate());
                    this.traverseToOtherRoom(newCoordinates, connectionEntry.getValue().getDestinationRoomId());
                    return true;
                } else return false;
            }
        }

        return false;
    }
    /**
     * This method is responsible for the placing of a tidy up robot in a room
     * @param roomHashMap collection of all rooms
     * @param tidyUpRobotHashMap collection of all tidy up robots
     * @param destinationRoomID the ID of the room where the robot should be placed
     * @return if the placing of the robot was possible or not
     */
    public Boolean placeRobot(HashMap<UUID, Room> roomHashMap, HashMap<UUID, TidyUpRobot> tidyUpRobotHashMap, UUID destinationRoomID) {
        if (roomHashMap.get(destinationRoomID) == null)
            throw new IllegalArgumentException("This destination room doesn't exist: " + destinationRoomID);

        for (Map.Entry<UUID, TidyUpRobot> tidyUpRobotEntry : tidyUpRobotHashMap.entrySet()) {
            if (tidyUpRobotEntry.getValue().getRoomID() != null) {
                if (tidyUpRobotEntry.getValue().getRoomID().compareTo(destinationRoomID) == 0 &&
                        tidyUpRobotEntry.getValue().getCoordinateString().equals("(0,0)")) {
                    return false;
                }
            }
        }

        this.placeInRoom(destinationRoomID);
        return true;
    }

    public boolean moveNorth(Room room, int moveAmount){
        for(int i = moveAmount; i > 0; i--){
            if(!room.getRoomCell(this.getX(),this.getY()).isBorderNorth() && this.getY() +1 < room.getRoomHeight() ){
                this.increaseY();
            }else break;
        }
        return true;
    }

    public boolean moveSouth(Room room, int moveAmount){
        for(int i = moveAmount; i > 0; i--){
            if(!room.getRoomCell(this.getX(),this.getY()).isBorderSouth() && this.getY() - 1 >=0) {
                this.decreaseY();
            } else break;
        }
        return true;
    }

    public boolean moveEast(Room room, int moveAmount){
        for(int i = moveAmount; i > 0; i--){
            if(!room.getRoomCell(this.getX(),this.getY()).isBorderEast() && this.getX() + 1 < room.getRoomWidth()) {
                this.increaseX();
            } else break;
        }
        return true;
    }

    public boolean moveWest(Room room, int moveAmount){
        for(int i = moveAmount; i > 0; i--){
            if(!room.getRoomCell(this.getX(),this.getY()).isBorderWest() && this.getX() - 1 >= 0) {
                this.decreaseX();
            } else break;
        }
        return true;
    }

    public String getCoordinateString(){ return this.coordinates.toString(); }
    public int getX(){ return this.coordinates.getX();}
    public int getY(){ return this.coordinates.getY();}

    public void setCoordinates(int x, int y){ this.coordinates.setNewCoordinates(x,y);}
    public void setCoordinates(Coordinate newCoordinate ) {this.coordinates.setNewCoordinates(newCoordinate);}

    public void increaseX(){ this.coordinates.increaseX(); }
    public void increaseY(){ this.coordinates.increaseY(); }
    public void decreaseX(){ this.coordinates.decreaseX(); }
    public void decreaseY(){ this.coordinates.decreaseY(); }

    public UUID getTidyUpRobotID() { return this.tidyUpRobotID; }
    public String getRobotName() { return this.robotName; }

    public UUID getRoomID(){ return this.roomID;}
    public void placeInRoom( UUID roomID) {this.roomID = roomID;}

    public void traverseToOtherRoom( int x, int y, UUID roomID) {
        this.setCoordinates(x, y);
        this.roomID = roomID;
    }

    public void traverseToOtherRoom( Coordinate newCoordinate,  UUID roomID) {
        this.setCoordinates(newCoordinate);
        this.roomID = roomID;
    }
}
