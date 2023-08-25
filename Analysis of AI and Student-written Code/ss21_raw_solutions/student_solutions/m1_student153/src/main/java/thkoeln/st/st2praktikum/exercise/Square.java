package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Square {

    static int countAll = 0;

    int count;
    boolean wallUp;
    boolean wallDown;
    boolean wallLeft;
    boolean wallRight;
    boolean droid = false;
    UUID mapID;


    public Square(UUID mapID, boolean wallUp, boolean wallDown, boolean wallLeft, boolean wallRight) {

        this.mapID = mapID;
        this.wallUp = wallUp;
        this.wallDown = wallDown;
        this.wallLeft = wallLeft;
        this.wallRight = wallRight;


        countAll++;
        this.count = countAll;
    }


}
