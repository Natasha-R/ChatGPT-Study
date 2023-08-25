package thkoeln.st.st2praktikum.exercise;

class Barrier {
    Position start, end;
    String orientation;

    Barrier(Position start, Position end) {
        this.start = start;
        this.end = end;
        this.orientation = start.x == end.x ? "vertical" : "horizontal";
    }

    public boolean intersects(Position a, Position b, String direction) {
        if (orientation.equals("vertical")) {
            if (direction.equals("ea") && a.x < start.x && b.x == start.x) {
                return Math.min(start.y, end.y) <= a.y && a.y < Math.max(start.y, end.y);
            }
            if (direction.equals("we") && a.x == start.x && b.x < start.x) {
                return Math.min(start.y, end.y) <= a.y && a.y < Math.max(start.y, end.y);
            }
        }
        else {
            if (direction.equals("no") && a.y < start.y && b.y == start.y) {
                return Math.min(start.x, end.x) <= a.x && a.x < Math.max(start.x, end.x);
            }
            if (direction.equals("so") && a.y == start.y && b.y < start.y) {
                return Math.min(start.x, end.x) <= a.x && a.x < Math.max(start.x, end.x);
            }
        }
        return false;
    }
}

