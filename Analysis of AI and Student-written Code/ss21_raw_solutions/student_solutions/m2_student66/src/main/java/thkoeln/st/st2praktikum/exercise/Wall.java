package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exceptions.WallException;

public class Wall {

    private Point start;
    private Point end;


    public Wall(Point pos1, Point pos2) {
        start = pos1;
        end = pos2;
        if(!firstPointIsCloserToGridOrigin()) {
            start = pos2;
            end = pos1;
        }
        if(!isHorizontal() && !isVertical())
            throw new WallException("A wall must be either vertical or horizontal.");
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        try {
            String[] wallStringArray = wallString.split("-");
            start = new Point(wallStringArray[0]);
            end = new Point(wallStringArray[1]);
            if(!firstPointIsCloserToGridOrigin()) {
                start = new Point(wallStringArray[1]);
                end = new Point(wallStringArray[0]);
            }
            if(!isHorizontal() && !isVertical())
                throw new WallException("A wall must be either vertical or horizontal.");
        }catch (Exception ex){
            throw new WallException("Wallstring should be in this form: (x,y)-(x,y).");
        }
    }

    public Boolean isHitByMove(Point from, Point to){
        int xDifference = to.getX() - from.getX();
        int yDifference = to.getY() - from.getY();
        switch (xDifference){
            case 1:
                return isVertical() && to.getX().equals(start.getX()) &&
                        from.getY() < end.getY() && from.getY() >= start.getY();
            case -1:
                return isVertical() && from.getX().equals(start.getX()) &&
                        from.getY() < end.getY() && from.getY() >= start.getY();
        }
        switch (yDifference){
            case 1:
                return isHorizontal() && to.getY().equals(start.getY()) &&
                        from.getX() < end.getX() && from.getX() >= start.getX();
            case -1:
                return isHorizontal() && from.getY().equals(start.getY()) &&
                        from.getX() < end.getX() && from.getX() >= start.getX();
        }
        return false;
    }

    public Boolean isVertical(){
        return start.getX().equals(end.getX());
    }

    public Boolean isHorizontal(){
        return start.getY().equals(end.getY());
    }

    private Boolean firstPointIsCloserToGridOrigin(){
        if(isHorizontal()) {
            return start.getX() < end.getX();
        } else {
            return start.getY() < end.getY();
        }
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
