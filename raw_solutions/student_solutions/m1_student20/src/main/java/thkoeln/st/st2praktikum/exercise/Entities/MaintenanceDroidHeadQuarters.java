package thkoeln.st.st2praktikum.exercise.Entities;

import thkoeln.st.st2praktikum.exercise.Interfaces.Commandable;
import thkoeln.st.st2praktikum.exercise.Interfaces.CreateMaintenanceDroidable;

public class MaintenanceDroidHeadQuarters implements CreateMaintenanceDroidable, Commandable {

    @Override
public MaintenanceDroid createMaintenanceDroid(String name){
    MaintenanceDroid maintenanceDroid = new MaintenanceDroid();
    maintenanceDroid.setNameOfMaintenanceDroid(name);
    return maintenanceDroid;
}
    @Override
    public boolean droidGoSteps(MaintenanceDroid maintenanceDroid, String direction, Integer steps){
        Boolean goesTheFullSteps = true;
        if(maintenanceDroid.getXYCoordinatesOfMaintenanceDroid() == null || maintenanceDroid.getSpaceShipDeck() == null)return false;
        else {
            SpaceShipDeck actuallySpaceShipDeck = maintenanceDroid.getSpaceShipDeck();
            Coordinates coordinatesOfDroid = maintenanceDroid.getXYCoordinatesOfMaintenanceDroid();
            Integer x = coordinatesOfDroid.getX_Coordinate();
            Integer y = coordinatesOfDroid.getY_Coordinate();
            Integer limit ;

            switch (direction) {
                case "no": {
                    limit = y + steps;
                    while(y < limit && (y + 1)  < actuallySpaceShipDeck.getHeight()){

                        if((actuallySpaceShipDeck.positionIsbesideHorizontalObstacle(new Coordinates(x,y)) == true &&
                                actuallySpaceShipDeck.positionIsbesideHorizontalObstacle(new Coordinates(x, (y + 1))) == true)  ||
                                actuallySpaceShipDeck.containsBlockedPosition(new Coordinates(x, (y + 1))))

                        {
                            goesTheFullSteps = false;
                            break;}
                        y++;

                    }


                }
                break;
                case "ea": {
                    limit = x + steps;
                    while(x < limit && (x + 1)  < actuallySpaceShipDeck.getWidth()){

                        if((actuallySpaceShipDeck.positionIsbesideVerticalObstacle(new Coordinates(x,y)) == true &&
                                actuallySpaceShipDeck.positionIsbesideVerticalObstacle(new Coordinates((x+1), y )) == true)  ||
                                actuallySpaceShipDeck.containsBlockedPosition(new Coordinates((x+1), y )))
                        {
                            goesTheFullSteps = false;
                            break;}
                        x++;

                    }


                }
                break;
                case "so": {
                    limit = y - steps;
                    while(y > limit &&  (y-1)  >= 0){

                        if((actuallySpaceShipDeck.positionIsbesideHorizontalObstacle(new Coordinates(x,y)) == true &&
                                actuallySpaceShipDeck.positionIsbesideHorizontalObstacle(new Coordinates(x, (y - 1))) == true)  ||
                                actuallySpaceShipDeck.containsBlockedPosition(new Coordinates(x, (y - 1))))

                        {
                            goesTheFullSteps = false;
                            break;}
                        y--;

                    }

                }
                break;
                case "we": {

                    limit = x - steps;
                    while(x > limit && (x-1)  >= 0){

                        if((actuallySpaceShipDeck.positionIsbesideVerticalObstacle(new Coordinates(x,y)) == true &&
                                actuallySpaceShipDeck.positionIsbesideVerticalObstacle(new Coordinates((x-1), y)) == true)  ||
                                actuallySpaceShipDeck.containsBlockedPosition(new Coordinates((x-1), y)))

                        {
                            goesTheFullSteps = false;
                            break;}
                        x--;

                    }

                }
                break;
            }
            actuallySpaceShipDeck.giveFreeBlockedPosition(coordinatesOfDroid);
            maintenanceDroid.setXYPositionOfDroid(new Coordinates(x, y));
            actuallySpaceShipDeck.addBlockedPosition(maintenanceDroid.getXYCoordinatesOfMaintenanceDroid());
        }
        //Switch in dem geschaut wird, in welche direction es geht.
        //Wenn direction alle vier Himmelsrichtungen, dann einer der move Methoden von Schnittstellen Movable...
        //und Klasse Move aufrufen.
        //Methoden soll x und y Position des Droids und das Spaceshipdeck übergeben werden.
        //Rückgabe Position x und y
        // Einsetzen der Position beim Droid
        //Wenn Droid gesetzt werden soll, mit Methode containsblockedPosition des Spaceships herausfinden, ob Position 0,0 besetzt
        // wenn false zurückgegeben wird, MaintenanceDroid das Deck zuordnen und beim Schiff die Position als blockiert angeben.
        //Wenn Portal genutzt werden soll, Liste des Spaceshipdeck von Connection durchgehen
       return goesTheFullSteps;

    }
    @Override
    public boolean droidGoOverAnConnection(MaintenanceDroid maintenanceDroid, SpaceShipDeck destinationSpaceShipDeck){
        SpaceShipDeck actuallySpaceShipDeck = maintenanceDroid.getSpaceShipDeck();
        Coordinates coordinatesOfDroid = maintenanceDroid.getXYCoordinatesOfMaintenanceDroid();
        if(coordinatesOfDroid == null || actuallySpaceShipDeck == null)return false;
        else{
          Coordinates destinationCoordinates = actuallySpaceShipDeck.returnDestinationCoordinatesWhenConnectionAvailable(coordinatesOfDroid,destinationSpaceShipDeck);
          if(destinationCoordinates == null) return false;
          else{
              actuallySpaceShipDeck.giveFreeBlockedPosition(coordinatesOfDroid);
              maintenanceDroid.setXYPositionOfDroid(destinationCoordinates);
              destinationSpaceShipDeck.addBlockedPosition(maintenanceDroid.getXYCoordinatesOfMaintenanceDroid());
              maintenanceDroid.setSpaceShipDeck((SpaceShipDeck) destinationSpaceShipDeck);
              return true;
          }

        }


    }
    @Override
    public boolean droidGoOnSpaceShipDeck(MaintenanceDroid maintenanceDroid, SpaceShipDeck selectedSpaceShipDeck){
        if(maintenanceDroid.getSpaceShipDeck() != null && maintenanceDroid.getXYCoordinatesOfMaintenanceDroid() != null) return false;
        else {
            boolean isPosionBlocked = selectedSpaceShipDeck.containsBlockedPosition(new Coordinates(0, 0));
            if (isPosionBlocked == true) return false;
            else {
                maintenanceDroid.setSpaceShipDeck((SpaceShipDeck) selectedSpaceShipDeck);
                maintenanceDroid.setXYPositionOfDroid(new Coordinates(0, 0));
                selectedSpaceShipDeck.addBlockedPosition(maintenanceDroid.getXYCoordinatesOfMaintenanceDroid());
                return true;
            }
        }

    }

}
