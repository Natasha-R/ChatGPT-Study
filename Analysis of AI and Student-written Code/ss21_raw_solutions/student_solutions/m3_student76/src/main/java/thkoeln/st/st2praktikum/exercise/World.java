package thkoeln.st.st2praktikum.exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.UUID;

@Component
@Transactional
public class World implements Creatable,Commandable{

    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;
    @Autowired
    private SpaceShipDeckRepository spaceShipDeckRepository;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;


    protected HashMap<UUID, MaintenanceDroid> maintenanceDroids = new HashMap<>();
    protected HashMap<UUID, SpaceShipDeck> spaceShipDecks = new HashMap<>();
    protected HashMap<UUID, TransportTechnology> technologies = new HashMap<>();


    public MaintenanceDroid retrieveMaintenanceDroid(UUID maintenanceDroid){
        return  maintenanceDroids.get(maintenanceDroid);
    }

    public SpaceShipDeck retrieveSpaceShipDeck(UUID spaceShipDeck){
        return spaceShipDecks.get(spaceShipDeck);
    }


    public  UUID retrieveOccupiedSpaceShipDeckId(UUID maintenanceDroid){
        return retrieveMaintenanceDroid(maintenanceDroid).getCurrentSpaceShipDeckUUID();
    }

    public Point retrievePoint(UUID maintenanceDroid){
        return retrieveMaintenanceDroid(maintenanceDroid).getPoint();
    }

    @Override
    public boolean movement(UUID maintenanceDroid, Command command) {
        int steps= command.getNumberOfSteps();
        if(command.getCommandType().equals(CommandType.NORTH)){
            maintenanceDroids.get(maintenanceDroid).moveNorth(steps);
        }else if(command.getCommandType().equals(CommandType.SOUTH)){
            maintenanceDroids.get(maintenanceDroid).moveSouth(steps);
        }else if(command.getCommandType().equals(CommandType.EAST)){
            maintenanceDroids.get(maintenanceDroid).moveEast(steps);
        }else if(command.getCommandType().equals(CommandType.WEST)){
            maintenanceDroids.get(maintenanceDroid).moveWest(steps);
        }
        return false;
    }

    @Override
    public boolean transport(UUID maintenanceDroid, Command command) {
        UUID destinationSpaceShipDeckId = command.getGridId();
        MaintenanceDroid droid= retrieveMaintenanceDroid(maintenanceDroid);
        Connection connexion = retrieveSpaceShipDeck(droid.getCurrentSpaceShipDeckUUID()).retrieveConnection(destinationSpaceShipDeckId);
        SpaceShipDeck destinationSpaceShipDeck= retrieveSpaceShipDeck(destinationSpaceShipDeckId);
        // droids' coordinate =src coordinate of connection
        if(connexion.getSourcePoint().equals(droid.getPoint())) {
            if (!destinationSpaceShipDeck.maintenanceDroids.isEmpty()) {
                for (int i = 0; i < destinationSpaceShipDeck.maintenanceDroids.size(); i++) {
                    //check if the destination point is free
                    if (destinationSpaceShipDeck.maintenanceDroids.get(i).getPoint().equals(connexion.getDestinationPoint())) {
                        throw new RuntimeException();
                    }
                }
            } else {
                //transfer droid from the source deck to the destination deck
                retrieveSpaceShipDeck(droid.getCurrentSpaceShipDeckUUID()).maintenanceDroids.remove(maintenanceDroids.get(maintenanceDroid));
                droid.setXY(connexion.getDestinationPoint().getX(), connexion.getDestinationPoint().getY());
                droid.setCurrentSpaceShipDeckUUID(destinationSpaceShipDeckId);
                droid.setCurrentSpaceShipDeck(retrieveSpaceShipDeck(destinationSpaceShipDeckId));
                return true;
            }
        }
        return false;
    }

    public void setMaintenanceDroid(UUID maintenanceDroid, UUID spaceShipDeck){
        retrieveMaintenanceDroid(maintenanceDroid).setCurrentSpaceShipDeckUUID(spaceShipDeck);
        retrieveMaintenanceDroid(maintenanceDroid).setXY(0,0);
        retrieveMaintenanceDroid(maintenanceDroid).setCurrentSpaceShipDeck(retrieveSpaceShipDeck(spaceShipDeck));
        retrieveSpaceShipDeck(spaceShipDeck).maintenanceDroids.add(retrieveMaintenanceDroid(maintenanceDroid));
    }

