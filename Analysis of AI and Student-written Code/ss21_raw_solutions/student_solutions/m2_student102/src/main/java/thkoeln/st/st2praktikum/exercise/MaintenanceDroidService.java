package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exception.BarrierException;
import thkoeln.st.st2praktikum.exercise.exception.ConnexionException;

import java.util.ArrayList;
import java.util.UUID;

public class MaintenanceDroidService {


    ArrayList<Barrier> allBarrier=new ArrayList<>();
    ArrayList<SpaceShip> allSpaceShip=new ArrayList<>();
    ArrayList<Connection> allConnectionPoint=new ArrayList<>();
    ArrayList<Droid> allDroid=new ArrayList<>();
    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShip spaceShip=new SpaceShip(height,width);
        allSpaceShip.add(spaceShip);
        return spaceShip.getId();
    }

    /**
     * This method adds a barrier to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceShipDeckId, Barrier barrier) {
        char alignmentType=' ';
        barrier.setIdSpaceShip(spaceShipDeckId);

        if( barrier.getStart().getX().equals( barrier.getEnd().getX() ) )
            alignmentType='V';
        if( barrier.getStart().getY().equals( barrier.getEnd().getY() ) )
            alignmentType='H';
        barrier.setBarrierType(alignmentType);
        if( getSpaceShipById(spaceShipDeckId).getX() <= barrier.getStart().getX() || getSpaceShipById(spaceShipDeckId).getY() <= barrier.getStart().getY() )
            throw new BarrierException("choose a valid number");
        else if( getSpaceShipById(spaceShipDeckId).getX() <= barrier.getEnd().getX() || getSpaceShipById(spaceShipDeckId).getY() <= barrier.getEnd().getY()  )
            throw new BarrierException("choose a valid number");
        else
            allBarrier.add(barrier);
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourcePoint the points of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationPoint the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        Connection connexion=new Connection(sourcePoint,destinationPoint,sourceSpaceShipDeckId,destinationSpaceShipDeckId);
        if( getSpaceShipById(sourceSpaceShipDeckId).getX() <=sourcePoint.getX() || getSpaceShipById(sourceSpaceShipDeckId).getY()<=sourcePoint.getY())
            throw new ConnexionException("choose a valid number");
        else if( getSpaceShipById( destinationSpaceShipDeckId ).getX() <=destinationPoint.getX() || getSpaceShipById( destinationSpaceShipDeckId ).getY()<=destinationPoint.getY())
            throw new ConnexionException("choose a valid number");
        else
            allConnectionPoint.add(connexion);
        return connexion.getConnectionID();
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        Droid droide=new Droid(name);
        allDroid.add(droide);
        return droide.getId();
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another spaceShipDeck
     * ENTER for setting the initial spaceShipDeck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Command command) {
        Droid droid=new Droid();
        for(Droid tmp : allDroid){
            if(maintenanceDroidId.equals(tmp.getId()) )
                droid=tmp;
        }
        switch (command.getCommandType()){
            case NORTH:
                MooveDroidToNorth mooveNorth=new MooveDroidToNorth(droid,allBarrier,allSpaceShip);
                mooveNorth.updateDroidPosition(command);
                return true;
            case SOUTH:
                MooveDroidToSouth mooveSouth=new MooveDroidToSouth(droid,allBarrier);
                mooveSouth.updateDroidPosition(command);
                return true;
            case EAST:
                MooveDroidToEast  mooveEast=new MooveDroidToEast(droid,allSpaceShip,allBarrier);
                mooveEast.updateDroidPosition(command);
                return true;
            case WEST:
                MooveDroidToWest mooveWest=new MooveDroidToWest(droid,allBarrier);
                mooveWest.updateDroidPosition(command);
                return true;
            case ENTER:
                DroidInitialisable initDroid=new DroidInitialisable(allDroid);
                Boolean state=initDroid.updateDroidList(command);
                allDroid= initDroid.getDroidList();
                return  state;
            case TRANSPORT:
                Traversable droidTransport=new Traversable(droid,allConnectionPoint);
                 return droidTransport.updateDroidPosition(command);

            default:
                return false;
        }
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        for(Droid tmp : allDroid){
            if(tmp.getId()==maintenanceDroidId)
                return tmp.getIdSpaceShip();
        }
        return null;
    }

    /**
     * This method returns the points a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the points of the maintenance droid
     */
    public Point getPoint(UUID maintenanceDroidId){
        Point p=new Point();
        for(Droid tmp: allDroid) {
            if(maintenanceDroidId.equals( tmp.getId() ) ) {
                p.setX(tmp.getPositionX());
                p.setY(tmp.getPositionY());
            }
        }
        return p;
    }
    public SpaceShip getSpaceShipById(UUID idSpaceShip){
        for(SpaceShip tmp : allSpaceShip){
            if( idSpaceShip.equals(tmp.getId()) )
                return tmp;
        }
        return null;
    }
}
