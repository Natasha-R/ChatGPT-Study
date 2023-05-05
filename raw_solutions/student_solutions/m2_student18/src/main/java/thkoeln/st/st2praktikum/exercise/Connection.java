package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

public class Connection{
    @Getter
    private final Vector2D sourceRoomCoordinates;
    @Getter
    private final Vector2D destinationRoomCoordinates;

    public Connection(Vector2D sourceRoomCoordinates, Vector2D destinationRoomCoordinates){
        this.sourceRoomCoordinates = sourceRoomCoordinates;
        this.destinationRoomCoordinates = destinationRoomCoordinates;
    }
}
