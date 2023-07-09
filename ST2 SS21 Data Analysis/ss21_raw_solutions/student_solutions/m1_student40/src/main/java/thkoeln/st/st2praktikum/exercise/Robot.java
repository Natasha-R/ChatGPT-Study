package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

public class Robot extends Movable {

    @Getter
    private UUID currentRoomID = null;
    @Getter
    private String name;

    public boolean switchRoom(UUID newRoomID, Coordinate destination) {
        if (this.currentRoomID != null) {
            this.currentRoomID = newRoomID;
            this.position.changeCoordinates(destination.getX(), destination.getY());
            return true;
        }

        return false;
    }

    public boolean isInRoom() {
        return currentRoomID != null;
    }

    public Robot(String name) {
        this.position.changeCoordinates(0,0);
        this.name = name;
    }

    public boolean placeInRoom(UUID roomID) {
        if (this.currentRoomID == null) {
            this.currentRoomID = roomID;
            return true;
        } else {
            return false;
        }
    }
}
