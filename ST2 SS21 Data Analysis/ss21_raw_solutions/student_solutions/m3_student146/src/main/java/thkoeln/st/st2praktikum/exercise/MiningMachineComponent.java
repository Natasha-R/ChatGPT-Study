package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class MiningMachineComponent {
    private MiningMachineService service;
    private MovementManager movementManager;

    @Autowired
    public MiningMachineComponent(MiningMachineService service) {
        this.service = service;
        this.movementManager = new MovementManager(new WallManager());
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
                for (MiningMachine miningMachine : service.miningMachineRepository.findAll()) {
                    if (miningMachine.getFieldId() != null) {
                        if (miningMachine.getFieldId().equals(order.getGridId())) {
                            if (miningMachine.getCoordinate().equals(new Coordinate(0, 0))) {
                                return Pair.of(false, machine);
                            }
                        }
                    }
                }
                machine.setFieldId(field.getId());
                break;
            case TRANSPORT:
                if (field == null) {
                    tmp = false;
                    break;
                }
                System.out.println("1");
                MiningMachine finalMachine = machine;
                System.out.println(machine.getCoordinate());
                System.out.println(field.getFieldConnections().size());
                field.getFieldConnections().forEach((uuid, fieldConnection) -> {
                    System.out.println(fieldConnection.getSourceCoordinate());
                });
                Map.Entry<UUID, FieldConnection> fieldConnectionEntry = field.getFieldConnections().entrySet().stream().filter(entry -> entry.getValue().getSourceCoordinate().equals(finalMachine.getCoordinate()) && entry.getValue().getSourceFieldId().equals(finalMachine.getFieldId())).findFirst().orElse(null);
                if (fieldConnectionEntry == null) {
                    tmp = false;
                    break;
                }
                System.out.println("2");
                FieldConnection fieldConnection = fieldConnectionEntry.getValue();
                if (!fieldConnection.getSourceCoordinate().equals(machine.getCoordinate())) {
                    tmp = false;
                    break;
                }
                System.out.println("3");
                for (MiningMachine miningMachine : service.miningMachineRepository.findAll()) {
                    if (miningMachine.getFieldId() != null) {
                        if (miningMachine.getFieldId().equals(order.getGridId())) {
                            if (!miningMachine.getCoordinate().equals(fieldConnection.getSourceCoordinate())) {
                                return Pair.of(false, machine);
                            }
                        }
                    }
                }
                System.out.println("4");
                machine.setFieldId(fieldConnection.getDestinationFieldId());
                machine.setCoordinate(fieldConnection.getDestinationCoordinate());
                break;
        }
        return Pair.of(tmp, machine);
    }
}
