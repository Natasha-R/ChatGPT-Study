package thkoeln.st.st2praktikum.exercise;

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

        obstacles.add(new Obstacle("(0,0)-(0,"+height+")"));
        obstacles.add(new Obstacle("(0,"+height+")-("+width+","+height+")"));
        obstacles.add(new Obstacle("("+width+",0)-("+width+","+height+")"));
        obstacles.add(new Obstacle("(0,0)-("+width+",0)"));
    }
    public void addObstacle(Obstacle obstacle)
    {
        Point obstacleStart = obstacle.getStart();
        Point obstacleEnd = obstacle.getEnd();

        if(obstacleStart.getX() > width ||obstacleEnd.getX() > width
                ||obstacleStart.getY() > height ||obstacleEnd.getY() > height)
        {
            throw new IllegalActionException("Obstacle darf nicht außerhalb des Fedes platziert werden!");
        }
        obstacles.add(obstacle);
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
    public Point getSize()
    {
        return new Point(width,height);
    }
}
