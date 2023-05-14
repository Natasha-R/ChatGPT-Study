package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embeddable;

@Embeddable
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
public class Obstacle {

    private Vector2D start;
    private Vector2D end;


    public Obstacle(Vector2D start, Vector2D end) {
        if(start.getX().equals(end.getX()) || start.getY().equals(end.getY())) {
            orderObstacle(start, end);
        }
        else
            throw new RuntimeException();
        
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
        if(obstacleStringCheck(obstacleString)){
            String[] split = obstacleString.split("-");

            Integer x1 = Integer.parseInt(split[0].replace("(","").replaceAll(",.*",""));
            Integer y1 = Integer.parseInt(split[0].replaceAll(".*,","").replace(")",""));

            Integer x2 = Integer.parseInt(split[1].replace("(","").replaceAll(",.*",""));
            Integer y2 = Integer.parseInt(split[1].replaceAll(".*,","").replace(")",""));

            return new Obstacle(new Vector2D(x1,y1), new Vector2D(x2,y2));
        }
        else
            throw new RuntimeException();
    }

    private static Boolean obstacleStringCheck(String obstacleString){
        String[] split = obstacleString.split("-");

        if(obstacleString.chars().filter(ch -> ch == '(').count() != 2 && obstacleString.chars().filter(ch -> ch == ')').count() != 2) return false;
        if(obstacleString.chars().filter(ch -> ch == ',').count() != 2) return false;
        if(!obstacleString.contains("-")) return false;
        if(split[1] == null) return false;
        if(!Character.isDigit(obstacleString.charAt(obstacleString.indexOf('-')-2)) || !Character.isDigit(obstacleString.charAt(obstacleString.indexOf('-')+2))) return false;
        if(!Character.isDigit(obstacleString.charAt(1)) || !Character.isDigit(obstacleString.charAt(obstacleString.length()-2))) return false;

        return true;
    }

    private void orderObstacle(Vector2D pos1, Vector2D pos2){
        if(pos1.getX().equals(pos2.getX())){
            if(pos1.getY() < pos2.getY()){
                this.start = pos1;
                this.end = pos2;
            }
            else{
                this.start = pos2;
                this.end = pos1;
            }
        }
        else{
            if(pos1.getX() < pos2.getX()){
                this.start = pos1;
                this.end = pos2;
            }
            else{
                this.start = pos2;
                this.end = pos1;
            }
        }
    }
    
}
