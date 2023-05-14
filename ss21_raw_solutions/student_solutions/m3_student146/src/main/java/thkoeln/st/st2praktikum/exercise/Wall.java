package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Pattern;

public class Wall {

    private Coordinate start;
    private Coordinate end;


    public Wall(Coordinate pos1, Coordinate pos2) {
        if (pos1.getX().equals(pos2.getX()) || pos1.getY().equals(pos2.getY())) {
            if ((pos1.getX() > pos2.getX() && pos1.getY().equals(pos2.getY())) || (pos1.getY() > pos2.getY() && pos1.getX().equals(pos2.getX()))) {
                this.start = pos2;
                this.end = pos1;
            } else {
                this.start = pos1;
                this.end = pos2;
            }
        } else
            throw new RuntimeException("No diagonal walls allowed!");
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        if(!Pattern.matches("\\(\\d+,\\d+\\)-\\(\\d+,\\d+\\)", wallString))
            throw new RuntimeException("Wrong format or the coordinates are negative!");
        String[] wallSplit = wallString.split("-");
        String from = wallSplit[0];
        String to = wallSplit[1];
        String[] fromSplit = from.split(",");
        String[] toSplit = to.split(",");
        int fromX = Integer.parseInt(fromSplit[0].replace("(", ""));
        int fromY = Integer.parseInt(fromSplit[1].replace(")", ""));
        int toX = Integer.parseInt(toSplit[0].replace("(", ""));
        int toY = Integer.parseInt(toSplit[1].replace(")", ""));
        if((fromX == toX) || (fromY == toY)) {
            if ((fromX > toX) || (fromY > toY)) {
                this.start = new Coordinate(wallSplit[1]);
                this.end = new Coordinate(wallSplit[0]);
            }else {
                this.start = new Coordinate(wallSplit[0]);
                this.end = new Coordinate(wallSplit[1]);
            }
        } else
            throw new RuntimeException("No diagonal walls allowed!");
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
