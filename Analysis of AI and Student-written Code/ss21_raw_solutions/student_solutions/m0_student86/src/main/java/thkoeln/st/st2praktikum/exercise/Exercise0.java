package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

class Coord {
    public Coord (Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
    public Integer x, y;

    public Integer pathLength (Coord target) {
        return  Math.abs(x - target.x) +
                Math.abs(y - target.y);
    }
}

class Obstacle {
    public Obstacle (Coord start, Coord end) {
        this.start = start;
        this.end = end;
    }
    public Coord start, end;

    public Boolean isHorizontal () { return start.y.equals(end.y); }
    public Boolean isVertical () { return start.x.equals(end.x); }

    public Integer possibleSteps (Coord pathStart, Coord pathEnd) {
        // Vertikale Bewegung und horizontales Hindernis
        if (pathStart.x.equals(pathEnd.x) && isHorizontal()) {
            Integer pathLine = pathStart.x;
            Integer obstLine = start.y;
            Integer pathMin = Math.min(pathStart.y, pathEnd.y);
            Integer pathMax = Math.max(pathStart.y, pathEnd.y);
            Integer obstMin = Math.min(start.x, end.x);
            Integer obstMax = Math.max(start.x, end.x);

            if (pathLine >= obstMin && pathLine < obstMax &&
                    pathMax >= obstLine && pathMin < obstLine) {
                Integer steps = Math.abs(pathStart.y - obstLine);
                return (pathStart.y < pathEnd.y) ? steps - 1 : steps;
            }
        }
        // Horizontale Bewegung und vertikales Hindernis
        else if (pathStart.y.equals(pathEnd.y) && isVertical()) {
            Integer pathLine = pathStart.y;
            Integer obstLine = start.x;
            Integer pathMin = Math.min(pathStart.x, pathEnd.x);
            Integer pathMax = Math.max(pathStart.x, pathEnd.x);
            Integer obstMin = Math.min(start.y, end.y);
            Integer obstMax = Math.max(start.y, end.y);

            if (pathLine >= obstMin && pathLine < obstMax &&
                    pathMax >= obstLine && pathMin < obstLine) {
                Integer steps = Math.abs(pathStart.x - obstLine);
                return (pathStart.x < pathEnd.x) ? steps - 1 : steps;
            }
        }

        return pathStart.pathLength(pathEnd);
    }
}

class Space {
    public Space (Integer width, Integer height) {
        this.width = width;
        this.height = height;
        obstacles = new ArrayList<>();
        cleanerPosition = new Coord(0,0);
    }

    Integer width, height;
    List<Obstacle> obstacles;
    Coord cleanerPosition;

    public void addObstacle (Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public void setCleanerPosition (Coord coord) {
        cleanerPosition = coord;
    }

    public Coord moveCleaner (Integer horSteps, Integer verSteps) {
        Coord destination = new Coord(
                cleanerPosition.x + horSteps,
                cleanerPosition.y + verSteps
        );

        destination.x = Math.min(width-1, Math.max(0, destination.x));
        destination.y = Math.min(height-1, Math.max(0, destination.y));

        Integer minSteps = cleanerPosition.pathLength(destination);

        for (Obstacle o : obstacles) {
            Integer possibleSteps = o.possibleSteps(cleanerPosition, destination);
            minSteps = Math.min(minSteps, possibleSteps);
        }

        cleanerPosition = new Coord(
                (int) (cleanerPosition.x + Math.signum(horSteps) * minSteps),
                (int) (cleanerPosition.y + Math.signum(verSteps) * minSteps)
        );

        return cleanerPosition;
    }
}

public class Exercise0 implements Walkable {

    public Exercise0 () {
        space = new Space(11, 8);
        space.addObstacle(new Obstacle(new Coord( 2, 1), new Coord(10 ,1)));
        space.addObstacle(new Obstacle(new Coord( 2, 1), new Coord( 2 ,6)));
        space.addObstacle(new Obstacle(new Coord(10 ,1), new Coord(10, 8)));
        space.addObstacle(new Obstacle(new Coord( 2 ,6), new Coord( 7, 6)));

        space.setCleanerPosition(new Coord( 7, 7));
    }

    Space space;

    @Override
    public String walk (String walkCommandString) {
        String direction = walkCommandString.substring(1, 3);
        int horSteps = 0, verSteps = 0;
        int steps = Integer.parseInt(
                walkCommandString.substring(4, walkCommandString.length() - 1)
        );

        switch (direction) {
            case "no": verSteps = steps; break;
            case "ea": horSteps = steps; break;
            case "so": verSteps = -steps; break;
            case "we": horSteps = -steps; break;
            default:
                return "";
        }

        Coord coord = space.moveCleaner(horSteps, verSteps);

        return "(" + coord.x.toString() + "," + coord.y.toString() + ")";
    }
}
