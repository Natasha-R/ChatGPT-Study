package thkoeln.st.st2praktikum.exercise;

import org.apache.tomcat.util.log.SystemLogHandler;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
import static java.util.UUID.randomUUID;

//room prevents robot from passing certain coordinates
public class room {
    Integer height;
    Integer width;
    UUID ID = randomUUID();
    //saves obstacles -> an obstacle is a border in one of the four directions of a frame
    //a border is saved as an ArrayList of three Integer Objects
    ArrayList<ArrayList<Integer>> Obstacles = new ArrayList<>();

    room(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }
    //check, if the borders are within this room ?
    //add new obstacle to the ArrayList
    public void addObstacle(String Coordinates){
        int[] CoordinatePair = putCoordinates(Coordinates);
        //get the direction of border and counter variable depending on the variable that changes when placing the border
        int start = 0, finish = 0 , direction = getDirection(CoordinatePair);
        if(direction == -1) return; //there was an issue getting the direction
        if(direction < 2 && direction > -1){ //what changes is the y coordinate
            if(CoordinatePair[1] < CoordinatePair[3]){
                start = CoordinatePair[1]; finish = CoordinatePair[3];
            }else{
                start = CoordinatePair[3]; finish = CoordinatePair[1];
            }
        }else if(direction >= 2){
            if(CoordinatePair[0] < CoordinatePair[2]){
                start = CoordinatePair[0]; finish = CoordinatePair[2];
            }else{
                start = CoordinatePair[2]; finish = CoordinatePair[0];
            }
        }
        /**
            add borders to each frame with the direction of the obstacle
            direction of border -> what now matters is vertical or horizontal
            obstacle goes east/west, but the obstacle will be placed either north or south in the frame
            same for north/south, but with the borders placed east and west in the frames
            (you need the coordinate that changes, the other gives direction)
        */
        for(int i = start; i < finish; i++) {
            ArrayList<Integer> entry1 = new ArrayList<>();
            ArrayList<Integer> entry2 = new ArrayList<>();
            //vertical
            if(direction < 2){
                //adding border in east frame of border
                entry1.add(CoordinatePair[0] - 1);  //x
                entry1.add(i);                      //y
                entry1.add(2);
                //adding border in west frame of border
                entry2.add(CoordinatePair[0]);      //x
                entry2.add(i);                      //y
                entry2.add(3);
            }
            //horizontal
            else{
                //adding border in south frame
                entry1.add(i);                      //x
                entry1.add(CoordinatePair[1]);      //y
                entry1.add(1);
               //adding border in north frame
                entry2.add(i);                      //x
                entry2.add(CoordinatePair[1]-1);    //y
                entry2.add(0);
            }
            Obstacles.add(entry1);
            Obstacles.add(entry2);
        }
    }
    //get direction the border is heading: north 0, south 1, east 2, west 3
    private int getDirection(int[] Coordinates){
        if(Coordinates.length == 4) {
            int x1 = Coordinates[0];
            int y1 = Coordinates[1];
            int x2 = Coordinates[2];
            int y2 = Coordinates[3];
            //north or south
            if(x1 == x2){
                if(y1 < y2) return 0;   //heading north
                else return 1;          //heading south
            }
            //west or east
            else if(y1 == y2){
                if(x1 < x2) return 2;   //heading east
                else return 3;          //heading west
            }
        }
        return -1;
    }
    //get coordinates in list of x1,y1,x2,y2 for creating borders
    private int[] putCoordinates(String Coordinates){
        int[] CoordinatePair = new int[4];
        Coordinates = Coordinates.replace("(","").replace(")","").replace("-",",");
        String[] separatedCords = Coordinates.split(",");
        CoordinatePair[0] = Integer.parseInt(separatedCords[0]);
        CoordinatePair[1] = Integer.parseInt(separatedCords[1]);
        CoordinatePair[2] = Integer.parseInt(separatedCords[2]);
        CoordinatePair[3] = Integer.parseInt(separatedCords[3]);
        return CoordinatePair;
    }
}
