// Package for the class
package thkoeln.st.st2praktikum.exercise;

// Required import statement
import java.awt.Point;

// Public class named Exercise0 implementing the interface Walkable
public class Exercise0 implements Walkable {

    // Declare a private Room object named room
    private Room room;

    // Declare a private Robot object named robot
    private Robot robot;

    // Default constructor
    public Exercise0() {

        // Initialize the room object with dimensions 12x8
        room = new Room(12, 8);

        // Initialize the robot object at the position (3,0)
        robot = new Robot(3, 0);

        // Add walls to the room at the specified positions
        room.addWall(new Wall(3,0,3,3));
        room.addWall(new Wall(7,0,7,2));
        room.addWall(new Wall(4,3,7,3));
        room.addWall(new Wall(1,4,8,4));

        // End of constructor
    }

    // Implementation of the method walkTo from the interface Walkable
    @Override
    public String walkTo(String walkCommandString) {

        // Extract the direction from the walkCommandString
        String direction = walkCommandString.substring(1, 3);

        // Extract the number of steps from the walkCommandString and convert it to integer
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1));

        // Start a loop that iterates as many times as steps
        for (int i = 0; i < steps; i++) {

            // Calculate the next position of the robot based on the direction
            Point nextPosition = robot.calculateNextPositionAfterMoveInDirection(direction);

            // If the robot can move to the next position, it moves, otherwise it breaks the loop
            if (room.canMoveTo(nextPosition)) {
                robot.moveInDirection(direction);
            } else {
                break;
            }
        }

        // After all steps (or breaking the loop), the method returns the current position of the robot as a String
        return robot.getCurrentPositionAsString();
    }

    // End of the Exercise0 class
}
