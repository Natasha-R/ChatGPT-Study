package thkoeln.st.st2praktikum.exercise;

public class CustomHelpers {
    public static Coordinate[] parseWallString(String wallString) {
        try {
            Coordinate[] wall = new Coordinate[2];

            String[] wallsString = wallString.split("-");

            wall[0] = new Coordinate(wallsString[0]);
            wall[1] = new Coordinate(wallsString[1]);

            return wall;
        } catch (Exception exception) {
            throw exception;
        }

    }

    public static moveType getMoveTypeFromString(String move) {
        switch (move) {
            case "so":
                return moveType.moveSouth;
            case "no":
                return moveType.moveNorth;
            case "ea":
                return moveType.moveEast;
            case "we":
                return moveType.moveWest;
        }

        throw new UnsupportedOperationException("move input not supported");
    }
}
