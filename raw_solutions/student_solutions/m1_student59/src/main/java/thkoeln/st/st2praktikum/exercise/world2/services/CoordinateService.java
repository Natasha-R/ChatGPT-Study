package thkoeln.st.st2praktikum.exercise.world2.services;

import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.IllegalDirectionException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.OutOfFieldException;
import thkoeln.st.st2praktikum.exercise.world2.field.FieldDimensions;
import thkoeln.st.st2praktikum.exercise.world2.types.Direction;

public interface CoordinateService {
    static Coordinate stringToCoordinate(String coordinteString){
        String newCoordinateRaw = coordinteString.substring(1, coordinteString.length() - 1);
        String[] newCoordinate = newCoordinateRaw.split(",");
        return new Coordinate(Integer.parseInt(newCoordinate[0]),Integer.parseInt(newCoordinate[1]));
    }
    static Coordinate nextCoordinateInDirection(Coordinate coordinate, Direction direction) throws IllegalDirectionException {
        Coordinate result;
        switch (direction){
            case NORTH:
                result = new Coordinate(coordinate.getXAxis(), coordinate.getYAxis()+1);
                break;
            case EAST:
                result = new Coordinate(coordinate.getXAxis()+1, coordinate.getYAxis());
                break;
            case SOUTH:
                result = new Coordinate(coordinate.getXAxis(), coordinate.getYAxis()-1);
                break;
            case WEST:
                result = new Coordinate(coordinate.getXAxis()-1, coordinate.getYAxis());
                break;
            default:
                throw new IllegalDirectionException(direction.toString());
        }
        return result;
    }

    /**
     * @param position aktuelle position der miningmaschine
     * @param direction richtung in der sich die maybeBorder befinden soll
     * @param field aktuelles field der miningmaschine
     * @return [maybeBorderStartCoordinates,maybeBorderEndCoordinates];
     * @throws IllegalDirectionException, OutOfFieldException
     */


    static Coordinate[] createMaybeBorder(Coordinate position, Direction direction, FieldDimensions field) throws IllegalDirectionException, OutOfFieldException {

        Coordinate maybeBorderStartCoordinates;
        Coordinate maybeBorderEndCoordinates;
        switch (direction) {
            case NORTH:
                if(position.getYAxis()+1  == field.getHeight()){
                    throw new OutOfFieldException("createMaybeBorder NORTH out of field: "+(position.getYAxis()+1));
                }
                maybeBorderStartCoordinates = new Coordinate(position.getXAxis(), position.getYAxis()+1);
                maybeBorderEndCoordinates = new Coordinate(position.getXAxis()+1, position.getYAxis()+1);
                break;
            case EAST:
                if(position.getXAxis()+1 == field.getWidth()){
                    throw new OutOfFieldException("createMaybeBorder EAST out of field: "+(position.getXAxis()+1));
                }
                maybeBorderStartCoordinates = new Coordinate(position.getXAxis()+1,position.getYAxis());
                maybeBorderEndCoordinates = new Coordinate(position.getXAxis()+1, position.getYAxis()+1);
                break;
            case SOUTH:
                if(position.getYAxis()-1 == -1){
                    throw new OutOfFieldException("createMaybeBorder SOUTH: out of field: "+(position.getYAxis()-1));
                }
                maybeBorderStartCoordinates = new Coordinate(position.getXAxis(),position.getYAxis());
                maybeBorderEndCoordinates = new Coordinate(position.getXAxis()+1,position.getYAxis());
                break;
            case WEST:
                if(position.getXAxis()-1 == -1){
                    throw new OutOfFieldException("createMaybeBorder WEST out of field: "+(position.getXAxis()-1));
                }
                maybeBorderStartCoordinates = new Coordinate(position.getXAxis(), position.getYAxis());
                maybeBorderEndCoordinates = new Coordinate(position.getXAxis(), position.getYAxis()+1);
                break;
            default:
                throw new IllegalDirectionException("createMaybeBorder: "+direction.toString());
        }
        return new Coordinate[]{maybeBorderStartCoordinates, maybeBorderEndCoordinates};
    }

    static Coordinate getDefaultSpawn() {
        return new Coordinate(0,0);
    }
}
