package thkoeln.st.st2praktikum.exercise;

public class Barrier {

    private Point start;
    private Point end;


    public Barrier(Point pos1, Point pos2) {
        this.initPoints(pos1, pos2);
    }

    private void initPoints(Point point1, Point point2) {
        this.validatePoints(point1, point2);
        this.setPointsInCorrectOrder(point1, point2);
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        String[] splittedString = barrierString.split("-");
        this.validateSplittedString(splittedString);

        Point start = new Point(splittedString[0]);
        Point end = new Point(splittedString[1]);

        this.initPoints(start, end);
    }

    private void validateSplittedString(String[] splittedString) {
        if(splittedString.length != 2) {
            throw new RuntimeException("Invalid Barrier String");
        }
    }

    private void validatePoints(Point start, Point end) {
        this.validatePointsAreHorizontalOrVerticalAligned(start, end);
    }

    private void validatePointsAreHorizontalOrVerticalAligned(Point start, Point end) {
        boolean isVerticalAligned = start.getX() == end.getX();
        boolean isHorizontalAligned = start.getY() == end.getY();

        if(!isVerticalAligned && !isHorizontalAligned) {
            throw new RuntimeException("Barrier must be horizontal or vertical");
        }
    }

    private void setPointsInCorrectOrder(Point start, Point end) {
        if(end.getX() < start.getX() || end.getY() < start.getY()) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public boolean isHorizontal() { return this.start.getY() == end.getY(); }

    public boolean isVertical() { return this.start.getX() == end.getX(); }
}
