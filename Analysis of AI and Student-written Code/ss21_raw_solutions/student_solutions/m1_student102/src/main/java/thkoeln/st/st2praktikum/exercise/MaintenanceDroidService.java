package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class MaintenanceDroidService {
    ArrayList<Barrier> allBarrierList=new ArrayList<>();
    ArrayList<SpaceShip> spaceShipList=new ArrayList<>();
    ArrayList<Connection> connectionPointList=new ArrayList<>();
    ArrayList<Droid> droidList=new ArrayList<>();

    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShip spaceShip=new SpaceShip(height,width);
        spaceShipList.add(spaceShip);
        return spaceShip.getId();
    }

    /**
     * This method adds a barrier to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID spaceShipDeckId, String barrierString) {
        int firstPointX,firstPointY,secondPointX,secondPointY;
        char alignmentType=' ';

        firstPointX=Integer.parseInt(barrierString.substring(1,2) );
        firstPointY=Integer.parseInt(barrierString.substring(3,4) );
        secondPointX=Integer.parseInt(barrierString.substring(7,8) );
        secondPointY=Integer.parseInt(barrierString.substring(9,10) );

        if(firstPointX==secondPointX)
            alignmentType='V';
        if(firstPointY==secondPointY)
            alignmentType='H';
        Barrier limit=new Barrier(spaceShipDeckId,alignmentType,firstPointX,firstPointY,secondPointX,secondPointY);
        allBarrierList.add(limit);
    }

    /**     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {

        Connection connexion=new Connection(sourceCoordinate,destinationCoordinate,sourceSpaceShipDeckId,destinationSpaceShipDeckId);
        connectionPointList.add(connexion);
        return connexion.getConnectionID();
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        Droid droid=new Droid(name);
        droidList.add(droid);
        return droid.getId();
    }

    /**
     * @param number,from,to 3 integers to check an interval
     * @return an interval if a number is in this one
     */
    public Boolean isInInterval(int number, int from ,int to ){
        if(from<=number && number<to)
            return true;
        return to <= number && number < from;
    }
    public Boolean isDroidOnInitialPosition(UUID idSpaceship){
        for(Droid tmp:droidList){
            if(tmp.getPositionX()==0 && tmp.getPositionY()==0 && idSpaceship.equals(tmp.getIdSpaceShip()))
                return true;
        }
        return false;
    }
    public int getSpaceShipMaxValue(UUID idSpaceship){
        int res=0;
        for(SpaceShip tmp:spaceShipList){
            if(tmp.getId().equals(idSpaceship)){
                res=tmp.getX();
            }
        }
        return  res;
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another spaceShipDeck
     * "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {
        Droid droid=new Droid();
        String command=commandString.substring(1,3);


        for(Droid tmp : droidList){
            if(maintenanceDroidId.equals(tmp.getId()) )
                droid=tmp;
        }
        if(command.equals("ea")){
            MooveDroidToEast moove=new MooveDroidToEast(droid,spaceShipList,allBarrierList);
            moove.updateDroidPosition(commandString);
            return true;
        }
        if(command.equals("we")){
            MooveDroidToWest moove=new MooveDroidToWest(droid,allBarrierList);
            moove.updateDroidPosition(commandString);
            return true;
        }
        if(command.equals("no")){
            MooveDroidToNorth moove=new MooveDroidToNorth(droid,allBarrierList,spaceShipList,commandString);
            moove.updateDroidPosition();
            return true;
        }
        if(command.equals("so")){
            MooveDroidToSouth moove=new MooveDroidToSouth(droid,allBarrierList,commandString);
            moove.updateDroidPosition();
            return true;
        }
        if(command.equals("tr")){
            Traversable moove=new Traversable(droid,connectionPointList);
            return moove.updateDroidPosition(commandString);
        }
        if(command.equals("en")){

            DroidInitialisable moove=new DroidInitialisable(droidList);
            Boolean state=moove.updateDroidList(commandString);
            droidList= moove.getDroidList();
            return  state;
        }
        return false;
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        for(Droid tmp : droidList){
            if(tmp.getId()==maintenanceDroidId)
                return tmp.getIdSpaceShip();
        }
        return null;
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId){
        for(Droid tmp : droidList){
            if(tmp.getId()==maintenanceDroidId)
                return "("+tmp.getPositionX()+","+tmp.getPositionY()+")";
        }
        return "";
    }
}
