package thkoeln.st.st2praktikum.entities;


import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.Wall;
import thkoeln.st.st2praktikum.interfaces.HitAble;
import thkoeln.st.st2praktikum.interfaces.LimitAble;
import thkoeln.st.st2praktikum.interfaces.UseAble;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room extends AbstractEntity implements LimitAble {

    private Integer height;
    private Integer width;
    @Transient
    private List<HitAble> hitAbles = new ArrayList<>();
    @Transient
    private List<UseAble> useAbles = new ArrayList<>();
    @Transient
    private List<Wall> walls = new ArrayList<>();

    public Room(Integer height, Integer width){
        this.height = height;
        this.width = width;
    }

    protected Room(){}

    @Override
    public Boolean isInsideLimit(Point point) {
        return point.getX() >= 0 && point.getX() < getWidth() &&
                point.getY() >= 0 && point.getY() < getHeight();
    }

    public Boolean somethingIsHitByMove(Point from, Point to){
        for(HitAble hitAble: hitAbles){
            if(hitAble.isHitByMove(from,to))
                return true;
        }
        for(Wall wall : walls){
            if(wall.isHitByMove(from, to))
                return true;
        }
        return false;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public List<HitAble> getHitAbles() {
        return hitAbles;
    }

    public List<UseAble> getUseAbles() {
        return useAbles;
    }

    public void addHitAble(HitAble hitAble){
        hitAbles.add(hitAble);
    }

    public void addUseAble(UseAble useAble){ useAbles.add(useAble); }

    public void addWall(Wall wall){ walls.add(wall); }

    public void removeWall(Wall wall){ walls.remove(wall); }

    public void removeHitAble(HitAble hitAble){ hitAbles.remove(hitAble); }

    public void removeUseAble(UseAble useAble){ useAbles.remove(useAble); }
}
