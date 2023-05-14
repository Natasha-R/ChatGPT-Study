package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {
    private Vector2D size;
    private UUID id = UUID.randomUUID();
    private List<Blocking> blockers = new ArrayList<Blocking>();


    public Room(Integer height, Integer width) throws Vector2DException {
        size = new Vector2D(width, height);
    }

    public boolean isCoordinateInBounds(Vector2D vector2D){
        if(vector2D.getX() > getWidth()-1 || vector2D.getY() > getHeight()-1)
            return false;
        return true;
    }

    public boolean isCoordinateEmpty(Vector2D coordinates){
        if(blockers.stream().anyMatch(findBlocker -> findBlocker.isBlockingCoordinate(coordinates)))
            return false;
        return true;
    }


    public void addBlocker(Blocking blocker){
        this.blockers.add(blocker);
    }
    public void removeBlocker(Blocking blocker){
        this.blockers.remove(blocker);
    }

    public int getHeight(){
        return size.getY();
    }
    public int getWidth(){
        return size.getX();
    }
    public UUID getId(){
        return id;
    }
}
