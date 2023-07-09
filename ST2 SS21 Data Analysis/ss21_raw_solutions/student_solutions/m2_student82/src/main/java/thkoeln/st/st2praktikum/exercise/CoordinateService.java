package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.OutOfFieldException;

public interface CoordinateService {


        static Coordinate[] createMaybeWall(Field droidPosition, OrderType orderType, Dimensions deckOfDroid) throws OutOfFieldException {
            Coordinate startCoordinate;
            Coordinate endCoordinate;

            switch (orderType) {
                case NORTH:
                    if(droidPosition.getCoordinate().getY() + 1 == deckOfDroid.getHeight()){
                        throw new OutOfFieldException(droidPosition.toString());
                    }else {
                        startCoordinate = new Coordinate(droidPosition.getCoordinate().getX() , droidPosition.getCoordinate().getY() + 1);
                        endCoordinate = new Coordinate(droidPosition.getCoordinate().getX() + 1, droidPosition.getCoordinate().getY() + 1);
                    }
                    break;
                case EAST:
                    if(droidPosition.getCoordinate().getX() + 1 == deckOfDroid.getWidht()){
                        throw new OutOfFieldException(droidPosition.toString());
                    }else {
                        startCoordinate = new Coordinate(droidPosition.getCoordinate().getX() + 1, droidPosition.getCoordinate().getY() );
                        endCoordinate = new Coordinate(droidPosition.getCoordinate().getX() + 1, droidPosition.getCoordinate().getY() + 1);
                    }
                    break;
                case SOUTH:
                    if(droidPosition.getCoordinate().getY() -1 == -1) {
                        throw new OutOfFieldException(droidPosition.toString());
                    }else {
                        startCoordinate = new Coordinate(droidPosition.getCoordinate().getX(), droidPosition.getCoordinate().getY() );
                        endCoordinate = new Coordinate(droidPosition.getCoordinate().getX() + 1, droidPosition.getCoordinate().getY());
                    }
                    break;
                case WEST:
                    if (droidPosition.getCoordinate().getX() -1 == -1){
                        throw new OutOfFieldException(droidPosition.toString());
                    }else {
                        startCoordinate = new Coordinate(droidPosition.getCoordinate().getX() , droidPosition.getCoordinate().getY() );
                        endCoordinate = new Coordinate(droidPosition.getCoordinate().getX() , droidPosition.getCoordinate().getY() + 1);
                    }
                    break;
                default:
                    throw new OutOfFieldException(droidPosition.toString());


            }
            return new Coordinate[]{startCoordinate, endCoordinate};
        }
    }

