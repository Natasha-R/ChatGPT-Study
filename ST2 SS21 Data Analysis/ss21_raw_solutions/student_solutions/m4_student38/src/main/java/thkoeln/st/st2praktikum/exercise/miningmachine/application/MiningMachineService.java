package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;


import java.util.*;


@Service
public class MiningMachineService {

    @Autowired
    private MiningMachineRepository miningMachineRepository;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;

    private HashMap<UUID, MiningMachine> miningMachines;
    private HashMap<UUID, Field> fields = new HashMap<>();
    private List<Connection> connections = new LinkedList<>();
    
    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        if (miningMachines == null)
            miningMachines = new HashMap<>();

        UUID miningMachineID = UUID.randomUUID();
        MiningMachine miningMachine = new MiningMachine(miningMachineID, name);
        miningMachines.put(miningMachineID,miningMachine);
        miningMachineRepository.save(miningMachine);
        return miningMachineID;
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a obstacle or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        if (miningMachineId == null || command == null)
            throw new IllegalArgumentException();

        Iterable<Field> fields_IT = fieldRepository.findAll();
        Iterable<TransportTechnology> transportTechnologies_IT = transportTechnologyRepository.findAll();
        for (Field field : fields_IT)
            fields.put(field.getFieldID(),field);
        for (TransportTechnology transportTechnology : transportTechnologies_IT){
            connections.addAll(transportTechnology.getConnections());
        }

        boolean terminated = miningMachines.get(miningMachineId).executeCommand(command,fields,connections);
        miningMachineRepository.deleteById(miningMachineId);
        miningMachineRepository.save(miningMachines.get(miningMachineId));
        return terminated;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        if (miningMachineId == null)
            throw new IllegalArgumentException();
        return miningMachines.get(miningMachineId).getFieldId();
    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        if (miningMachineId == null)
            throw new IllegalArgumentException();
        return miningMachines.get(miningMachineId).getLocation();
    }

    public MiningMachine getMiningMachineById(UUID id) {
        Optional<MiningMachine> miningMachineOptional = miningMachineRepository.findById(id);
        if (miningMachineOptional.isPresent())
            return miningMachineOptional.get();
        else
            throw new IllegalArgumentException("Miningmachine not found!");

    }

    public Iterable<MiningMachine> getMiningMachines() {
        return miningMachineRepository.findAll();
    }

    public MiningMachine createMiningMachine(MiningMachine miningMachine) {
        miningMachine.setId(UUID.randomUUID());
        miningMachineRepository.save(miningMachine);
        return miningMachine;
    }

    public boolean deleteById(UUID id) {
        if (miningMachineRepository.findById(id).isPresent()){
            miningMachineRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public MiningMachine changeName(UUID id, String name) {
        Optional<MiningMachine> miningMachine = miningMachineRepository.findById(id);
        if (miningMachine.isEmpty()) throw new IllegalArgumentException("Not a good name");
        miningMachine.get().setName(name);
        miningMachineRepository.save(miningMachine.get());
        return miningMachine.get();
    }

    public void giveCommand(UUID id, Command command) {
        Optional<MiningMachine> miningMachine = miningMachineRepository.findById(id);
        if (miningMachine.isPresent()){
            miningMachine.get().commands.add(command);
            if (command.getCommandType() == CommandType.ENTER){
                miningMachine.get().spawnOnField(fieldRepository.findById(command.getGridId()).get());
            } else{
                miningMachine.get().move(command,fieldRepository.findById(miningMachine.get().getFieldId()).get());
            }
        } else{
            throw new IllegalArgumentException("Illegal command!");
        }
    }

    public List<Command> getCommandList(UUID id) {
        Optional<MiningMachine> miningMachine = miningMachineRepository.findById(id);
        if (!miningMachine.isEmpty()){
            return miningMachine.get().commands;
        }
        else{
            return new LinkedList<>();
        }
    }

    public void deleteCommands(UUID id) {
        Optional<MiningMachine> miningMachine = miningMachineRepository.findById(id);
        miningMachine.ifPresent(machine -> machine.commands.clear());
    }
}
