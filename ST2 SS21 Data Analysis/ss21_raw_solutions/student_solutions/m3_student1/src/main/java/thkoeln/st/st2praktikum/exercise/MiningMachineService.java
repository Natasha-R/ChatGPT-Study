package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class MiningMachineService {

    private MiningMachineRepository miningMachineRepository;
    private FieldRepository fieldRepository;
    private ConnectionRepository connectionRepository;
    private TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    public MiningMachineService(MiningMachineRepository miningMachineRepository, FieldRepository fieldRepository,
                              ConnectionRepository connectionRepository, TransportTechnologyRepository transportTechnologyRepository) {
        this.miningMachineRepository = miningMachineRepository;
        this.fieldRepository = fieldRepository;
        this.connectionRepository = connectionRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field newField = new Field(width, height);
        fieldRepository.save(newField);
        return newField.getId();    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        fieldRepository.findById(fieldId).get().addBarrier(barrier);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology newTransportTechnology = new TransportTechnology(technology);
        transportTechnologyRepository.save(newTransportTechnology);
        return newTransportTechnology.getId();    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        Connection newConnection = new Connection(
                sourceFieldId,
                sourcePoint,
                destinationFieldId,
                destinationPoint
        );
        connectionRepository.save(newConnection);
        return transportTechnologyRepository
                .findById(transportTechnologyId)
                .stream()
                .findFirst()
                .orElse(null)
                .addConnection(newConnection);    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine newTidyUpRobot = new MiningMachine(name);
        miningMachineRepository.save(newTidyUpRobot);
        return newTidyUpRobot.getId();    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        MiningMachine tidyUpRobot = miningMachineRepository.findById(miningMachineId).get();

        switch (command.getCommandType()) {
            case ENTER:
                List<MiningMachine> robotsInTargetRoom = miningMachineRepository.findByFieldIdAndFieldIdIsNotNull(command.getGridId());
                System.out.println(tidyUpRobot.getId());
                return tidyUpRobot.placeInInitialRoom(command, robotsInTargetRoom);
            case TRANSPORT:
                TransportTechnology transportTechnology =
                        transportTechnologyRepository
                                .findByConnectionsSourceRoomIdAndConnectionsSourcePoint(tidyUpRobot.getFieldId(), tidyUpRobot.getPoint()).stream()
                                .findFirst()
                                .orElse(null);
                List<MiningMachine> robotsInDestinationRoom = miningMachineRepository.findByFieldIdAndFieldIdIsNotNull(command.getGridId());
                return tidyUpRobot.transport(command, transportTechnology, robotsInDestinationRoom);
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                Field currentRoom = fieldRepository.findById(tidyUpRobot.getFieldId()).get();
                List<MiningMachine> robotsInCurrentRoom = miningMachineRepository.findByFieldIdAndFieldIdIsNotNull(currentRoom.getId());
                tidyUpRobot.walk(command, currentRoom, robotsInCurrentRoom); // TODO: 18.06.2021
                return true;
            default:
                throw new IllegalArgumentException();
        }    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return miningMachineRepository
                .findById(miningMachineId)
                .get()
                .getFieldId();    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        return miningMachineRepository
                .findById(miningMachineId)
                .get()
                .getPoint();    }
}
