package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Obstacle {


    @Embedded
    private Vector2D start;
    @Embedded
    private Vector2D end;

    private static char direction;


    public Obstacle(Vector2D start, Vector2D end) {
        if (start.getX() == end.getX()) {
            if (start.getY() < end.getY()){
                this.start = start;
                this.end = end;
            }else {
                this.start = end;
                this.end = start;
            }
            direction = 'v';
        }
        else if (start.getY() == end.getY()){
            if (start.getX() < end.getX()){
                this.start = start;
                this.end = end;
            }else {
                this.start = end;
                this.end = start;
            }
            direction = 'h';
        }else {
            throw new RuntimeException("keine diagonalen obstacles");
        }
    }
    
    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) {
        try {
            Pattern searchForInt = Pattern.compile("[+-]?[0-9]+");
            Matcher intExtracted = searchForInt.matcher(obstacleString);
            int[] positions = new int[4];
            int i = 0;
            while (intExtracted.find()) {
                positions[i] = Integer.parseInt(obstacleString.substring(intExtracted.start(), intExtracted.end()));
                i++;
            }
            String obstacleStringSplit[] = obstacleString.split("-");
            if (positions[0] == positions[2]) {
                if (positions[1] < positions[3]){
                    direction = 'v';
                    return new Obstacle(Vector2D.fromString(obstacleStringSplit[0]), Vector2D.fromString(obstacleStringSplit[1]));
                  //  start = new Vector2D(obstacleStringSplit[0]);
                  //  end = new Vector2D(obstacleStringSplit[1]);
                }else {
                    direction = 'v';
                    return new Obstacle(Vector2D.fromString(obstacleStringSplit[1]), Vector2D.fromString(obstacleStringSplit[0]));
                   // start = new Vector2D(obstacleStringSplit[1]);
                   // end = new Vector2D(obstacleStringSplit[0]);
                }

            } else if (positions[1] == positions[3]) {
                if (positions[0] < positions [2]){
                    direction = 'h';
                    return new Obstacle(Vector2D.fromString(obstacleStringSplit[0]), Vector2D.fromString(obstacleStringSplit[1]));
                  //  start = new Vector2D(obstacleStringSplit[0]);
                   // end = new Vector2D(obstacleStringSplit[1]);
                }else {
                    direction = 'h';
                    return new Obstacle(Vector2D.fromString(obstacleStringSplit[1]), Vector2D.fromString(obstacleStringSplit[0]));
                   // start = new Vector2D(obstacleStringSplit[1]);
                    //end = new Vector2D(obstacleStringSplit[0]);
                }
            } else {
                throw new RuntimeException("keine diagonalen obstacles");
            }
        }
        catch (Exception e){
            throw new RuntimeException("Parsing Failed");
        }
    }

    public static char getDirection() {
        return direction;
    }
}
    

