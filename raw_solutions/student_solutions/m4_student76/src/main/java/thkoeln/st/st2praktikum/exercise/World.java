package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.ConnectionRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.UUID;

@Service
@Transactional
public class World implements Creatable, Commandable {


    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;
    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;
    @Autowired
    private SpaceShipDeckRepository spaceShipDeckRepository;
    @Autowired
    private ConnectionRepository connectionRepository;


    private HashMap<UUID, SpaceShipDeck> spaces = new HashMap<>();
    private HashMap<UUID, MaintenanceDroid> maintenanceDroids = new HashMap<>();
    private HashMap<UUID, TransportTechnology> transportTechnologies = new HashMap<>();


    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroid){
        return fetchMaintenanceDroidById(maintenanceDroid).getSpaceId();
    }
    public MaintenanceDroid getMaintenanceDroidById(UUID maintenanceDroid){
        return maintenanceDroidRepository.getMaintenanceDroidById(maintenanceDroid);
    }
    public Point getPoints(UUID maintenanceDroidId){
        return fetchMaintenanceDroidById(maintenanceDroidId).getPoint();
    }
    private SpaceShipDeck fetchSpaceById(UUID spaceId){
        return spaces.get(spaceId);
    }
    private MaintenanceDroid fetchMaintenanceDroidById(UUID maintenanceDroid){
        return maintenanceDroids.get(maintenanceDroid);
    }


    @Override
    public UUID createConnection(UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        Connection createdConnection =  new Connection(sourceSpaceShipDeckId, destinationSpaceShipDeckId,sourcePoint,destinationPoint);
        SpaceShipDeck souSpace = fetchSpaceById(sourceSpaceShipDeckId);
        SpaceShipDeck desSpace = fetchSpaceById(destinationSpaceShipDeckId);

        if (sourcePoint.getX()<=souSpace.getWidth() && sourcePoint.getY()<=souSpace.getHeight()){

            if (destinationPoint.getX()<=desSpace.getWidth() && destinationPoint.getY()<= desSpace.getHeight()) {
                fetchSpaceById(sourceSpaceShipDeckId).addConnection(createdConnection);
                return createdConnection.getId();
            }else throw new RuntimeException();
        }else throw new RuntimeException();
    }

    @Override
    public UUID createSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(width-1,height-1);
        spaces.put(spaceShipDeck.getId(), spaceShipDeck);
        spaceShipDeckRepository.save(spaceShipDeck);
        return spaceShipDeck.getId();
    }

    @Override
    public UUID createMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid();

        maintenanceDroid.setName(name);
        maintenanceDroids.put(maintenanceDroid.getId(),maintenanceDroid);
        maintenanceDroidRepository.save(maintenanceDroid);
        return maintenanceDroid.getId();    }

    @Override
    public void createBarrier(UUID space, Barrier barrier) {
        fetchSpaceById(space).addBarrier(barrier);
    }

    public UUID createTransportTechnology(String technology){
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologies.put(transportTechnology.getId(),transportTechnology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getId();
    }
    public boolean canTheCommandExecute(UUID maintenanceDroid, Command command){
        MaintenanceDroid maintenanceDroid1 = maintenanceDroidRepository.findById(maintenanceDroid).get();
        if (command.getCommandType().equals(CommandType.ENTER)){
            boolean returnedValue = initialize(maintenanceDroid,command);
            maintenanceDroid1 = maintenanceDroids.get(maintenanceDroid);
            maintenanceDroid1.addCommand(command);
            maintenanceDroidRepository.save(maintenanceDroid1);
            return returnedValue;
        }
        else if (command.getCommandType().equals(CommandType.TRANSPORT)){
            boolean returnedValue = transport(maintenanceDroid,command);
            maintenanceDroid1 = maintenanceDroids.get(maintenanceDroid);
            maintenanceDroid1.addCommand(command);
            maintenanceDroidRepository.save(maintenanceDroid1);
            return returnedValue;
        }
        else{
            boolean returnedValue = movement(maintenanceDroid,command);
            maintenanceDroid1 = maintenanceDroids.get(maintenanceDroid);
            maintenanceDroid1.addCommand(command);
            maintenanceDroidRepository.save(maintenanceDroid1);
            return returnedValue;
        }
    }

    @Override
    public boolean movement(UUID maintenanceDroid, Command command) {
        int steps = command.getNumberOfSteps();
        if (command.getCommandType().equals(CommandType.NORTH)){
            maintenanceDroids.get(maintenanceDroid).moveNorth(steps);
        }
        else if (command.getCommandType().equals(CommandType.SOUTH)){
            maintenanceDroids.get(maintenanceDroid).moveSouth(steps);
        }
        else if (command.getCommandType().equals(CommandType.WEST)){

            maintenanceDroids.get(maintenanceDroid).moveWest(steps);
        }
        else if (command.getCommandType().equals(CommandType.EAST)){
            maintenanceDroids.get(maintenanceDroid).moveEast(steps);
        }

        return false;
    }

    @Override
    public boolean transport(UUID maintenanceDroid, Command command) {
        UUID destinationSpaceId = command.getGridId();
        MaintenanceDroid device = fetchMaintenanceDroidById(maintenanceDroid);
        Connection Connection = fetchSpaceById(device.getSpaceId()).fetchConnectionByDestinationSpaceId(destinationSpaceId);
        SpaceShipDeck desSpace = fetchSpaceById(destinationSpaceId);


        if (Connection.getSourcePoint().equals(device.getPoint())) {
            if (!desSpace.getMaintenanceDroids().isEmpty()){
                for (int i = 0; i<desSpace.getMaintenanceDroids().size(); i++){

                    if (desSpace.getMaintenanceDroids().get(i).getPoint().equals(Connection.getDestinationPoint())){
                        throw new  RuntimeException();
                    }
                }
            } else {


                fetchSpaceById(device.getSpaceId()).getMaintenanceDroids().remove(maintenanceDroids.get(maintenanceDroid));
                device.setXY(Connection.getDestinationPoint().getX(), Connection.getDestinationPoint().getY());
                device.setSpaceShipDeckId(destinationSpaceId);
                device.setCurrentSpaceShipDeck(fetchSpaceById(destinationSpaceId));
                return true;
            }
        }
        return false;
    }

    private void setMaintenanceDroid(UUID maintenanceDroid, UUID spaceShipDeck){
        fetchMaintenanceDroidById(maintenanceDroid).setSpaceShipDeckId(spaceShipDeck);
        fetchMaintenanceDroidById(maintenanceDroid).setXY(0,0);
        fetchMaintenanceDroidById(maintenanceDroid).setCurrentSpaceShipDeck(fetchSpaceById(spaceShipDeck));
        fetchSpaceById(spaceShipDeck).getMaintenanceDroids().add(fetchMaintenanceDroidById(maintenanceDroid));
    }

    @Override
    public boolean initialize(UUID maintenanceDroid, Command command) {
        UUID spaceShipDeck = command.getGridId();

        if (fetchSpaceById(spaceShipDeck).getMaintenanceDroids().isEmpty()) {
            setMaintenanceDroid(maintenanceDroid, spaceShipDeck);
            return true;
        }else{
            for (int i = 0; i<fetchSpaceById(spaceShipDeck).getMaintenanceDroids().size(); i++) {
                if (fetchSpaceById(spaceShipDeck).getMaintenanceDroids().get(i).getPoint().equals(new Point(0,0))) return false;
                else {
                    setMaintenanceDroid(maintenanceDroid, spaceShipDeck);
                    return true;
                }
            }
        }
        return false;
    }

}
