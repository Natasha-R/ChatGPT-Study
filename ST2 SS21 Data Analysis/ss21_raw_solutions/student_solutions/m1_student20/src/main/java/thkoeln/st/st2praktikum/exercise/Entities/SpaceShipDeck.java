package thkoeln.st.st2praktikum.exercise.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

import java.util.UUID;

@Entity
public class SpaceShipDeck {

    private ArrayList<Connection> connectionOfThisSpaceShipDeck = new ArrayList<Connection>() ;
    private ArrayList<Coordinates> blockedPositions = new ArrayList<>();
    private ArrayList<Coordinates> positionsbesideHorizontalObstacle = new ArrayList<>();
    private ArrayList<Coordinates> positionsbesideVerticalObstacle = new ArrayList<>();
    public void addBlockedPosition(Coordinates coordinates) {
        blockedPositions.add(coordinates);
    }

    public boolean containsBlockedPosition(Coordinates selectedCoordinates){
        for(Coordinates coordinates :blockedPositions){
            if(coordinates.getX_Coordinate() == selectedCoordinates.getX_Coordinate() && coordinates.getY_Coordinate() == selectedCoordinates.getY_Coordinate()) return true;
        }
        return false;
    }

    public void giveFreeBlockedPosition(Coordinates coordinates){
        blockedPositions.remove(coordinates);
    }

    public void addPositionsbesideHorizontalObstacle(Coordinates coordinates) {
        positionsbesideHorizontalObstacle.add(coordinates);
    }

    public void addPositionsbesideVerticalObstacle(Coordinates coordinates) {
       positionsbesideVerticalObstacle.add(coordinates);
    }

    public boolean positionIsbesideVerticalObstacle(Coordinates coordinatesOfDroid){
        for(Coordinates positions : positionsbesideVerticalObstacle){
            if(positions.getY_Coordinate() == coordinatesOfDroid.getY_Coordinate() && positions.getX_Coordinate() == coordinatesOfDroid.getX_Coordinate())return true;
        }
        return false;
    }

    public boolean positionIsbesideHorizontalObstacle(Coordinates coordinatesOfDroid){
        for(Coordinates positions : positionsbesideHorizontalObstacle){
            if(positions.getY_Coordinate() == coordinatesOfDroid.getY_Coordinate() && positions.getX_Coordinate() == coordinatesOfDroid.getX_Coordinate())return true;
        }
        return false;
    }

    @Id
    protected UUID spaceShipDeck_Id;
    public UUID getSpaceShipDeck_Id(){
        return this.spaceShipDeck_Id;
    }


    public void addConnectionOnSpaceShipDeck(Connection connection) {
        connectionOfThisSpaceShipDeck.add(connection);
    }

    public Coordinates returnDestinationCoordinatesWhenConnectionAvailable(Coordinates coordinatesOfDroid, SpaceShipDeck destinationSpaceShipDeck){
        for(Connection connection : connectionOfThisSpaceShipDeck){
            if(connection.getSourceCoordinates().getX_Coordinate() == coordinatesOfDroid.getX_Coordinate() &&
                    connection.getSourceCoordinates().getY_Coordinate() == coordinatesOfDroid.getY_Coordinate() && connection.getDestinationSpaceShipDeck() == destinationSpaceShipDeck ){

             return connection.getDestinationCoordinates();

            }

        }
        return null;
    }

    public SpaceShipDeck(){
        spaceShipDeck_Id = UUID.randomUUID();
    }

    private Integer height;

    public void setHeight(Integer height){
        if(height < 0)height *= -1;
        this.height = height;
    }

    public Integer getHeight(){
        return this.height;
    }


    private Integer width;

    public void setWidth(Integer width){
        if(width < 0)width *= -1;
        this.width = width;
    }

    public Integer getWidth(){
        return this.width;
    }

    public SpaceShipDeck(Integer height, Integer width){
        this.height = height;
        this.width = width;
    }




}
