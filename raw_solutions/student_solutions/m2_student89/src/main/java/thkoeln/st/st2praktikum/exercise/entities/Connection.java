package thkoeln.st.st2praktikum.exercise.entities;

import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.UUID;

public class Connection {


    private UUID entranceRoomID;
    private Vector2D entranceCoordinates;
    private UUID exitRoomID;
    private Vector2D exitCoordinates;
    public Connection(UUID entranceRoomID, Vector2D entranceCoordinates,UUID exitRoomID, Vector2D exitCoordinates){

      
        this.entranceRoomID=entranceRoomID;
        this.entranceCoordinates=entranceCoordinates;
        this.exitRoomID=exitRoomID;
        this.exitCoordinates=exitCoordinates;


    }

    public Vector2D getEntranceCoordinates() {
        return entranceCoordinates;
    }

    public Vector2D getExitCoordinates() {
        return exitCoordinates;
    }

    public UUID getEntranceRoomID() {
        return entranceRoomID;
    }

    public UUID getExitRoomID() {
        return exitRoomID;
    }
}
