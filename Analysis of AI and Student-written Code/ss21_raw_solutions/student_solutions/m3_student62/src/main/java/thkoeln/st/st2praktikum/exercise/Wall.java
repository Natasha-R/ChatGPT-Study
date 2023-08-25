package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Setter
    @Embedded
    private Point start;
    @Setter
    @Embedded
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

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        this(new Point(wallString.split("-")[0]),new Point(wallString.split("-")[1]));
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
            if(x> start.getX()&&x<end.getX()){
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
            if(y>start.getY()&&y<end.getY()){
                result=true;
            }else{
                result=false;
            }
        }
        return result;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Boolean isVertical(){ return vertical; }

}
