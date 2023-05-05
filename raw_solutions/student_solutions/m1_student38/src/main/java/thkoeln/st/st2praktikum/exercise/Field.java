package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

class Field{
    Cell[][] map;
    int height;
    int width;
    UUID fieldID;

    public Field(int height, int width, UUID fieldID){
        this.height = height;
        this.width = width;
        this.fieldID = fieldID;
        this.initField();
    }

    void initField(){
        map = new Cell[height][width];
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                map[i][j] = new Cell(false,false,false,false);
            }
        }
    }
}