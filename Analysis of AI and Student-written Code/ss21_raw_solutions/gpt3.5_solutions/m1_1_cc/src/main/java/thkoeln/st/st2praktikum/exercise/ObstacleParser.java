package thkoeln.st.st2praktikum.exercise;

public class ObstacleParser implements ObstacleParserInterface {
    private static final String INVALID_OBSTACLE_STRING_MESSAGE = "Invalid obstacleString: ";

    @Override
    public Obstacle parseObstacle(String obstacleString) {
        String[] coordinates = obstacleString.split(",");
        if (coordinates.length != 4) {
            throw new IllegalArgumentException(INVALID_OBSTACLE_STRING_MESSAGE + obstacleString);
        }

        int startX = Integer.parseInt(coordinates[0].trim());
        int startY = Integer.parseInt(coordinates[1].trim());
        int endX = Integer.parseInt(coordinates[2].trim());
        int endY = Integer.parseInt(coordinates[3].trim());

        return new Obstacle(startX, startY, endX, endY);
    }
}
