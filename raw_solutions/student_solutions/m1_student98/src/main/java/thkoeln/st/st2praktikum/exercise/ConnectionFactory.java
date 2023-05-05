package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConnectionFactory {

    public Connectable createConnection(Roomable sourceRoom, Roomable destinationRoom, String sourceCoordinates, String targetCoordinates) {
        XYPositionable source = coordinateStringToPosition(sourceRoom, sourceCoordinates);
        XYPositionable destination = coordinateStringToPosition(destinationRoom, targetCoordinates);
        return new Connection(source, destination);
    }

    /**
     * @param coordinates Coordinates are expected to look like this: (10,3)
     */
    private XYPositionable coordinateStringToPosition(Roomable room, String coordinates) {
        String coordinatesWithoutBraces = removeBracesFromCoordinateString(coordinates);
        String[] position = coordinatesWithoutBraces.split(",");
        return createPosition(room, Integer.parseInt(position[0]), Integer.parseInt(position[1]));
    }

    private String removeBracesFromCoordinateString(String coordinates) {
        return coordinates.replaceAll("\\(", "").replaceAll("\\)", "");
    }

    private XYPositionable createPosition(Roomable room, Integer xPos, Integer yPos) {
        return new Position(xPos, yPos, room);
    }
}
