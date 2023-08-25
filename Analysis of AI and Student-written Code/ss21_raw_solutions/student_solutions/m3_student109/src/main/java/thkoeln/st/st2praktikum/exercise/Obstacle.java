package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Obstacle {

    @Embedded
    private Vector2D start;
    @Embedded
    private Vector2D end;
    private char direction;

    public Obstacle(Vector2D pos1, Vector2D pos2) {
        if (pos1.getX() == pos2.getX()) {
            if (pos1.getY() < pos2.getY()){
                this.start = pos1;
                this.end = pos2;
            }else {
                this.start = pos2;
                this.end = pos1;
            }
            direction = 'v';
        }
        else if (pos1.getY() == pos2.getY()){
            if (pos1.getX() < pos2.getX()){
                this.start = pos1;
                this.end = pos2;
            }else {
                this.start = pos2;
                this.end = pos1;
            }
            direction = 'h';
        }else {
            throw new RuntimeException("keine diagonalen obstacles");
        }
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
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
                    start = new Vector2D(obstacleStringSplit[0]);
                    end = new Vector2D(obstacleStringSplit[1]);
                }else {
                    start = new Vector2D(obstacleStringSplit[1]);
                    end = new Vector2D(obstacleStringSplit[0]);
                }
                direction = 'v';
            } else if (positions[1] == positions[3]) {
                if (positions[0] < positions [2]){
                    start = new Vector2D(obstacleStringSplit[0]);
                    end = new Vector2D(obstacleStringSplit[1]);
                }else {
                    start = new Vector2D(obstacleStringSplit[1]);
                    end = new Vector2D(obstacleStringSplit[0]);
                }
                direction = 'h';
            } else {
                throw new RuntimeException("keine diagonalen obstacles");
            }
        }
        catch (Exception e){
            throw new RuntimeException("Parsing Failed");
        }
    }


}
