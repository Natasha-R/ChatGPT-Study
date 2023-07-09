package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Direction;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Collidable;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class MiningMachineService {

    MiningMachineRepository miningMachineRepository;

    TransportTechnologyRepository transportTechnologyRepository;
    FieldRepository fieldRepository;

    @Autowired
    public MiningMachineService(MiningMachineRepository miningMachineRepository,
                                TransportTechnologyRepository transportTechnologyRepository,
                                FieldRepository fieldRepository){

        this.miningMachineRepository = miningMachineRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.fieldRepository = fieldRepository;
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

        machine.addCommand(command);

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

    private boolean moveMachineMultipleSteps(
            MiningMachine machine,
            Direction direction,
            int steps
    ){
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

    public Iterable<MiningMachine> findAll(){
        return miningMachineRepository.findAll();
    }

    public Optional<MiningMachine> getMiningMachineById(UUID id){
        return miningMachineRepository.findById(id);
    }

    public void deleteMachine(UUID id){
        miningMachineRepository.deleteById(id);
    }

}
