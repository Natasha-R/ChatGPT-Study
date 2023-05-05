package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;



public class Obstacle {

    private Coordinate start;
    private Coordinate end;


    public Obstacle(Coordinate start, Coordinate end) {
        
        this.start = start;
        this.end = end;
        
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
        throw new UnsupportedOperationException();
    }
    
}
