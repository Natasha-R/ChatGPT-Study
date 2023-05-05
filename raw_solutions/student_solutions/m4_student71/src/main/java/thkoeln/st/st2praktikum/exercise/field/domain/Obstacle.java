package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.InvalidArgumentRuntimeException;

import javax.persistence.Embeddable;


@Embeddable
@NoArgsConstructor
public class Obstacle {

    @Getter
    @Setter
    private Coordinate start;
    @Getter @Setter
    private Coordinate end;


    public Obstacle(Coordinate start, Coordinate end) {

        if(!start.getX().equals(end.getX()) && !start.getY().equals(end.getY()))
            throw new InvalidArgumentRuntimeException("Obstacle is diagonal");

        if(start.getX() < end.getX() || start.getY() < end.getY() ){
            this.start = start;
            this.end = end;
        }else{
            this.start = end;
            this.end = start;
        }
        
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) {
        if(obstacleString.toCharArray()[0] != '(' || obstacleString.toCharArray()[obstacleString.length()-1] != ')')
            throw new RuntimeException("Invalid Argument Exception");

        if(obstacleString.split("-").length != 2)
            throw new RuntimeException("Invalid Argument Exception");

        val start = new Coordinate(obstacleString.split("-")[0]);
        val end = new Coordinate(obstacleString.split("-")[1]);

        return new Obstacle(start,end);
    }
    
}
