package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Repositories.FieldRepository;
import thkoeln.st.st2praktikum.exercise.Repositories.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.Repositories.TransportSystemRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;


import java.util.UUID;


@Service
public class MiningMachineService {
    //private final FieldRepository fieldRepository;
    private final MiningMachineRepository miningMachineRepository;
    private final MachineCommander machineCommander;

    public MiningMachineService(MiningMachineRepository miningMachineRepository, MachineCommander machineCommander) {
        //this.fieldRepository = fieldRepository;
        this.miningMachineRepository = miningMachineRepository;
        this.machineCommander = machineCommander;
    }
    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine machine = new MiningMachine(name);
        miningMachineRepository.save(machine);
        System.out.println(name);
        return machine.getMiningMachineId();
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
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        return machineCommander.executeCommand(miningMachineId,command);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        for (MiningMachine miningMachine : miningMachineRepository.findAll()) {
            if (miningMachine.getMiningMachineId() == miningMachineId) {
                return miningMachine.getFieldId();
            }
        }
        return null;
    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        for (MiningMachine miningMachine : miningMachineRepository.findAll()) {
            if (miningMachine.getMiningMachineId() == miningMachineId) {
                return miningMachine.getPoint();
            }
        }
        return null;
    }
}
