package thkoeln.st.st2praktikum.exercise.Entities;

import thkoeln.st.st2praktikum.exercise.Interfaces.CreateObstacleable;

public class ObstacleFactory implements CreateObstacleable {
    private Obstacle obstacle;
    @Override
    public Obstacle createObstacle(SpaceShipDeck spaceShipDeck, String obstacleString){
        CoordinateStringReader reader = new CoordinateStringReader();
        obstacle = new Obstacle();
        obstacle.setSpaceShipDeck((SpaceShipDeck) spaceShipDeck);
        String [] twoCoordinates = reader.splittStringWithTwoCoordinates(obstacleString);
        obstacle.setDestinationCoordinates(reader.readCoordinatesOfString(twoCoordinates[1]));
        obstacle.setSourceCoordinates(reader.readCoordinatesOfString(twoCoordinates[0]));
        return obstacle;
    }
    @Override
    public void addObstacleOnSpaceShipDeck(SpaceShipDeck spaceShipDeck){
        Coordinates sourceCoordinates = obstacle.getSourceCoordinates();
        Coordinates destinationCoordinates = obstacle.getDestinationCoordinates();

       if(sourceCoordinates.getY_Coordinate() == destinationCoordinates.getY_Coordinate()){
           Integer y = sourceCoordinates.getY_Coordinate();
           for(Integer x = sourceCoordinates.getX_Coordinate(); x < destinationCoordinates.getX_Coordinate(); x++){
               spaceShipDeck.addPositionsbesideHorizontalObstacle(new Coordinates(x,(y-1)));
               spaceShipDeck.addPositionsbesideHorizontalObstacle(new Coordinates(x,y));
           }
       }
       else if(sourceCoordinates.getX_Coordinate() == destinationCoordinates.getX_Coordinate()){
            Integer x = sourceCoordinates.getX_Coordinate();
            for(Integer y = sourceCoordinates.getY_Coordinate(); y < destinationCoordinates.getY_Coordinate(); y++){
                spaceShipDeck.addPositionsbesideVerticalObstacle(new Coordinates((x -1),y));
                spaceShipDeck.addPositionsbesideVerticalObstacle(new Coordinates(x,y));
            }
        }

        //Anschließend Schleife, die durchläuft und einzelne x und y Koordinaten als Position speichert.
        //Diese Position wird durch Aufruf der Methode addPositionsbesideObstacle vom SpaceShipDeck der Liste positionsbesideObstacle hinzu gefügt

    }

}
