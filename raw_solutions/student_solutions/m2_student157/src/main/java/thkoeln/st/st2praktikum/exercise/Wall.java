package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wall {

    private Vector2D start;
    private Vector2D end;
    ArrayList<Brick> bricks = new ArrayList<>();


    public Wall(Vector2D pos1, Vector2D pos2) {
        if(pos1.getX().equals(pos2.getX())) {
            if(pos1.getY() < pos2.getY()) {
                this.start = pos1;
                this.end = pos2;
            } else {
                this.start = pos2;
                this.end = pos1;
            }
        } else {
            if(pos1.getX() < pos2.getX()) {
                this.start = pos1;
                this.end = pos2;
            } else {
                this.start = pos2;
                this.end = pos1;
            }
        }

        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();

        if(startX != endX && startY != endY) {
            throw new RuntimeException();
        }

        if(startX == endX) {
            for (int i = startY; i < endY; i++) {
                bricks.add(new Brick(new Vector2D(startX - 1, i), new Vector2D(startX, i)));
            }
        } else {
            for (int i = startX; i < endX; i++) {
                bricks.add(new Brick(new Vector2D(i, startY - 1), new Vector2D(i, startY)));
            }
        }
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        final String regex = "\\(([0-9]*),([0-9]*)\\)-\\(([0-9]*),([0-9]*)\\)";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(wallString);

        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        if (matcher.find()) {
            startX = Integer.parseInt(matcher.group(1));
            startY = Integer.parseInt(matcher.group(2));
            endX = Integer.parseInt(matcher.group(3));
            endY = Integer.parseInt(matcher.group(4));
        } else {
            throw new RuntimeException();
        }

        if(startX == endX) {
            for (int i = startY; i < endY; i++) {
                bricks.add(new Brick(new Vector2D(startX - 1, i), new Vector2D(startX, i)));
            }
        } else {
            for (int i = startX; i < endX; i++) {
                bricks.add(new Brick(new Vector2D(i, startY - 1), new Vector2D(i, startY)));
            }
        }

        this.start = new Vector2D(startX, startY);
        this.end = new Vector2D(endX, endY);
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}
