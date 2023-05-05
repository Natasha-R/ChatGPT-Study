package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class Obstacle {

    @Getter @Setter
    private Coordinate start;
    @Getter @Setter
    private Coordinate end;


    public Obstacle(Coordinate pos1, Coordinate pos2) {

        if(!pos1.getX().equals(pos2.getX()) && !pos1.getY().equals(pos2.getY()))
            throw new InvalidArgumentRuntimeException("Obstacle is diagonal");

        if(pos1.getX() < pos2.getX() || pos1.getY() < pos2.getY() ){
            this.start = pos1;
            this.end = pos2;
        }else{
            this.start = pos2;
            this.end = pos1;
        }
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        if(obstacleString.toCharArray()[0] != '(' || obstacleString.toCharArray()[obstacleString.length()-1] != ')')
            throw new RuntimeException("Invalid Argument Exception");

        if(obstacleString.split("-").length != 2)
            throw new RuntimeException("Invalid Argument Exception");

        start = new Coordinate(obstacleString.split("-")[0]);
        end = new Coordinate(obstacleString.split("-")[1]);
    }

}
