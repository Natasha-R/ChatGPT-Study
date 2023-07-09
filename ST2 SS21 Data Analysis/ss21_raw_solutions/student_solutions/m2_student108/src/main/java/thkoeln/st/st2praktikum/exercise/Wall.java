package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class Wall {

    private Point start;
    private Point end;
    private int length;
    ArrayList<Step> forbiddenSteps;


    public Wall(Point pos1, Point pos2) {
        this.start = pos1;
        this.end = pos2;
        if (isWallDiagonalCheck()) throw new RuntimeException("Walls can got horizontal or vertical, but not diagonal!");
        checkSequenceOfPoints();
        establishForbiddenSteps();
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        if (wallString.length() < 11) throw new RuntimeException("Coordinates should have the form \"(Start-x,Start-y)-(End-x,End-y)\"!");
        String[] startEndSplitted = wallString.split("-");
        if (startEndSplitted.length != 2) throw new RuntimeException("Coordinates should have the form \"(Start-x,Start-y)-(End-x,End-y)\"!");
        if (startEndSplitted[0].charAt(0) != '(' ||
                startEndSplitted[0].charAt(startEndSplitted[0].length()-1) != ')' ||
                startEndSplitted[1].charAt(0) != '(' ||
                startEndSplitted[1].charAt(startEndSplitted[0].length()-1) != ')') {
            throw new RuntimeException("Coordinates should have the form \"(Start-x,Start-y)-(End-x,End-y)\"!");
        }
        Point[] points = new Point[2];
        try {
            points[0] = new Point(startEndSplitted[0]);
            points[1] = new Point(startEndSplitted[1]);
        } catch (RuntimeException exception) {
            throw new RuntimeException("Create Start- & End-Points Failed ("+exception.getMessage()+")");
        }
        this.start = points[0];
        this.end = points[1];
        if (isWallDiagonalCheck()) throw new RuntimeException("Walls can got horizontal or vertical, but not diagonal!");
        checkSequenceOfPoints();
        establishForbiddenSteps();
    }

    private boolean isWallDiagonalCheck() {
        if (this.start.getX() == this.end.getX() || this.start.getY() == this.end.getY()) return false;
        return true;
    }

    private void checkSequenceOfPoints() {
        if (( this.start.getX()+ this.start.getY()) > (this.end.getX()+this.end.getY())) {
            Point tempPoint = this.start;
            this.start = this.end;
            this.end = tempPoint;
        }
    }

    private void establishForbiddenSteps() {
        forbiddenSteps = new ArrayList<>();
        if (start.getX() - end.getX() != 0) {
            this.length = end.getX()- start.getX();
            addForbiddenStepDirectionY(start.getX(), start.getY());
        } else {
            this.length = end.getY()- start.getY();
            addForbiddenStepDirectionX(start.getX(), start.getY());
        }
    }

    private void addForbiddenStepDirectionX(int startX, int startY) {
        for (int i=0; i<this.length; i++) {
            Step step = new Step(new Point(startX, startY+i), new Point(startX+1, startY+i), false);
            forbiddenSteps.add(step);
        }
    }

    private void addForbiddenStepDirectionY(int startX, int startY) {
        for (int i=0; i<this.length; i++) {
            Step step = new Step(new Point(startX+i, startY-1), new Point(startX+i, startY), false);
            forbiddenSteps.add(step);
        }
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public boolean isStepBlocked(Point start, Point end) {
        for ( int i=0; i<forbiddenSteps.size(); i++ ) {
            if ((forbiddenSteps.get(i).equals(start, end) || forbiddenSteps.get(i).equals(end, start)) && !forbiddenSteps.get(i).getUseable()) {
                return true;
            }
        }
        return false;
    }
}
