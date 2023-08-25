package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

public class Connection extends Entity {
    @Getter
    private UUID sourceRoomID;
    @Getter
    private UUID destinationRoomID;

    public boolean canUse(UUID currentRoomID) {
        return currentRoomID.equals(this.sourceRoomID);
    }

    public Connection(UUID sourceID, UUID destinationID) {
        this.sourceRoomID = sourceID;
        this.destinationRoomID = destinationID;
    }
}
