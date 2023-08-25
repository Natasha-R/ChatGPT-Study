package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@NoArgsConstructor
public class Wall {

    @Id
    @GeneratedValue
    private Long WallId;

    @Embedded
    @Setter
    private Point start;
    @Embedded
    @Setter
    private Point end;
    private Boolean vertical;


    public Wall(Point pos1, Point pos2) {
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
    
    public Point getStart() {
        return start;
    }

    public Point getEnd() {
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
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        return new Wall(Point.fromString(wallString.split("-")[0]), Point.fromString(wallString.split("-")[1]));
    }
    
}
