package thkoeln.st.st2praktikum.exercise.util;

import org.springframework.data.util.Pair;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.MiningMachine;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.field.FieldConnection;
import thkoeln.st.st2praktikum.exercise.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {
    private final WallManager wallManager;
    private final MovementManager movementManager;
    private final Map<UUID, MiningMachine> machines;
    private final Map<UUID, Field> fields;

    public GameManager() {
        this.wallManager = new WallManager();
        this.movementManager = new MovementManager(wallManager);
        this.machines = new HashMap<>();
        this.fields = new HashMap<>();
    }

    public Pair<Boolean, MiningMachine> onMovement(MiningMachine machine, Field field, Order order) {
        boolean tmp = true;
        switch (order.getOrderType()) {
            case NORTH:
            case SOUTH:
                return Pair.of(true, movementManager.moveY(machine, field, order));
            case EAST:
            case WEST:
                return Pair.of(true, movementManager.moveX(machine, field, order));
            case ENTER:
                if (machines.entrySet().stream().anyMatch(uuidMiningMachineEntry -> uuidMiningMachineEntry.getValue().getFieldId() != null && uuidMiningMachineEntry.getValue().getFieldId().equals(order.getGridId()) && uuidMiningMachineEntry.getValue().getCoordinate().equals(new Coordinate(0, 0)))) {
                    tmp = false;
                    break;
                }
                machine = new MiningMachine(new Coordinate(0, 0), order.getGridId());
                break;
            case TRANSPORT:
                if(field == null) {
                    tmp = false;
                    break;
                }
                MiningMachine finalMachine = machine;
                Map.Entry<UUID, FieldConnection> fieldConnectionEntry = field.getFieldConnections().entrySet().stream().filter(entry -> entry.getValue().getSourceCoordinate().equals(finalMachine.getCoordinate()) && entry.getValue().getSourceField().getUuid().equals(finalMachine.getFieldId())).findFirst().orElse(null);
                if (fieldConnectionEntry == null) {
                    tmp = false;
                    break;
                }
                FieldConnection fieldConnection = fieldConnectionEntry.getValue();
                if (!fieldConnection.getSourceCoordinate().equals(machine.getCoordinate())) {
                    tmp = false;
                    break;
                }
                if (machines.entrySet().stream().anyMatch(uuidMiningMachineEntry -> uuidMiningMachineEntry.getValue().getFieldId() != null && uuidMiningMachineEntry.getValue().getFieldId().equals(order.getGridId()) && uuidMiningMachineEntry.getValue().getCoordinate().equals(fieldConnection.getDestinationCoordinate()))) {
                    tmp = false;
                    break;
                }
                machine = new MiningMachine(fieldConnection.getDestinationCoordinate(), fieldConnection.getDestinationField().getUuid());
                break;
        }
        return Pair.of(tmp, machine);
    }

    public WallManager getWallManager() {
        return wallManager;
    }

    public MovementManager getMovementManager() {
        return movementManager;
    }

    public Map<UUID, MiningMachine> getMachines() {
        return machines;
    }

    public Map<UUID, Field> getFields() {
        return fields;
    }
}
