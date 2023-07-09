package thkoeln.st.st2praktikum.exercise;


import org.springframework.data.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {

    //    private Robot robot = new Robot(7,7,new Map(11,8, new ArrayList<Barrier>(Arrays.asList(
    //                new Barrier(2,6,7,6),    // Horizontal
    //                new Barrier(2,1,10,1),   // Horizontal
    //                new Barrier(2,1,2,6),    // Vertikal
    //                new Barrier(10,1,10,8)   // Vertikal
    //            ))));

    // ================================= INITIALISIERUNG DES ROBOTER + MAP + BARRIEREN =================================
    private Robot robot = new Robot(7,7,new Map(11,8, Arrays.asList(
            new Barrier(2,6,7,6),    // Horizontal
            new Barrier(2,1,10,1),   // Horizontal
            new Barrier(2,1,2,6),    // Vertikal
            new Barrier(10,1,10,8)   // Vertikal
        )));


    @Override
    public String walk(String walkCommandString) {
        return robot.moveRobot(walkCommandString);
    }

}
