package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.TestComponent;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;


@Service
public class MiningMachineService {
    public final MiningMachineRepository miningMachineRepository;

    @Autowired
    public MiningMachineService(MiningMachineRepository miningMachineRepository) {
        this.miningMachineRepository = miningMachineRepository;
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        UUID uuid = UUID.randomUUID();
        miningMachineRepository.save(new MiningMachine(uuid, name));
        return uuid;
    }

    /**
     * This method lets the mining machine execute a order.
     *
     * @param miningMachineId the ID of the mining machine
     * @param order           the given order
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on squares with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a wall or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        MiningMachine machine = miningMachineRepository.findById(miningMachineId).orElse(null);
        if (machine == null) {
            return false;
        }
        Pair<Boolean, MiningMachine> pair = TestComponent.miningMachineComponent.onMovement(machine, order);
        if (pair.getFirst())
            miningMachineRepository.save(machine);
        machine.getOrders().add(order);
        miningMachineRepository.save(machine);
        return pair.getFirst();
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        MiningMachine miningMachine = miningMachineRepository.findById(miningMachineId).orElse(null);
        if (miningMachine == null)
            return null;
        return miningMachine.getId();
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId) {
        MiningMachine miningMachine = miningMachineRepository.findById(miningMachineId).orElse(null);
        if (miningMachine == null)
            return null;
        return miningMachine.getCoordinate();
    }
}
