package thkoeln.st.st2praktikum.exercise.Room;

import thkoeln.st.st2praktikum.exercise.Coordinate;

public interface MoveAllowed {
    /**
     * Checks if an object is out of bounds and if it will hit another blocking object
     * @param coordinate startpoint
     */
    public boolean canIGoThere(Coordinate coordinate);

    /**
     * Checks if an object will collide with an obstacle
     * @param point1 startpoint
     * @param point2 endpoint
     */
    public boolean obstacleCollision(Coordinate point1,Coordinate point2);
}
