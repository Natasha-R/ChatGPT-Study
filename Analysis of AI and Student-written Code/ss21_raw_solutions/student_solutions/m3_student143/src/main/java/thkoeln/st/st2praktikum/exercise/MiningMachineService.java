package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
public class MiningMachineService {

    FieldRepository fieldRepository;
    MiningMachineRepository miningMachineRepository;
    TransportTechnologyRepository transportTechnologyRepository;


    @Autowired
    public MiningMachineService(
            FieldRepository fieldRepository,
            MiningMachineRepository miningMachineRepository,
            TransportTechnologyRepository transportTechnologyRepository
    ){

        this.fieldRepository = fieldRepository;
        this.miningMachineRepository = miningMachineRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field fieldToSave = new Field(new Dimension(width,height));
        fieldRepository.save(fieldToSave);
        return fieldToSave.getId();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        fieldRepository
                .findById(fieldId)
                .orElseThrow()
                .addWall(wall);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        this.transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(
            UUID transportTechnologyId,
            UUID sourceFieldId,
            Coordinate sourceCoordinate,
            UUID destinationFieldId,
            Coordinate destinationCoordinate
        ) {

        Connection connection = new Connection(
                fieldRepository.findById(sourceFieldId).orElseThrow(),
                fieldRepository.findById(destinationFieldId).orElseThrow(),
                sourceCoordinate,
                destinationCoordinate
        );

        TransportTechnology technology =
        this.transportTechnologyRepository
               .findById(transportTechnologyId)
               .orElseThrow();

        technology.addConnection(connection);

        return connection.getId();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name, new Coordinate(0,0));
        miningMachineRepository.save(miningMachine);
        return miningMachine.getId();
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {

        MiningMachine machine =  miningMachineRepository
                .findById(miningMachineId)
                .orElseThrow();

        switch (command.getCommandType()){
            case NORTH:
                return moveMachineMultipleSteps(machine,Direction.NORTH,command.getNumberOfSteps());
            case WEST:
                return moveMachineMultipleSteps(machine,Direction.WEST,command.getNumberOfSteps());
            case SOUTH:
                return moveMachineMultipleSteps(machine,Direction.SOUTH,command.getNumberOfSteps());
            case EAST:
                return moveMachineMultipleSteps(machine,Direction.EAST,command.getNumberOfSteps());
            case TRANSPORT:
                return transportMachine(machine,fieldRepository.findById(command.getGridId()).orElseThrow());
            case ENTER:
                return moveMachineToField(
                        machine,fieldRepository.findById(command.getGridId())
                                .orElseThrow(),
                        new Coordinate(0,0)
                );
        }

        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return miningMachineRepository
                .findById(miningMachineId)
                .orElseThrow()
                .getFieldId();
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId){
        return miningMachineRepository
                .findById(miningMachineId)
                .orElseThrow()
                .getCoordinate();
    }



    private boolean moveMachineToField(MiningMachine machine, Field fieldToEnter, Coordinate destinationCoordinate){
        boolean isBlocked = false;
        for(MiningMachine otherMachine : miningMachineRepository.findAll()){
            if(otherMachine != machine &&
                    otherMachine.getField() == fieldToEnter &&
                    otherMachine.getCoordinate().equals(destinationCoordinate)
            ){
                isBlocked = true;
            }
        }
        if(!isBlocked){
            machine.setCoordinate(destinationCoordinate);
            machine.setField(fieldToEnter);
            return true;
        }
        return false;
    }

    private boolean moveMachineMultipleSteps(MiningMachine machine, Direction direction, int steps){
        if(direction == Direction.NONE) { return false; }

        List<Collidable> collidables = machine.getField().getCollidables();
        for(MiningMachine m : miningMachineRepository.findAll()){
            if(m.getField() != null &&
                    m.getFieldId() == machine.getFieldId() &&
                    m.getId() != machine.getId()){
                collidables.add(m);
            }
        }
        while (steps > 0){
            boolean machineIsColliding = false;
            for(Collidable collidable : collidables){
                if(machine.isCollidingWith(collidable,direction)){
                    machineIsColliding = true;
                }
            }
            if(!machineIsColliding){
                machine.setCoordinate(machine.nextCoordinate(direction));
            }
            steps--;
        }
        return true;
    }

    private boolean transportMachine(MiningMachine machine, Field fieldToTransportTo){
        if(fieldToTransportTo == null){return false;}

        Connection connectionToUse = null;
        for(TransportTechnology technology : transportTechnologyRepository.findAll()){
            for(Connection connection : technology.getConnections()){
                if(connection.getSourceField().getId().equals(machine.getField().getId())
                        && connection.getSourceCoordinate().equals(machine.getCoordinate())
                        && connection.getDestinationField().equals(fieldToTransportTo)
                ){
                    connectionToUse = connection;
                }
            }
        }
        if(connectionToUse == null){ return false; }

        return moveMachineToField(machine,
                connectionToUse.getDestinationField(),
                connectionToUse.getDestinationCoordinate()
        );
    }


}
