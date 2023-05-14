package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.deck.Dimensions;
import thkoeln.st.st2praktikum.exercise.droid.Direction;
import thkoeln.st.st2praktikum.exercise.exceptions.OutOfFieldException;
import thkoeln.st.st2praktikum.exercise.field.Field;

import java.util.Arrays;

public interface CoordinateService {
    static Coordinate stringToCoordinate(String coordinateString){
        String newCoordinateRaw = coordinateString.substring(1, coordinateString.length()-1);
        String[] newCoordinate = newCoordinateRaw.split(",");
        return new Coordinate(Integer.parseInt(newCoordinate[0]),Integer.parseInt(newCoordinate[1]));
    }

    static Coordinate[] createMaybeWall(Field droidPosition, Direction dir, Dimensions deckOfDroid) throws OutOfFieldException {
        Coordinate startCoordinate;
        Coordinate endCoordinate;

        switch (dir) {
            case NO:
                if(droidPosition.getCoordinate().getYAxes() + 1 == deckOfDroid.getHeight()){
                    throw new OutOfFieldException(droidPosition.toString());
                }else {
                    startCoordinate = new Coordinate(droidPosition.getCoordinate().getXAxes() , droidPosition.getCoordinate().getYAxes() + 1);
                    endCoordinate = new Coordinate(droidPosition.getCoordinate().getXAxes() + 1, droidPosition.getCoordinate().getYAxes() + 1);
                }
                break;
            case EA:
                if(droidPosition.getCoordinate().getXAxes() + 1 == deckOfDroid.getWidht()){
                    throw new OutOfFieldException(droidPosition.toString());
                }else {
                    startCoordinate = new Coordinate(droidPosition.getCoordinate().getXAxes() + 1, droidPosition.getCoordinate().getYAxes() );
                    endCoordinate = new Coordinate(droidPosition.getCoordinate().getXAxes() + 1, droidPosition.getCoordinate().getYAxes() + 1);
                }
                break;
            case SO:
                if(droidPosition.getCoordinate().getYAxes() -1 == -1) {
                    throw new OutOfFieldException(droidPosition.toString());
                }else {
                    startCoordinate = new Coordinate(droidPosition.getCoordinate().getXAxes(), droidPosition.getCoordinate().getYAxes() );
                    endCoordinate = new Coordinate(droidPosition.getCoordinate().getXAxes() + 1, droidPosition.getCoordinate().getYAxes());
                }
                break;
            case WE:
                if (droidPosition.getCoordinate().getXAxes() -1 == -1){
                    throw new OutOfFieldException(droidPosition.toString());
                }else {
                    startCoordinate = new Coordinate(droidPosition.getCoordinate().getXAxes() , droidPosition.getCoordinate().getYAxes() );
                    endCoordinate = new Coordinate(droidPosition.getCoordinate().getXAxes() , droidPosition.getCoordinate().getYAxes() + 1);
                }
                break;
            default:
                throw new OutOfFieldException(droidPosition.toString());


        }
        return new Coordinate[]{startCoordinate, endCoordinate};
    }
}
