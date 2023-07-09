package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Robot {
    private Point currentLocation;

    public Robot(int initialXPosition, int initialYPosition) {
        this.setCurrentLocation(new Point(initialXPosition, initialYPosition));
    }

    private void setCurrentLocation(Point newLocation) {
        this.currentLocation = newLocation;
    }

    private Point getCurrentLocation() {
        return this.currentLocation;
    }

    public Point calculateNextPositionAfterMoveInDirection(String cardinalDirection) {
        Point futurePositionAfterMove = new Point(getCurrentLocation());

        switch (cardinalDirection) {
            case "no": futurePositionAfterMove.y++; break;
            case "ea": futurePositionAfterMove.x++; break;
            case "so": futurePositionAfterMove.y--; break;
            case "we": futurePositionAfterMove.x--; break;
        }

        return futurePositionAfterMove;
    }

    public void moveInDirection(String cardinalDirection) {
        Point newLocation = calculateNextPositionAfterMoveInDirection(cardinalDirection);
        setCurrentLocation(newLocation);
    }

    public String getCurrentPositionAsString() {
        Point currentLocation = getCurrentLocation();
        return "(" + currentLocation.x + "," + currentLocation.y + ")";
    }
}
