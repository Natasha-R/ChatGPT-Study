package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {


    private UUID entranceRoomID;
    private String entranceCoordinates;
    private UUID exitRoomID;
    private String exitCoordinates;
    public Connection(UUID entranceRoomID, String entranceCoordinates,UUID exitRoomID, String exitCoordinates){
        this.entranceRoomID=entranceRoomID;
        this.entranceCoordinates=entranceCoordinates;
        this.exitRoomID=exitRoomID;
        this.exitCoordinates=exitCoordinates;
    }

    public String getEntranceCoordinates() {
        return entranceCoordinates;
    }

    public String getExitCoordinates() {
        return exitCoordinates;
    }

    public UUID getEntranceRoomID() {
        return entranceRoomID;
    }

    public UUID getExitRoomID() {
        return exitRoomID;
    }
}
