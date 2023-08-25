package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Field {
    @Id
    private UUID id;

    //private Vector<Obstacle> obstacles=new Vector<Obstacle>();
    //@CollectionTable(name = "field_obstacles", joinColumns = @JoinColumn(name = "user_id"))
    //@Column(name = "phone_number")
    @ElementCollection(targetClass = Obstacle.class, fetch = FetchType.EAGER)
    private List<Obstacle> obstacles;

    private int width=0,height=0;

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public Field(Integer height, Integer width, UUID id)
    {
        this.id = id;
        obstacles = new ArrayList<>();
        this.width = width;
        this.height = height;

        obstacles.add(Obstacle.fromString("(0,0)-(0,"+height+")"));
        obstacles.add(Obstacle.fromString("(0,"+height+")-("+width+","+height+")"));
        obstacles.add(Obstacle.fromString("("+width+",0)-("+width+","+height+")"));
        obstacles.add(Obstacle.fromString("(0,0)-("+width+",0)"));
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
