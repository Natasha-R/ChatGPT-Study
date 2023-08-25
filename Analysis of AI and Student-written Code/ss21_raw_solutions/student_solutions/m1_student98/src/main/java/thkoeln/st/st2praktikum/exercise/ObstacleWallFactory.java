package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ObstacleWallFactory {

    private XYPositionable fromPosition;
    private XYPositionable toPosition;


    public Passable createObstacle(Roomable room, String obstacleString) {
        obstacleStringToPositions(room, obstacleString);
        return new ObstacleWall(this.fromPosition, this.toPosition);
    }

    private void obstacleStringToPositions(Roomable room, String obstacleString) {
        String withoutBraces = removeBracesFromString(obstacleString);
        String[] coordinatesSeparated = withoutBraces.split("-");

        String[] pointFrom = coordinatesSeparated[0].split(",");
        String[] pointTo = coordinatesSeparated[1].split(",");

        this.fromPosition = createPosition(room, Integer.parseInt(pointFrom[0]), Integer.parseInt(pointFrom[1]));
        this.toPosition = createPosition(room, Integer.parseInt(pointTo[0]), Integer.parseInt(pointTo[1]));
    }

    private String removeBracesFromString(String input) {
        return input.replaceAll("\\(", "").replaceAll("\\)","");
    }

    private XYPositionable createPosition(Roomable room, Integer xPos, Integer yPos) {
        return new Position(xPos, yPos, room);
    }
}
