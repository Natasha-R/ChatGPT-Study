package thkoeln.st.st2praktikum.exercise.room;

import java.util.*;

public class Room {
    private UUID id = UUID.randomUUID();


    private ArrayList<AbstractObstacle> verticalObstacles;
    private ArrayList<AbstractObstacle> horizontalObstacles;

    // when moving east/west i have to check if the point above me is also part of the obstacle
    // when moving north/south i have to check if the point right of me is also part of the obstacle
    // including the directions down here limits the movement to these four basic steps but i would consider this a reasonable design choice
    // movements such as "go diagonal" should be implemented somewhere else using these four basic steps
    public boolean canIGoThere(String direction, int x,int y){
        if(direction.matches("we|ea")){
            if(direction.matches("ea")){
                return !verticalObstacles.stream().anyMatch(obstacle -> obstacle.collision(x+1,y));
            }
            return !verticalObstacles.stream().anyMatch(obstacle -> obstacle.collision(x,y));
        }
        if(direction.matches("no|so")){
            if(direction.matches("no")){
                return !horizontalObstacles.stream().anyMatch(obstacle -> obstacle.collision(x,y+1));
            }
            return !horizontalObstacles.stream().anyMatch(obstacle -> obstacle.collision(x,y));
        }
        return false;
    }

    public void addObstacle(int x1,int y1,int x2,int y2){
        AbstractObstacle obstacle;
        if(y1 == y2){
            obstacle = new HorizontalObstacle(x1,y1,x2,y2);
            horizontalObstacles.add(obstacle);
        }
        if(x1 == x2){
            obstacle = new VerticalObstacle(x1,y1,x2,y2);
            verticalObstacles.add(obstacle);
        }
    }

    public Room(int height, int width) {
        horizontalObstacles = new ArrayList<>();
        verticalObstacles = new ArrayList<>();
        addObstacle(0, 0, width, 0);
        addObstacle(0, 0, 0, height);
        addObstacle(width, 0, width, height);
        addObstacle(0, height, width, height);
    }

    public UUID getId() {
        return id;
    }
}