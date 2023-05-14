package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Room {

    private final Cell[][] room;
    private final int roomWidth;
    private final int roomHeight;
    private final UUID roomId;

    public Room(int width, int height){
        this.roomId = UUID.randomUUID();
        this.roomHeight = height;
        this.roomWidth = width;
        this.room = new Cell[this.roomWidth][this.roomHeight];
        for(int i = 0; i < this.roomWidth;i++){
            for (int j = 0; j < this.roomHeight; j++){
                this.room[i][j] = new Cell();
            }
        }
    }

    public void setBorder(String barrierString){
        String[] stringCoordinates = barrierString.replaceAll("[^?0-9]+", " ").trim().split(" ");
        int startX = Integer.parseInt(stringCoordinates[0]);
        int startY = Integer.parseInt(stringCoordinates[1]);
        int endX = Integer.parseInt(stringCoordinates[2]);
        int endY = Integer.parseInt(stringCoordinates[3]);

        if((endX - startX) != 0){
            for( int i = startX; i < endX;i++){
                this.room[i][startY].setBorderSouth(true);
                this.room[i][startY - 1].setBorderNorth(true);
            }
        }
        else if((endY - startY) != 0){
            for( int i = startY; i<=endY;i++){
                this.room[startX][i].setBorderWest(true);
                this.room[startX - 1][i].setBorderEast(true);
            }
        }
    }

    public int getRoomHeight() { return roomHeight; }
    public int getRoomWidth() { return roomWidth; }
    public UUID getRoomId() { return roomId; }
    public Cell getRoomCell(int x, int y) { return room[x][y]; }

}
