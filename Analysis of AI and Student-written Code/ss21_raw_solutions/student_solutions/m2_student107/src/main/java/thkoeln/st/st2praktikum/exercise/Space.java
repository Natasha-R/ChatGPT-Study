package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Space implements Unique {
    private UUID uuid;
    private int width;
    private int height;
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private List<CleaningDevice> cleaningDeviceList = new ArrayList<CleaningDevice>();

    public void addObstacle(String obstacleString){
        this.obstacles.add(new Obstacle(obstacleString));
    }
    public void addObstacle(Obstacle obstacle){
        if(this.inBounds(obstacle.getStart()) && this.inBounds(obstacle.getEnd())) {
            this.obstacles.add(obstacle);
        } else throw new RuntimeException("Coordinate out of Bounds");
    }

    public Space(int height, int width){
        this.width = width;
        this.height = height;
        this.uuid = UUID.randomUUID();
    }

    public UUID getID() {
        return this.uuid;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public List<CleaningDevice> getCleaningDeviceList() {
        return this.cleaningDeviceList;
    }






    public boolean inBounds(Coordinate coordinate){
        return coordinate.getX() < this.width
                && coordinate.getX() >= 0
                && coordinate.getY() < this.height
                && coordinate.getY() >= 0;
    }
}
