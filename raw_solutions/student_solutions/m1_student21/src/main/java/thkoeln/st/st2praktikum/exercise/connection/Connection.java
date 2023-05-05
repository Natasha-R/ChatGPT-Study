package thkoeln.st.st2praktikum.exercise.connection;

import java.util.UUID;

public class Connection {

    private UUID id = UUID.randomUUID();

    private UUID sourceRoom;
    private UUID destRoom;
    private int sourceX;
    private int sourceY;
    private int destinationX;
    private int destinationY;

    public Connection(UUID sourceRoom,int sourceX,int sourceY , UUID destRoom, int destX, int destY){
        this.sourceRoom = sourceRoom;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.destRoom = destRoom;
        this.destinationX = destX;
        this.destinationY = destY;
    }

    public UUID getSourceRoom() {
        return sourceRoom;
    }

    public int getSourceX() {
        return sourceX;
    }

    public int getSourceY() {
        return sourceY;
    }

    public UUID getDestinationRoom() {
        return destRoom;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    public UUID getId(){
        return this.id;
    }
}
