package thkoeln.st.st2praktikum.exercise;
import java.util.*;

public class GameInstance {

    ArrayList<SpaceShipDeck> decks = new ArrayList();
    ArrayList<Droid> droids = new ArrayList();
    ArrayList<Portal> portals = new ArrayList();
    private int endmap;


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

    public void addWall(UUID mapID, Wall wall){

        int field = findMapByID(mapID);
        decks.get(field).createWall(wall);
    }

    public boolean moveDroid(UUID droidID, Order order) {

            boolean status=true;
            int droid ;
            int map;

            droid=findDroidByID(droidID);

            switch (order.getOrderType()) {

                case ENTER:
                    droid=findDroidByID(droidID);
                    map = findMapByID(order.getGridId());
                    if (decks.get(map).field[0][0].droid) {
                        status = false;
                    }else{

                        droids.get(droid).spawnDroid(order.getGridId(), decks.get(map).field);
                    }
                    break;
                case  TRANSPORT:

                    status=false;
                    for (Portal portal : portals) {
                        if (portal.endMapID.compareTo(order.getGridId()) == 0 && portal.startMapID.compareTo(droids.get(droid).coordZ) == 0) {
                            if (portal.start.getX() == droids.get(droid).coordX && portal.start.getY() == droids.get(droid).coordY) {
                                int startmap = findMapByID(portal.startMapID);
                                int endmap = findMapByID(portal.endMapID);

                                if(decks.get(endmap).field[portal.ende.getY()][portal.ende.getX()].droid==true){
                                   System.out.println("Ist belegt");
                                    break;
                                }

                                decks.get(endmap).field[portal.ende.getY()][portal.ende.getX()].droid=true;
                                decks.get(startmap).field[portal.start.getY()][portal.start.getX()].droid=false;

                                droids.get(droid).coordZ = portal.endMapID;
                                droids.get(droid).coordY = portal.ende.getY();
                                droids.get(droid).coordX = portal.ende.getX();

                                status = true;
                            }
                            break;
                        }
                    }
                    break;
                default:
                    map=findMapByDroidsPosition(droid);
                    droids.get(droid).moveDroid(order, decks.get(map));
            }
            return status;
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

    public UUID getSpaceShipIDOfDroid(UUID droidID){
        int droid=findDroidByID(droidID);
        return droids.get(droid).coordZ;
    }

    public int getCoordx(UUID droidID){
        int droid=findDroidByID(droidID);
        return droids.get(droid).coordX;
    }

    public int getCoordy(UUID droidID){
        int droid=findDroidByID(droidID);
        return droids.get(droid).coordY;
    }
}