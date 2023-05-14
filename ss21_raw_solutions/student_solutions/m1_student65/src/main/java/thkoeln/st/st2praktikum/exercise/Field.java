package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.Vector;

public class Field {
    private Vector<Obstacle> obstacles;
    private int width,height;

    public Vector<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(Vector<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }
    public Field(Integer height, Integer width)
    {
        obstacles = new Vector<Obstacle>();
        this.width = width;
        this.height = height;

        obstacles.add(new Obstacle(new Vector2d(0,0),new Vector2d(0,height)));
        obstacles.add(new Obstacle(new Vector2d(0,height),new Vector2d(width,height)));
        obstacles.add(new Obstacle(new Vector2d(width,0),new Vector2d(width,height)));
        obstacles.add(new Obstacle(new Vector2d(0,0),new Vector2d(width,0)));
    }
    public void addObstacle(String obstacleString)
    {
        Vector2d vec1 = new Vector2d();
        Vector2d vec2 = new Vector2d();

        int index = obstacleString.indexOf("(");
        vec1.setX(getNumberAt(obstacleString, index + 1));
        index = obstacleString.indexOf(",");
        vec1.setY(getNumberAt(obstacleString, index + 1));

        index = obstacleString.lastIndexOf("(");
        vec2.setX( getNumberAt(obstacleString, index + 1));
        index = obstacleString.lastIndexOf(",");
        vec2.setY(getNumberAt(obstacleString, index + 1));

        obstacles.add(new Obstacle(vec1, vec2));        //need a fix maybe
    }
    private int getNumberAt(String obstacleString, int index)
    {
        char curChar = obstacleString.charAt(index);
        int size = 0;
        while(curChar != ',' && curChar != ')')
        {
            size++;
            curChar = obstacleString.charAt(index+size);
        }
        String s = obstacleString.substring(index,index+size);
        int number = Integer.valueOf(obstacleString.substring(index,index+size));
        return number;
    }
}
