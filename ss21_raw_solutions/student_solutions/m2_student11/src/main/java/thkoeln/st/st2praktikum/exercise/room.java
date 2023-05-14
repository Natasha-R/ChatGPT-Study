package thkoeln.st.st2praktikum.exercise;

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
    ArrayList<Obstacle> Obstacles = new ArrayList<>();

    room(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }

    public void addObstacle(String Coordinates){
        Obstacles.add(new Obstacle(Coordinates));
    }
    public void  addObstacle(Obstacle obs){
        Obstacles.add(obs);
    }
}
