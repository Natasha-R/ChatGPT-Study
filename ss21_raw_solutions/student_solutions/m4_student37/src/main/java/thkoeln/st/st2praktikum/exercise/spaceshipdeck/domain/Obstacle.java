package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Obstacle {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Embedded
    private Vector2D start;
    @Setter
    @Embedded
    private Vector2D end;
    private boolean vertical;


    public Obstacle(Vector2D pos1, Vector2D pos2) {
        if(pos1.getX()==pos2.getX()){
            vertical=true;
            if(pos1.getY()>pos2.getY()){
                start=pos2;
                end=pos1;
            }else{
                start=pos1;
                end=pos2;
            }
        }else if(pos1.getY()==pos2.getY()){
            vertical=false;
            if(pos1.getX()>pos2.getX()){
                start=pos2;
                end=pos1;
            }else{
                start=pos1;
                end=pos2;
            }
        }else{
            throw new IllegalArgumentException();
        }
    }

    public Boolean isVertical(){ return vertical; }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    public boolean sameX(int x) {
        boolean result = false;
        if(vertical){
            if(x==start.getX()){
                result=true;
            }else{
                result=false;
            }
        }else{
            if(x>= start.getX()&&x<end.getX()){
                result=true;
            }else{
                result=false;
            }
        }
        return result;
    }

    public boolean sameY(int y) {
        boolean result = false;
        if(!vertical){
            if(y==start.getY()){
                result=true;
            }else{
                result=false;
            }
        }else{
            if(y>=start.getY()&&y<end.getY()){
                result=true;
            }else{
                result=false;
            }
        }
        return result;
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) {
        return new Obstacle(Vector2D.fromString(obstacleString.split("-")[0]), Vector2D.fromString(obstacleString.split("-")[1]));
    }
    
}
