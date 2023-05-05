package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Field implements FieldBuilder {

    private final int xCoordinateSize;
    private final int yCoordinateSize;
    private final UUID uuid;
    private Room[][] fieldWithRooms;


    public Field(int xCoordinateSize, int yCoordinateSize){
        this.xCoordinateSize = xCoordinateSize;
        this.yCoordinateSize = yCoordinateSize;
        fieldWithRooms = new Room[xCoordinateSize][yCoordinateSize];;
        uuid = UUID.randomUUID();
        createField();
        determineNeighbor();


    }

    public UUID getUuid() {
        return uuid;
    }

    public Room getRoom (int xCoordinate, int yCoordinate){
        return fieldWithRooms[xCoordinate][yCoordinate];
    }

    public Room[][] getFieldWithRooms() {
        return fieldWithRooms;
    }

    public void createField() {
        for (int i = 0; i < xCoordinateSize; i++) {
            for (int j = 0; j < yCoordinateSize; j++) {
                fieldWithRooms[i][j] = new Room(i, j,this);
            }
        }
    }

    public void determineNeighbor(){

        for(int i = 0; i < fieldWithRooms.length; i++){
            for(int j = 0; j < fieldWithRooms[i].length; j++){

                if(j+1 < fieldWithRooms[i].length) {
                    fieldWithRooms[i][j].setNorth(fieldWithRooms[i][j + 1]);
                }
                if(i+1 < fieldWithRooms.length){
                    fieldWithRooms[i][j].setEast(fieldWithRooms[i+1][j]);
                }
                if(j-1 >= 0 ){
                    fieldWithRooms[i][j].setSouth(fieldWithRooms[i][j-1]);
                }

                if(i-1 >= 0 && i-1 < fieldWithRooms[i].length ){
                    fieldWithRooms[i][j].setWest(fieldWithRooms[i-1][j]);
                }
            }
        }
    }

    //Wall: (2,3) - (2,8)
    public void placeWall(Wall wall) {

        if (wall != null) {
            if (wall.isVertically()) {
                for (int i = wall.getyStartPosition(); i < wall.getyEndPosition(); i++) {
                    fieldWithRooms[wall.getxStartPosition() - 1][i].setEast(null);
                    fieldWithRooms[wall.getxStartPosition()][i].setWest(null);
                }
                //horizontally wall
            } else {
                for (int i = wall.getxStartPosition(); i < wall.getxEndPosition(); i++) {
                    fieldWithRooms[i][wall.getyStartPosition()].setSouth(null); //(2,3),(3,3)...
                    fieldWithRooms[i][wall.getyStartPosition() - 1].setNorth(null); //(2,2),(3,2)...
                }
            }
        }else{
            System.out.println("wall is null");
        }
    }

}