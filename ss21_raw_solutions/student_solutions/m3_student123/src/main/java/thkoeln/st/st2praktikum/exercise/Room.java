package thkoeln.st.st2praktikum.exercise;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Room {
    @Id
    private final UUID roomId;

    @ElementCollection
    private List<Cell> room = new ArrayList<>();
    private int roomWidth;
    private int roomHeight;

    protected Room(){
        this.roomId = UUID.randomUUID();
    }
    public Room(int width, int height){
        this.roomId = UUID.randomUUID();
        this.roomHeight = height;
        this.roomWidth = width;
        for(int i = 0; i < roomHeight * roomWidth; i++){
            this.room.add(new Cell());
        }
    }

    public void setBorder(Barrier barrier){
        int startX = barrier.getStart().getX();
        int startY = barrier.getStart().getY();
        int endX = barrier.getEnd().getX();
        int endY = barrier.getEnd().getY();

        if((endX - startX) != 0){
            for( int i = startX; i < endX;i++){
                this.room.get(startY*roomWidth+i).setBorderSouth(true);
                this.room.get((startY-1) *roomWidth+i).setBorderNorth(true);
                /*this.room[i][startY].setBorderSouth(true);
                this.room[i][startY - 1].setBorderNorth(true);*/
            }
        }
        else if((endY - startY) != 0){
            for( int i = startY; i<=endY;i++){
                this.room.get(startX+roomWidth*i).setBorderWest(true);
                this.room.get(startX-1+(roomWidth*i)).setBorderEast(true);
            }
        }
    }
    public Boolean checkIfVectorOutOfBounds(Vector2D vector) {
        if(vector.getX() < 0 || vector.getX() > this.roomWidth) return true;
        if(vector.getY() < 0 || vector.getY() > this.roomHeight) return true;
        return false;
    }

    public int getRoomHeight() { return roomHeight; }
    public int getRoomWidth() { return roomWidth; }
    public Vector2D getMaxRoomCoordinates() {return new Vector2D(roomWidth,roomHeight);}
    public UUID getRoomId() { return roomId; }
    public Cell getRoomCell(int x, int y) { return room.get(y*roomWidth +x); }

}