package thkoeln.st.st2praktikum.exercise;
public class Obstacle implements  ObstaclePassable{

    private Coordinate start;
    private Coordinate end;


    public Obstacle(Coordinate pos1, Coordinate pos2) {
        checkOrderedObstacle(pos1,pos2);
    }
    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        String[] positionDivider=obstacleString.split("-");

        this.start= new Coordinate( positionDivider[0]);
        this.end=new Coordinate(positionDivider[1]);
        checkIfObstacleValid(obstacleString);
    }


@Override
    public Coordinate getUnaccessedCoordinate(Coordinate actualCoordinate, Coordinate targetCoordinate) {
        OrderType direction = calculateDirection(actualCoordinate, targetCoordinate);
        switch (direction) {
            case NORTH:  return calculatePositionNorth(actualCoordinate, targetCoordinate);
            case SOUTH:  return calculatePositionSouth(actualCoordinate, targetCoordinate);
            case WEST:   return calculatePositionWest(actualCoordinate, targetCoordinate);
            case EAST:   return calculatePositionEast(actualCoordinate, targetCoordinate);
            default: throw new IllegalArgumentException("Illegal Direction: "+ direction);
        }
    }
    private static OrderType calculateDirection(Coordinate currentPosition, Coordinate targetPosition) {
        int horizontalSteps = targetPosition.getX() - currentPosition.getX();
        int verticalSteps = targetPosition.getY() - currentPosition.getY();
        if (verticalSteps > 0)          return OrderType.NORTH;
        else if (verticalSteps < 0)     return OrderType.SOUTH;
        else if (horizontalSteps > 0)   return OrderType.WEST;
        else if (horizontalSteps < 0)   return OrderType.EAST;
        else                            return OrderType.illegal;
    }


    private Coordinate calculatePositionNorth(Coordinate actualPosition, Coordinate targetPosition) {
        if (start.getY().equals(end.getY())
                && start.getY() > actualPosition.getY()
                && start.getX() <= actualPosition.getX()
                && end.getX() > actualPosition.getX()
                && start.getY() <= targetPosition.getY())
            return new Coordinate( targetPosition.getX(), start.getY()-1);
        return targetPosition;
    }

    private Coordinate calculatePositionSouth(Coordinate actualPosition, Coordinate targetPosition) {
        if (start.getY().equals(end.getY())
                && start.getY() <= actualPosition.getY()
                && start.getX() <= actualPosition.getX()
                && end.getX() > actualPosition.getX()
                && start.getY() > targetPosition.getY())
            return new Coordinate( targetPosition.getX(), start.getY());
        return targetPosition;
    }

    private Coordinate calculatePositionEast(Coordinate actualPosition, Coordinate targetPosition) {
        if (start.getX().equals(end.getX() )
                && start.getX() > actualPosition.getX()
                && start.getY() <= actualPosition.getY()
                && end.getY()  > actualPosition.getY()
                && start.getX() <= targetPosition.getX())
            return new Coordinate(start.getX()-1, targetPosition.getY());
        return targetPosition;
    }

    private Coordinate calculatePositionWest(Coordinate actualPosition, Coordinate targetPosition) {
        if (start.getX().equals(end.getX() )
                && start.getX() <= actualPosition.getX()
                && start.getY() <= actualPosition.getY()
                && end.getY() > actualPosition.getY()
                && start.getX() > targetPosition.getX())
            return new Coordinate( start.getX(), targetPosition.getY());
        return targetPosition;
    }
    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public void checkIfObstacleValid(String obstacleString){
        String[] positionDivider=obstacleString.split("-");
        String first =  positionDivider[0];
        String second = positionDivider[1];
        if(first.length()!=second.length()  ){
            throw new RuntimeException("Wrong Obstacletype");
        }
        if( !(String.valueOf(obstacleString.charAt(0)).equals("(")
                && String.valueOf(obstacleString.charAt(4)).equals(")")
                && String.valueOf(obstacleString.charAt(5)).equals("-")
                && String.valueOf(obstacleString.charAt(6)).equals("(")
                && String.valueOf(obstacleString.charAt(10)).equals(")"))){
            throw new RuntimeException( "Invalid String");
        }

    }

    public void checkOrderedObstacle(Coordinate position1, Coordinate position2){
        if (position1.getX().equals(position2.getY()) && position1.getY().equals(position2.getY())){
            throw new RuntimeException("No diagonal line please");
        }

        if ((position1.getX()>position2.getX() && position1.getY().equals(position2.getY())) || (position1.getY()>position2.getY()&& position1.getX().equals(position2.getX()))){
            this.start = position2;
            this.end = position1;
        }
        else {
            this.start=position1;
            this.end=position2;
        }

        if (!(start.getX().equals(end.getX())||start.getY().equals(end.getY()))){
            throw new RuntimeException("Wrong Obstacle");
        }

    }

}
