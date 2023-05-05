package thkoeln.st.st2praktikum.exercise.entities;

public class Move {
    private final Coordinate start;
    private String direction;

    public Move(Coordinate start, String direction) {
        this.start = start;
        this.direction = direction;
    }

    public Move(Coordinate start, Coordinate destination) {
        this.start = start;
        if (!(start.getX() == destination.getX())) {
            switch (destination.getX() - start.getX()) {
                case 1:
                    this.direction = "no";
                    break;
                case -1:
                    this.direction = "so";
                    break;
            }
        } else if (!(start.getY() == destination.getY())) {
            switch (destination.getX() - start.getX()) {
                case 1:
                    this.direction = "ea";
                    break;
                case -1:
                    this.direction = "we";
                    break;
            }
        }
    }


    public Coordinate getStart() {
        return start;
    }

    public String getDirection() {
        return direction;
    }


    public Move reverse() {
        String oppositeDirection;
        int x = start.getX();
        int y = start.getY();

        switch (direction) {
            case "no":
                y += 1;
                oppositeDirection = "so";
                break;
            case "so":
                y -= 1;
                oppositeDirection = "no";
                break;
            case "ea":
                x += 1;
                oppositeDirection = "we";
                break;
            case "we":
                x -= 1;
                oppositeDirection = "ea";
                break;
            default:
                throw new IllegalArgumentException("Invalid Direction in Move:" + this);
        }

        return new Move(new Coordinate(x, y), oppositeDirection);
    }

    public Coordinate destination() {
        int x = start.getX();
        int y = start.getY();

        switch (direction) {
            case "no":
                y += 1;
                break;
            case "so":
                y -= 1;
                break;
            case "ea":
                x += 1;
                break;
            case "we":
                x -= 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid Direction in Move:" + this);
        }
        return new Coordinate(x, y);
    }

    public boolean equals(Object o) {
        if (o instanceof Move) {
            Move move = (Move) o;
            return this.getStart().equals(move.getStart()) && this.getDirection().equals(move.getDirection());
        } else return false;
    }
}
