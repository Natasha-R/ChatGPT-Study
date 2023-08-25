package thkoeln.st.st2praktikum.exercise;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Walkable {
    private static final int FIELD_WIDTH = 12;
    private static final int FIELD_HEIGHT = 9;

    private Cell position;
    private Set<Cell> walls;

    public Exercise0() {
        this.position = new Cell(0, 2);

        this.walls = new HashSet<>();
        // field boundaries
        for(int i=0; i<FIELD_WIDTH; i++) {
            this.walls.add(new Cell(i, -1)); // south boundary
            this.walls.add(new Cell(i, FIELD_HEIGHT)); // north boundary
        }
        for(int j=0; j<FIELD_HEIGHT; j++) {
            this.walls.add(new Cell(-1, j)); // west boundary
            this.walls.add(new Cell(FIELD_WIDTH, j)); // east boundary
        }
        // specified walls
        addWall(3, 0, 3, 3);
        addWall(5, 0, 5, 4);
        addWall(4, 5, 7, 5);
        addWall(7, 5, 7, 9);
    }

    private void addWall(int x1, int y1, int x2, int y2) {
        for(int i=x1; i<=x2; i++) {
            for(int j=y1; j<=y2; j++) {
                this.walls.add(new Cell(i, j));
            }
        }
    }

    public String walk(String walkCommandString) {
        Pattern pattern = Pattern.compile("\\[(\\w+),(\\d+)\\]");
        Matcher matcher = pattern.matcher(walkCommandString);
        if(matcher.find()) {
            String direction = matcher.group(1);
            int steps = Integer.parseInt(matcher.group(2));
            int dx = 0, dy = 0;
            switch (direction) {
                case "no": dy = 1; break;
                case "so": dy = -1; break;
                case "ea": dx = 1; break;
                case "we": dx = -1; break;
            }
            for(int i=0; i<steps; i++) {
                Cell nextPosition = new Cell(position.x + dx, position.y + dy);
                if(walls.contains(nextPosition)) break;
                this.position = nextPosition;
            }
        }
        return this.position.toString();
    }

    private static class Cell {
        int x, y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj) return true;
            if(obj == null || getClass() != obj.getClass()) return false;
            Cell cell = (Cell) obj;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
