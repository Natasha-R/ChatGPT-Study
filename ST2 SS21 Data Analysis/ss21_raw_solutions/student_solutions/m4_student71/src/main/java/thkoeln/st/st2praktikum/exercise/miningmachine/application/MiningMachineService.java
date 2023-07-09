package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;


import javax.management.InvalidAttributeValueException;
import java.util.List;
import java.util.UUID;


@Service
public class MiningMachineService {



    private final FieldRepository fieldRepository;
    @Getter
    private final MiningMachineRepository miningMachineRepository;

    @Autowired
    public MiningMachineService(MiningMachineRepository miningMachineRepository, FieldRepository fieldRepository) {
        this.miningMachineRepository = miningMachineRepository;
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        var miningMachine = new MiningMachine(name);
        miningMachineRepository.save(miningMachine);
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
     *      (Movement commands are always successful, even if the mining machine hits a obstacle or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        try {

            MiningMachine tempMiningMachine = miningMachineRepository.findById(miningMachineId).get();

            if(command.getCommandType() == CommandType.ENTER)
            {
                val field = fieldRepository.findById(command.getGridId()).get();
                field.spawnMiningMachine(tempMiningMachine ,new Coordinate(0,0));
            }

            tempMiningMachine.executeCommand(command);


            miningMachineRepository.save(tempMiningMachine);

        } catch (InvalidAttributeValueException | SpawnMiningMachineException | TeleportMiningMachineException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return miningMachineRepository.findById(miningMachineId).get().getFieldId();
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId){
        return miningMachineRepository.findById(miningMachineId).get().getCoordinate();
    }

    public MiningMachine getMiningMachine(UUID miningMachineId){
        return miningMachineRepository.findById(miningMachineId).orElse(null);
    }

    public Iterable<MiningMachine> getALlMiningMachine(){
        return miningMachineRepository.findAll();
    }

    public Boolean deleteMiningMachine(UUID id){
        MiningMachine miningMachine = getMiningMachine(id);
        if( miningMachine == null)
            return false;
        else {
            getMiningMachineRepository().delete(miningMachine);
            return true;
        }
    }

}
