package thkoeln.st.st2praktikum.entities;


import thkoeln.st.st2praktikum.interfaces.HitAble;
import thkoeln.st.st2praktikum.interfaces.LimitAble;
import thkoeln.st.st2praktikum.interfaces.UseAble;

import java.util.HashSet;


public class Room extends AbstractEntity implements LimitAble {

    private Integer height;
    private Integer width;
    private HashSet<HitAble> hitAbles = new HashSet<>();
    private HashSet<UseAble> useAbles = new HashSet<>();

    public Room(Integer height, Integer width){
        this.height = height;
        this.width = width;
    }

    @Override
    public Boolean isInsideLimit(Cell cell) {
        return cell.getX() >= 0 && cell.getX() < getWidth() &&
                cell.getY() >= 0 && cell.getY() < getHeight();
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

    public void addHitAble(HitAble hitAble){ hitAbles.add(hitAble); }

    public void addUseAble(UseAble useAble){ useAbles.add(useAble); }

    public void removeHitAble(HitAble hitAble){ hitAbles.remove(hitAble); }

    public void removeUseAble(UseAble useAble){ useAbles.remove(useAble); }
}