    @Override
    public boolean initialize(UUID maintenanceDroid, Command command) {
        UUID spaceShipDeck= command.getGridId();

        if(retrieveSpaceShipDeck(spaceShipDeck).maintenanceDroids.isEmpty()){
            setMaintenanceDroid(maintenanceDroid, spaceShipDeck);
            return  true;
        }else{
            for(int i = 0; i<retrieveSpaceShipDeck(spaceShipDeck).maintenanceDroids.size(); i++){
                if(retrieveSpaceShipDeck(spaceShipDeck).maintenanceDroids.get(i).getPoint().equals(new Point(0,0))){return false;}
                else {setMaintenanceDroid(maintenanceDroid,spaceShipDeck);
                    return true;}
            }
        }
        return false;
    }

    @Override
    public UUID createTransportTechnology(String technology){
            TransportTechnology createdTechnology = new TransportTechnology(technology);

            technologies.put(createdTechnology.getId(),createdTechnology);
            transportTechnologyRepository.save(createdTechnology);
            return createdTechnology.getId();
    }

    @Override
    public UUID createConnection(UUID sourceSpaceShipDeckId, Point sourcePoint,
                                 UUID destinationSpaceShipDeckId, Point destinationPoint) {

        Connection createdConnection = new Connection(sourceSpaceShipDeckId,destinationSpaceShipDeckId,sourcePoint,destinationPoint);
        SpaceShipDeck sourceSpaceDek= retrieveSpaceShipDeck(sourceSpaceShipDeckId);
        SpaceShipDeck destinationSpaceShipDeck = retrieveSpaceShipDeck(destinationSpaceShipDeckId);
        //make sure connection isn't out of bounds
        if(sourcePoint.getX() <= sourceSpaceDek.getWidth() && sourcePoint.getY()<= sourceSpaceDek.getHeight()){
            if(destinationPoint.getX()<= destinationSpaceShipDeck.getWidth() && destinationPoint.getY() <= destinationSpaceShipDeck.getHeight()){
                retrieveSpaceShipDeck(sourceSpaceShipDeckId).addConnection(createdConnection);
                return createdConnection.getId();
            }else throw new RuntimeException();
        }else throw new RuntimeException();
    }

    @Override
    public UUID createSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(width-1,height-1);
        spaceShipDecks.put(spaceShipDeck.getId(), spaceShipDeck);
        spaceShipDeckRepository.save(spaceShipDeck);
        return spaceShipDeck.getId();
    }

    @Override
    public void createBarrier(UUID spaceShipDeck, Barrier barrier) {
        retrieveSpaceShipDeck(spaceShipDeck).addBarrier(barrier);
    }

    @Override
    public UUID createMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid();
        maintenanceDroid.setName(name);
        maintenanceDroids.put(maintenanceDroid.getId(),maintenanceDroid);
        maintenanceDroidRepository.save(maintenanceDroid);
        return maintenanceDroid.getId();
    }

    public boolean canCommandBeExecuted(UUID maintenanceDroid, Command command){
        MaintenanceDroid maintenanceDroid1 = maintenanceDroidRepository.findById(maintenanceDroid);
        if (command.getCommandType().equals(CommandType.ENTER)){
            boolean returnedValue = initialize(maintenanceDroid,command);
            maintenanceDroid1 = maintenanceDroids.get(maintenanceDroid);
            maintenanceDroidRepository.save(maintenanceDroid1);
            return returnedValue;
        }
        else if (command.getCommandType().equals(CommandType.TRANSPORT)){
            boolean returnedValue = transport(maintenanceDroid,command);
            maintenanceDroid1 = maintenanceDroids.get(maintenanceDroid);
            maintenanceDroidRepository.save(maintenanceDroid1);
            return returnedValue;
        }
        else{
            boolean returnedValue = movement(maintenanceDroid,command);
            maintenanceDroid1 = maintenanceDroids.get(maintenanceDroid);
            maintenanceDroidRepository.save(maintenanceDroid1);
            return returnedValue;
        }
    }
}
