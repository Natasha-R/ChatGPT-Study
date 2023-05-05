package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.*;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;
import java.util.UUID;


@Service
public class MiningMachineService {

    MiningMachinelist miningMachinelist = new MiningMachinelist();
    Machinecoordinateshashmap machinecoordinateshashmap = new Machinecoordinateshashmap();
    Fieldminingmachinehashmap fieldminingmachinehashmap = new Fieldminingmachinehashmap();

    private MiningMachineDtoMapper miningMachineDtoMapper = new MiningMachineDtoMapper();

    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    MiningMachineRepository miningMachineRepository;
    @Autowired
    TransportCategoryRepository transportCategoryRepository;





    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        UUID miningmachineuuid = UUID.randomUUID();
        MiningMachine newMiningMachine = new MiningMachine(name, miningmachineuuid);
        miningMachineRepository.save(newMiningMachine);
        miningMachinelist.add(newMiningMachine);
        return miningmachineuuid;
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {



        for(MiningMachine machine : miningMachinelist.getMiningmachinelist()) {
            if(machine.getId().equals(miningMachineId)) {
                UUID fieldwheremachineis = machine.getFieldId();
                OrderType moveCommand = order.getOrderType();
                Integer steps = order.getNumberOfSteps();
                UUID UUID = order.getGridId();
                machine.getOrderlist().add(order);
                machine.setCoordinate(machine.executeCommand(moveCommand,steps, UUID, miningMachinelist ,
                        miningMachineRepository, fieldwheremachineis, machine.getId(), machinecoordinateshashmap, fieldRepository, transportCategoryRepository));


                miningMachineRepository.save(machine);

                if(!machine.isExecutefailer()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return fieldminingmachinehashmap.getFieldminingmachinehashmap().get(miningMachineId);

    }

    /**
     * This method returns the coordinate a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId){
        Iterable<MiningMachine> miningMachines = miningMachineRepository.findAll();
        thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate coordinates = null;
        for(MiningMachine miningMachine : miningMachines){

            if(miningMachine.getId() == miningMachineId) {
                coordinates = miningMachine.getCoordinate();
            }
        }
        return coordinates;
    }

    public MiningMachine createMiningMachineFromDto(MiningMachineDto miningMachineDto) {
        MiningMachine miningMachine =   miningMachineDtoMapper.mapToEntity( miningMachineDto );
        save( miningMachine );
        return miningMachine;
    }


    public void save( MiningMachine miningMachine ) {
        // first, persist the dungeon with a cascade to the fields ...
        miningMachineRepository.save( miningMachine );
    }

    public MiningMachine findById( UUID id ) {
        return miningMachineRepository.findById( id ).orElseThrow( () ->
                new InvalidStringError( "No dungeon with ID " + id + " can be found.") );
    }

}
