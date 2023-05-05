package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@NoArgsConstructor
@Embeddable
@Getter
public class Obstacle {

    @Embedded
    private Coordinate start;

    @Embedded
    private Coordinate end;


    public Obstacle(Coordinate start, Coordinate end) {
        
       checkOrderedObstacle(start,end);
        
    }
    
    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) {
       return new Obstacle(obstacleString);
    }

    public Obstacle(String obstacleString) {
        String[] positionDivider=obstacleString.split("-");

        this.start= new Coordinate( positionDivider[0]);
        this.end=new Coordinate(positionDivider[1]);
        checkIfObstacleValid(obstacleString);
    }

    public void checkIfObstacleValid(String obstacleString){
        String[] positionDivider=obstacleString.split("-");
        String first =  positionDivider[0];
        String second = positionDivider[1];
        if(first.length()!=second.length()  ){
            throw new RuntimeException("Wrong Obstacletype");
        }
        if( !(String.valueOf(obstacleString.charAt(0)).equals("(")
                && String.valueOf(obstacleString.charAt(4)).equals(")")
                && String.valueOf(obstacleString.charAt(5)).equals("-")
                && String.valueOf(obstacleString.charAt(6)).equals("(")
                && String.valueOf(obstacleString.charAt(10)).equals(")"))){
            throw new RuntimeException( "Invalid String");
        }

    }

    public void checkOrderedObstacle(Coordinate position1, Coordinate position2){
        if (position1.getX().equals(position2.getY()) && position1.getY().equals(position2.getY())){
            throw new RuntimeException("No diagonal line please");
        }

        if ((position1.getX()>position2.getX() && position1.getY().equals(position2.getY())) || (position1.getY()>position2.getY()&& position1.getX().equals(position2.getX()))){
            this.start = position2;
            this.end = position1;
        }
        else {
            this.start=position1;
            this.end=position2;
        }

        if (!(start.getX().equals(end.getX())||start.getY().equals(end.getY()))){
            throw new RuntimeException("Wrong Obstacle");
        }

    }
    
}
