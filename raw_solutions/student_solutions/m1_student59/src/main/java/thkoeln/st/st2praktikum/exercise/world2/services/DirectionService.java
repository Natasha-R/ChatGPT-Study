package thkoeln.st.st2praktikum.exercise.world2.services;

import thkoeln.st.st2praktikum.exercise.world2.types.Direction;

public interface DirectionService {
    static Direction stringToDirection(String directionString) throws IllegalArgumentException{
        Direction result;
        switch (directionString) {
            case "en":
                result = Direction.EN;
                break;
            case "tr":
                result = Direction.TR;
                break;
            case "no":
                result = Direction.NORTH;
                break;
            case "we":
                result = Direction.WEST;
                break;
            case "so":
                result = Direction.SOUTH;
                break;
            case "ea":
                result = Direction.EAST;
                break;
            default:
                throw new IllegalArgumentException(directionString);
        }
        return result;
    }

}
