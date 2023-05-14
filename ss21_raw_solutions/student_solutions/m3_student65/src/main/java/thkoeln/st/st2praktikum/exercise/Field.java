package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;
import java.util.Vector;
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Field {
    @Id
    private UUID id;

    @Embedded
    //@ElementCollection(targetClass = Obstacle.class, fetch = FetchType.EAGER)
    //@OneToMany(cascade = CascadeType.REMOVE)
    private Vector<Obstacle> obstacles=new Vector<Obstacle>();
    private int width=0,height=0;

    public Vector<Obstacle> getObstacles() {
        return obstacles;
    }

    public Field(Integer height, Integer width, UUID id)
    {
        this.id = id;
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
