package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.field.application.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;


import java.util.ArrayList;
import java.util.UUID;


@Service
public class MiningMachineService {
    private MiningMachineRepository miningMachineRepository;
    private FieldRepository fieldRepository;
    private MiningMachineDtoMapper mapper = new MiningMachineDtoMapper();

    @Autowired
    public MiningMachineService(MiningMachineRepository miningMachineRepository, FieldRepository fieldRepository) {
        this.miningMachineRepository = miningMachineRepository;
        this.fieldRepository = fieldRepository;
    }

    public Iterable<MiningMachine> findAllMm(){
        return miningMachineRepository.findAll();
    }

    public MiningMachine patchMmFromDto(UUID id, MiningMachineDto miningMachineDto){
        MiningMachine miningMachine = findMmById(id);
        MiningMachineDtoMapper mapper = new MiningMachineDtoMapper();
        mapper.mapToExistingEntity(miningMachineDto, miningMachine, true);
        return miningMachine;
    }

    public MiningMachine findMmById(UUID id) {
        return miningMachineRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No MiningMachine with ID " + id + " can be found."));
    }

    public void deleteMmById(UUID id) {
        miningMachineRepository.deleteById(id);
    }

    public Field findFieldById(UUID id) {
        return fieldRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No Field with ID " + id + " can be found."));
    }

    public MiningMachine addMiningMachineFromDto(MiningMachineDto miningMachineDto){
        MiningMachine miningMachine = mapper.mapToEntity(miningMachineDto);
        miningMachine.setMiningMachineId(UUID.randomUUID());
        miningMachineRepository.save(miningMachine);
        return miningMachine;
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        UUID miningMachineId = UUID.randomUUID();
        miningMachineRepository.save(new MiningMachine(miningMachineId, name));
        return miningMachineId;
    }

    /**
     * This method lets the mining machine execute a order.
     *
     * @param miningMachineId the ID of the mining machine
     * @param order           the given order
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on grid cells with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        findMmById(miningMachineId).addOrder(order);
        if (order.getOrderType() == OrderType.ENTER) {
            return entryMiningMachine(miningMachineId, order.getGridId());
        }
        if (order.getOrderType() == OrderType.TRANSPORT) {
            return transportMiningMachine(miningMachineId, order.getGridId());
        } else {
            return moveMiningMachine(miningMachineId, order.getOrderType(), order.getNumberOfSteps());
        }
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return findMmById(miningMachineId).getFieldId();
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId) {
        return findMmById(miningMachineId).getCoordinate();
    }

    //

    public Boolean moveMiningMachine(UUID miningMachineId, OrderType orderType, Integer steps) {
        MiningMachine miningMachine = findMmById(miningMachineId);
        Field field = findFieldById(miningMachine.getFieldId());
        MiningMachine newMm = field.moveMiningMachine(miningMachine, orderType, steps, (ArrayList<MiningMachine>) miningMachineRepository.findAll());
        if (newMm == null) {
            return false;
        }
        miningMachine = newMm;
        miningMachineRepository.save(miningMachine);
        return true;

    }

    public Boolean transportMiningMachine(UUID miningMachineId, UUID destinationFieldId) {
        MiningMachine miningMachine = findMmById(miningMachineId);
        Field field = findFieldById(miningMachine.getFieldId());
        MiningMachine newMiningMachine = field.transportMiningMachine(miningMachine, destinationFieldId, (ArrayList<MiningMachine>) miningMachineRepository.findAll());
        if (newMiningMachine != null) {
            miningMachine = newMiningMachine;
            miningMachineRepository.save(miningMachine);
            return true;
        }
        return false;
    }

    public Boolean entryMiningMachine(UUID miningMachineId, UUID fieldId) {
        Field field = findFieldById(fieldId);
        MiningMachine miningMachine = findMmById(miningMachineId);
        for (MiningMachine otherMiningMachine : miningMachineRepository.findAll()) {
            if (fieldId.equals(otherMiningMachine.getFieldId())) {
                if (otherMiningMachine.getCoordinate().getX() == 0 && otherMiningMachine.getCoordinate().getY() == 0) {
                    return false;
                }
            }
        }
        miningMachine.setFieldId(fieldId);
        miningMachine.setCoordinate(new Coordinate(0, 0));
        miningMachineRepository.save(miningMachine);
        return true;
    }
}
