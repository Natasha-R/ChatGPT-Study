package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embeddable;

@Embeddable
public class Wall {

    @Getter
    private Point start;
    @Getter
    private Point end;


    public Wall(Point pos1, Point pos2) {
        start = pos1;
        end = pos2;
        if(!firstPointIsCloserToGridOrigin(start, end)) {
            start = pos2;
            end = pos1;
        }
        if(!isHorizontal(start, end) && !isVertical(start, end))
            throw new WallException("A wall must be either vertical or horizontal.");
    }

    protected Wall(){}

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        try {
            String[] wallStringArray = wallString.split("-");
            Point start = Point.fromString(wallStringArray[0]);
            Point end = Point.fromString(wallStringArray[1]);
            if(!firstPointIsCloserToGridOrigin(start, end)) {
                start = Point.fromString(wallStringArray[1]);
                end = Point.fromString(wallStringArray[0]);
            }
            if(!isHorizontal(start, end) && !isVertical(start, end))
                throw new WallException("A wall must be either vertical or horizontal.");
            return new Wall(start, end);
        }catch (Exception ex){
            throw new WallException("Wallstring should be in this form: (x,y)-(x,y).");
        }
    }

    public Boolean isHitByMove(Point from, Point to){
        int xDifference = to.getX() - from.getX();
        int yDifference = to.getY() - from.getY();
        switch (xDifference){
            case 1:
                return isVertical(start, end) && to.getX().equals(start.getX()) &&
                        from.getY() < end.getY() && from.getY() >= start.getY();
            case -1:
                return isVertical(start, end) && from.getX().equals(start.getX()) &&
                        from.getY() < end.getY() && from.getY() >= start.getY();
        }
        switch (yDifference){
            case 1:
                return isHorizontal(start, end) && to.getY().equals(start.getY()) &&
                        from.getX() < end.getX() && from.getX() >= start.getX();
            case -1:
                return isHorizontal(start, end) && from.getY().equals(start.getY()) &&
                        from.getX() < end.getX() && from.getX() >= start.getX();
        }
        return false;
    }

    public static Boolean isVertical(Point start, Point end){
        return start.getX().equals(end.getX());
    }

    public static Boolean isHorizontal(Point start, Point end){
        return start.getY().equals(end.getY());
    }

    private static Boolean firstPointIsCloserToGridOrigin(Point start, Point end){
        if(isHorizontal(start, end)) {
            return start.getX() < end.getX();
        } else {
            return start.getY() < end.getY();
        }
    }
}
