package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldConnection;
import thkoeln.st.st2praktikum.exercise.miningmachine.application.MiningMachineService;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class MiningMachineComponent {
    private MiningMachineService service;
    private FieldService fieldService;
    private TransportTechnologyService technologyService;
    private MovementManager movementManager;

    public MiningMachineComponent(MiningMachineService service, FieldService fieldService, TransportTechnologyService technologyService) {
        this.service = service;
        this.fieldService = fieldService;
        this.technologyService = technologyService;
        this.movementManager = new MovementManager(new WallManager());
    }

    public Pair<Boolean, MiningMachine> onMovement(MiningMachine machine, Order order) {
        boolean tmp = true;
        Field field;
        if (machine.getFieldId() != null)
            field = fieldService.fieldRepository.findById(machine.getFieldId()).orElse(null);
        else
            field = fieldService.fieldRepository.findById(order.getGridId()).orElse(null);
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
                machine.setFieldId(Objects.requireNonNull(field).getId());
                break;
            case TRANSPORT:
                if (field == null) {
                    tmp = false;
                    break;
                }
                field.getFieldConnections().forEach((uuid, fieldConnection) -> System.out.println(fieldConnection.getSourceCoordinate()));
                AtomicReference<FieldConnection> fieldConnection = new AtomicReference<>(null);
                System.out.println(technologyService.transportTechnologyRepo.count());
                technologyService.transportTechnologyRepo.findAll().forEach(transportTechnology -> {
                    if (fieldConnection.get() == null) {
                        for (FieldConnection connection : transportTechnology.getConnection()) {
                            if (connection.getSourceFieldId().equals(machine.getFieldId()))
                                fieldConnection.set(connection);
                        }
                    }
                });
                if (fieldConnection.get() == null) {
                    tmp = false;
                    break;
                }
                if (!fieldConnection.get().getSourceCoordinate().equals(machine.getCoordinate())) {
                    tmp = false;
                    break;
                }
                for (MiningMachine miningMachine : service.miningMachineRepository.findAll()) {
                    if (miningMachine.getFieldId() != null) {
                        if (miningMachine.getFieldId().equals(order.getGridId())) {
                            if (!miningMachine.getCoordinate().equals(fieldConnection.get().getSourceCoordinate())) {
                                return Pair.of(false, machine);
                            }
                        }
                    }
                }
                machine.setFieldId(fieldConnection.get().getDestinationFieldId());
                machine.setCoordinate(fieldConnection.get().getDestinationCoordinate());
                break;
        }
        return Pair.of(tmp, machine);
    }
}
