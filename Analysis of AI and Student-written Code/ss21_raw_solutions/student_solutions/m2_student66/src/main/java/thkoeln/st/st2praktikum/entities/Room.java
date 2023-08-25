package thkoeln.st.st2praktikum.entities;


import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.Wall;
import thkoeln.st.st2praktikum.interfaces.HitAble;
import thkoeln.st.st2praktikum.interfaces.LimitAble;
import thkoeln.st.st2praktikum.interfaces.UseAble;

import java.util.HashSet;


public class Room extends AbstractEntity implements LimitAble {

    private Integer height;
    private Integer width;
    private HashSet<HitAble> hitAbles = new HashSet<>();
    private HashSet<UseAble> useAbles = new HashSet<>();
    private HashSet<Wall> walls = new HashSet<>();

    public Room(Integer height, Integer width){
        this.height = height;
        this.width = width;
    }

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

    public HashSet<HitAble> getHitAbles() {
        return hitAbles;
    }

    public HashSet<UseAble> getUseAbles() {
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
