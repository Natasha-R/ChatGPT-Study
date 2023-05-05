package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.connexion.Connexion;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.spaceship.SpaceShip;
import thkoeln.st.st2praktikum.exercise.spaceship.SpaceShipRepository;

import java.util.ArrayList;
import java.util.UUID;


@Service
public class MaintenanceDroidService {
    @Autowired
    SpaceShipRepository spaceShipRepository;
    @Autowired
    MaintenanceDroidRepository maintenanceDroidRepository;
    @Autowired
    AddDroidInSpaceship addDroidInSpaceship;
    @Autowired
    Transport transport;
    @Autowired
    MoveToEast moveToEast;
    @Autowired
    MoveToNorth moveToNorth;
    @Autowired
    MoveToSouth moveToSouth;
    @Autowired
    MoveToWest moveToWest;
    private ArrayList<Connexion> allConnectionPoint = new ArrayList<>();
    private ArrayList<Barrier> allBarrier = new ArrayList<>();

    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShip spaceShip=new SpaceShip();
        spaceShip.setHeight(height);
        spaceShip.setWidth(width);
        //make consistent
        spaceShipRepository.save(spaceShip);
        return spaceShip.getSpaceChipId();
    }

    /**
     * This method adds a barrier to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceShipDeckId, Barrier barrier) {
        char alignmentType = ' ';
        barrier.setIdSpaceShip(spaceShipDeckId);

        if (barrier.getStart().getX().equals(barrier.getEnd().getX()))
            alignmentType = 'V';
        if (barrier.getStart().getY().equals(barrier.getEnd().getY()))
            alignmentType = 'H';
        barrier.setBarrierType(alignmentType);
        if (getSpaceShip(spaceShipDeckId).getHeight() <= barrier.getStart().getX() || getSpaceShip(spaceShipDeckId).getWidth() <= barrier.getStart().getY())
            throw new RuntimeException("choose a valid number");
        else if (getSpaceShip(spaceShipDeckId).getHeight() <= barrier.getEnd().getX() || getSpaceShip(spaceShipDeckId).getWidth() <= barrier.getEnd().getY())
            throw new RuntimeException("choose a valid number");
        else
            allBarrier.add(barrier);
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        return UUID.randomUUID();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        /*
        System.out.println("Je suis la");
        Connexion connexion=new Connexion();
        connexion.setTransportCategoryId( transportCategoryId );
        connexion.setIdSource( sourceSpaceShipDeckId );
        connexion.setPointSource( sourcePoint );
        connexion.setIdDestination( destinationSpaceShipDeckId );
        connexion.setPointDestination( destinationPoint );
        connexionRepository.save( connexion ) ;
        System.out.println("Tadaaaaaaaa J'ai fini'");
        return connexion.getConnectionID();
         */
        Connexion connexion = new Connexion(sourcePoint,destinationPoint,sourceSpaceShipDeckId,destinationSpaceShipDeckId,transportCategoryId);
        if (getSpaceShip(sourceSpaceShipDeckId).getHeight() <= sourcePoint.getX() || getSpaceShip(sourceSpaceShipDeckId).getWidth() <= sourcePoint.getY())
            throw new RuntimeException("choose a valid number");
        else if (getSpaceShip(destinationSpaceShipDeckId).getHeight()<= destinationPoint.getX() || getSpaceShip(destinationSpaceShipDeckId).getWidth() <= destinationPoint.getY())
            throw new RuntimeException("choose a valid number");
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
        MaintenanceDroid maintenanceDroid=new MaintenanceDroid();
        maintenanceDroid.setPoint( new Point(0,0) );
        maintenanceDroid.setName( name );
        //making Consistent
        maintenanceDroidRepository.save( maintenanceDroid );
        return maintenanceDroid.getDroidID();
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another spaceship deck
     * ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Command command) {
        //addDroidInSpaceship.exec(maintenanceDroidId);
        //System.out.println( " My Droid "+ maintenanceDroidRepository.findAll().iterator().next().getDroidID() );

        switch (command.getCommandType()) {
            case NORTH:
                moveToNorth.setAllBarrier(allBarrier);
                moveToNorth.updateDroidPosition(maintenanceDroidId,command);
                return true;
            case SOUTH:
                moveToSouth.setAllBarrier(allBarrier);
                moveToSouth.updateDroidPosition(maintenanceDroidId,command);
                return true;
            case EAST:
                moveToEast.setAllBarrier(allBarrier);
                moveToEast.updateDroidPosition(maintenanceDroidId,command);
                return true;
            case WEST:
                moveToWest.setAllBarrier(allBarrier);
                moveToWest.updateDroidPosition(maintenanceDroidId,command);
                return true;
            case ENTER:
                Boolean state = addDroidInSpaceship.updateDroidList( maintenanceDroidId , command) ;
                return state;
            case TRANSPORT:
                transport.setAllConnectionPoint(allConnectionPoint);
                return transport.updateDroidPosition(maintenanceDroidId , command) ;
            default:
                return false;
        }
/*
        if ( command.getCommandType() == CommandType.EAST ) {
            moveToEast.setAllBarrier(allBarrier);
            moveToEast.updateDroidPosition(maintenanceDroidId,command);
            return true;
        }
        if ( command.getCommandType() == CommandType.ENTER ) {
            addDroidInSpaceship.exec( maintenanceDroidId , command) ;
            return true;
        }
        if ( command.getCommandType() == CommandType.TRANSPORT ) {
            transport.setAllConnectionPoint(allConnectionPoint);
            transport.updateDroidPosition(maintenanceDroidId , command) ;
            return true;
        }
        return false;

 */
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return maintenanceDroidRepository.findById(maintenanceDroidId).get().getSpaceShipDeckId();
    }

    /**
     * This method returns the point a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the point of the maintenance droid
     */
    public Point getMaintenanceDroidPoint(UUID maintenanceDroidId){
        return maintenanceDroidRepository.findById(maintenanceDroidId).get().getPoint();
    }
    public SpaceShip getSpaceShip(UUID idSpaceShip){
        return spaceShipRepository.findById(idSpaceShip).get();
    }
}
