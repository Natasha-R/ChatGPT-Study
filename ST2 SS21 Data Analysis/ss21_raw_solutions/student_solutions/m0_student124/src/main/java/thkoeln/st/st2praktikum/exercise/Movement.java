package thkoeln.st.st2praktikum.exercise;

import java.util.List;

public class Movement extends Path {

    private Direction direction;

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    public Movement(Point start, Point end, Direction direction) {
        super(start, end);
        this.direction = direction;
    }

    public Point getLastPossibleMovementPoint(List<Path> walls, int mapWidth, int mapHeight) {
        // mache bewegung und überprüfe jedes mal ob man eine wall berührt auf diesem Punkt
        Point position = getFrom();

        System.out.println();

        while (!position.equals(getTo())) {
            for (Path wall: walls) {
                if(stepCollidesWithWall(position, wall, direction)) {
                    System.out.println(wall);
                    System.out.println("Collides with wall");
                    return position;
                }
            }

            Point newPosition = move(position, 1);

            System.out.println("New position: " + newPosition);

            if(newPosition.isOutOfBounds(mapWidth, mapHeight)) {
                System.out.println("Is out of bounds");
                return position;
            }

            position = newPosition;

            System.out.println(position);
            System.out.println(getTo());
            System.out.println(position.equals(getTo()));

            System.out.println();
        }

        return position;
    }

    private Point move(Point position, int i) {
        switch (direction) {
            case NORTH:
                return new Point(position.getX(), position.getY() + 1);
            case EAST:
                return new Point(position.getX() + 1, position.getY());
            case SOUTH:
                return new Point(position.getX(), position.getY() - 1);
            case WEST:
                return new Point(position.getX() - 1, position.getY());
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    private boolean stepCollidesWithWall(Point position, Path wall, Direction direction) {

        int pathMinX = Math.min(wall.getFrom().getX(), wall.getTo().getX());
        int pathMaxX = Math.max(wall.getFrom().getX(), wall.getTo().getX());
        int pathMinY = Math.min(wall.getFrom().getY(), wall.getTo().getY());
        int pathMaxY = Math.max(wall.getFrom().getY(), wall.getTo().getY());

        int x = position.getX();
        int y = position.getY();

        switch (direction) {
            case NORTH:
            case SOUTH:
                // wall muss vertikal sein

                // ist wall vertikal?
                if(pathMinY == pathMaxY) {
                    System.out.println("Wall ist vertikal");

                    // kann es auf y-Achse Berührung geben
                    if(direction == Direction.SOUTH) {
                        // (8,2) -> (8,1)
                        if(y != pathMinY) {
                            return false;
                        }
                    } else {
                        // (8,1) -> (8,2)
                        if(y + 1 != pathMinY) {
                            return false;
                        }
                    }
                    return x >= pathMinX && x < pathMaxX;
                }
                break;
            default:
                // wall muss horizontal sein

                // ist wall horizontal?
                if(pathMinX == pathMaxX) {
                    System.out.println("Wall ist horizontal");

                    // kann es auf y-Achse Berührung geben
                    if(direction == Direction.WEST) {
                        // (8,2) -> (8,1)
                        if(x != pathMinX) {
                            return false;
                        }
                    } else {
                        // (8,1) -> (8,2)
                        if(x + 1 != pathMinX) {
                            return false;
                        }
                    }
                    return y >= pathMinY && y < pathMaxY;
                }
                break;
        }

        return false;
    }

    public Direction getDirection() {
        return direction;
    }
}
