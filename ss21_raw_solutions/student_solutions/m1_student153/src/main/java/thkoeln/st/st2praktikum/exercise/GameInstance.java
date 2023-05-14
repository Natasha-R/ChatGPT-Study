package thkoeln.st.st2praktikum.exercise;
import java.util.*;

public class GameInstance {

    ArrayList<SpaceShipDeck> decks = new ArrayList();
    ArrayList<Droid> droids = new ArrayList();
    ArrayList<Portal> portals = new ArrayList();

    public void createDroid(Droid droid) {
        droids.add(droid);
    }

    public void createPortal(Portal portal) {
        portals.add(portal);
    }

    public void createDeck(SpaceShipDeck spd) {
        decks.add(spd);
    }

    public void createBorder(int count) {
        decks.get(count).createBorder();
    }

    public void createWall(UUID mapID, String command) {

        int field = findMapByID(mapID);
        decks.get(field).createWall(command);
    }

    public boolean moveDroid(UUID droidID, String command) {

        boolean status = true;
        int droid ;
        int map;
        UUID mapID=new UUID(2,9);

        String firstCommand = command.substring(1, 3);
        String secondCommand = (command.substring(4, command.length() - 1));

        if(secondCommand.length()>1) {
            mapID = UUID.fromString(secondCommand);
        }

        droid=findDroidByID(droidID);
        map=findMapByID(mapID);

        switch (firstCommand) {

            case "en":
                if (decks.get(map).field[0][0].droid) {
                    status=false;
                } else {
                    droids.get(droid).spawnDroid(mapID, decks.get(map).field);
                }
                break;

            case "tr":
                status=false;
                for (Portal portal : portals) {
                    if (portal.endMapID.compareTo(mapID) == 0 && portal.startMapID.compareTo(droids.get(droid).coordZ) == 0) {
                        if (portal.startCoordX == droids.get(droid).coordX && portal.startCoordY == droids.get(droid).coordY) {
                            droids.get(droid).coordZ = portal.endMapID;
                            droids.get(droid).coordY = portal.endCoordY;
                            droids.get(droid).coordX = portal.endCoordX;
                            status = true;
                        }
                        break;
                    }
                }
                break;

            default:
                map=findMapByDroidsPosition(droid);
                droids.get(droid).moveDroid(firstCommand, secondCommand, decks.get(map));
        }
        return status;
    }

    public UUID getSpaceShipIDOfDroid(UUID droidID){
        int droid=findDroidByID(droidID);
        return droids.get(droid).coordZ;
 }

    public String getCoords(UUID droidID){
        int droid=findDroidByID(droidID);
        return "("+droids.get(droid).coordX+","+droids.get(droid).coordY+")";
 }

 public int findDroidByID(UUID droidID){
     int droid=0;
     for (int count = 0; count < droids.size(); count++) {
         if (droids.get(count).droidID.compareTo(droidID) == 0) {
              droid = count;
             break;
         }
     }
 return droid;
 }

 public int findMapByID (UUID mapID){
     int map=0;
     for (int count= 0; count< decks.size(); count++) {
         if (decks.get(count).mapID.compareTo(mapID) == 0) {
             map = count;
             break;
         }
     }
     return map;
 }

 public int findMapByDroidsPosition(int droid){
     int map=0;
     for (int count = 0; count < decks.size(); count++) {
         if (decks.get(count).mapID.compareTo(droids.get(droid).coordZ) == 0) {
             map = count;
             break;
         }
     }
return map;
 }

}