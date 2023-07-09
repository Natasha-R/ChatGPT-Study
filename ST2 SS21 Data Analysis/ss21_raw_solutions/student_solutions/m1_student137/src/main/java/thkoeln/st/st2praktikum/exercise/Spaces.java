package thkoeln.st.st2praktikum.exercise;

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;

import java.util.UUID;
import java.util.ArrayList;

public class Spaces {

    UUID id;
    int height;
    int width;
    field[][] singleField;

    // constructor
    public  Spaces (Integer height, Integer width, UUID name) {

        this.height = height;
        this.width = width;
        this.id = name;

        field[][] singleField = new field[height][width];
        for (int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                field currentField = new field (name);
                singleField[i][j] = currentField;
            }
        }
        this.singleField = singleField;

    }

    public void createOuttaWall(Spaces space) {

        // create outter level borders

        for(int i = 0; i < space.height; i++) {
            space.singleField[0][i].borderLeft = true;
        }

        for(int i = 0; i < space.height; i++) {
            space.singleField[width-1][i].borderRight = true;
        }

        for(int i = 0; i < space.width; i++) {
            space.singleField[i][0].borderBottom = true;
        }

        for(int i = 0; i < space.width; i++) {
            space.singleField[i][height-1].borderTop = true;
        }
    }

    public void createBarrier(ArrayList<Integer> barriers, Spaces activeLevel) {

        ArrayList<Integer> wallPositions = barriers;

        if(wallPositions.get(1) == wallPositions.get(3)) { // wall is going left/right
            for (int i = wallPositions.get(0); i < wallPositions.get(2); i++) {
                this.singleField[i][wallPositions.get(1)].borderBottom = true;

                if (wallPositions.get(1) > 0) {
                    this.singleField[i][wallPositions.get(1)- 1].borderTop = true;
                }

            }
        }

        if(wallPositions.get(0) == wallPositions.get(2)) { // wall is going up/down
            for(int j = wallPositions.get(1); j < wallPositions.get(3); j++) {
                this.singleField[wallPositions.get(0)][j].borderLeft = true;

                if(wallPositions.get(0) > 0) {
                    this.singleField[wallPositions.get(0) - 1][j].borderRight = true;
                }
            }
        }

    }


}