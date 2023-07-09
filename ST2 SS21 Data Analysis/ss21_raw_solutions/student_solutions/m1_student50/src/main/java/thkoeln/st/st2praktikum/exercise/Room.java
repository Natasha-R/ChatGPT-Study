package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.ArrayList;


public class Room extends AbstractEntity {

    private int height;
    private int width;
    protected ArrayList<Wall> walls = new ArrayList<>();
    protected ArrayList<TidyUpRobot> tidyUpRobots = new ArrayList<>();
    protected ArrayList<Connection> connections = new ArrayList<>();

    protected Connection connection(UUID destinationRoom){
        for (int i = 0; i < connections.size(); i++){
            if (connections.get(i).getDestinationRoom().equals(destinationRoom)){
                return connections.get(i);
            }
        }
        return null;
    }

    protected void addWall(String stringForWall){
        walls.add(new Wall(stringForWall));
    }

    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width = width;
    }

}
