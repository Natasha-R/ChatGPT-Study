package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.repository.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import java.util.Optional;
import java.util.UUID;


@Service
public class MiningMachineService {

    private MiningMachineRepository miningMachineRepository;
    private final FieldService fieldService;
    private MiningMachineDtoMapper miningMachineDtoMapper = new MiningMachineDtoMapper();

    @Autowired
    public MiningMachineService(MiningMachineRepository miningMachineRepository, FieldService fieldService) {
        this.miningMachineRepository = miningMachineRepository;
        this.fieldService = fieldService;
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        this.miningMachineRepository.save(miningMachine);
        return miningMachine.getUuid();
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
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        saveCommandInMiningMachine(miningMachineId, command);
        if (command.isInitialisationCommand()) {
            return initialiseMachine(miningMachineId, command);
        }

        if (command.isTransportCommand()) {
            return transportMachine(miningMachineId, command);
        }

        if (command.isMoveCommand()) {
            return moveMachine(miningMachineId, command);
        }

        return false;
    }

    private Boolean moveMachine(UUID miningMachineId, Command command) {
        // get Machine
        MiningMachine miningMachine = findById(miningMachineId);
        // get Field
        Field field = fieldService.findById(miningMachine.getFieldId());

        miningMachineRepository.save(field.moveDroid(miningMachineId, command));

        return true;
    }

    private Boolean transportMachine(UUID miningMachineId, Command command) {
        try {
            Field source = getFieldByMachineUUID(miningMachineId);
            MiningMachine miningMachine = findById(miningMachineId);
            Connection connection = source.transportDroid(miningMachine);

            Field destination = fieldService.findById(connection.getDestinationFieldId());
            miningMachine.setFieldId(destination.getId());
            return destination.addDroid(miningMachine, connection.getDestinationCoordinate());
        } catch (Exception e) {
            return false;
        }
    }

    private Field getFieldByMachineUUID(UUID machineUuid) throws Exception {
        for (Field field : fieldService.findAll()) {
            for (MiningMachine miningMachine : field.getMiningMachines()) {
                if (miningMachine.getUuid().equals(machineUuid)) {
                    return field;
                }
            }
        }

        throw new Exception("Field not found");
    }

    private Boolean initialiseMachine(UUID miningMachineId, Command command) {
        Field field = fieldService.findById(command.getGridId());

        if (field.canPlaceDroid()) {
            MiningMachine miningMachine = findById(miningMachineId);
            field.addMaintenanceDroid(miningMachine);
            miningMachine.setFieldId(field.getId());
            miningMachine.setCoordinates(new Point(0, 0));
            save(miningMachine);
            return true;
        }
        return false;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        Optional<MiningMachine> optionalMiningMachine = this.miningMachineRepository.findById(miningMachineId);
        if (optionalMiningMachine.isEmpty()) {
            throw new RuntimeException();
        }
        MiningMachine miningMachine = optionalMiningMachine.get();
        return miningMachine.getFieldId();
    }

    /**
     * This method returns the point a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        Optional<MiningMachine> optionalMiningMachine = this.miningMachineRepository.findById(miningMachineId);
        if (optionalMiningMachine.isEmpty()) {
            throw new RuntimeException();
        }
        MiningMachine miningMachine = optionalMiningMachine.get();
        return miningMachine.getPoint();
    }

    public Iterable<MiningMachine> findAll() {
        return miningMachineRepository.findAll();
    }

    public MiningMachine findById(UUID id) {
        return miningMachineRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No miningMachine with ID " + id + " can be found."));
    }

    public MiningMachine createMiningMachineFromDto(MiningMachineDto miningMachineDto) {
        MiningMachine monster = miningMachineDtoMapper.mapToEntity(miningMachineDto);
        save(monster);
        return monster;
    }

    public void save(MiningMachine miningMachine) {
        miningMachineRepository.save(miningMachine);
    }

    public void deleteById(UUID id) {
        MiningMachine miningMachine = miningMachineRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No miningMachine with ID " + id + " can be found."));
        miningMachineRepository.delete(miningMachine);
    }

    private void saveCommandInMiningMachine(UUID miningMachineId, Command command) {
        MiningMachine miningMachine = this.findById(miningMachineId);
        miningMachine.getCommands().add(command);
        miningMachineRepository.save(miningMachine);
    }
}
