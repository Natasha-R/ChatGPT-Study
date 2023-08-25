package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

public class Connection extends Tile {

    @Getter
    private Room destinationRoom;
    @Getter
    private Coordinate destinationPosition;
    @Getter
    UUID id = UUID.randomUUID();

    public Connection(Room destinationRoom, Coordinate destinationPosition ){
        this.destinationRoom = destinationRoom;
        this.destinationPosition = destinationPosition;
    }

    public boolean canUse(UUID destinationID){
        return destinationRoom.getId().equals(destinationID);
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public boolean isConnection() {
        return true;
    }
}
