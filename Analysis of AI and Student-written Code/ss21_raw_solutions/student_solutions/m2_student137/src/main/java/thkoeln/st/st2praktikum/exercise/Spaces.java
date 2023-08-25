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

    public void createBarrier(Barrier barriers, Spaces activeLevel) {


        // if the Y values are equal the wall stays on a horizontal line. therefore it iterates the x values to create the wall on each field
        if(barriers.getStart().getY().equals(barriers.getEnd().getY())) {
            for (int i = barriers.getStart().getX(); i < barriers.getEnd().getX(); i++) {
                this.singleField[i][barriers.getStart().getY()].borderBottom = true;

                //if the y value is also higher than 0 it will create an upper wall on the field below for consistency
                if (barriers.getStart().getY() > 0) {
                    this.singleField[i][barriers.getStart().getY()- 1].borderTop = true;
                }

            }
        }

        // same as above but for the X values on a vertical line
        if(barriers.getStart().getX().equals(barriers.getEnd().getX())) {
            for(int j = barriers.getStart().getY(); j < barriers.getEnd().getY(); j++) {
                this.singleField[barriers.getStart().getX()][j].borderLeft = true;

                if(barriers.getStart().getX() > 0) {
                    this.singleField[barriers.getStart().getX() - 1][j].borderRight = true;
                }
            }
        }

    }

}