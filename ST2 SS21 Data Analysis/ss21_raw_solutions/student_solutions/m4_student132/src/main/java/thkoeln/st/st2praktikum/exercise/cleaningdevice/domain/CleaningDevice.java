package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Entity
public class CleaningDevice {

    @Id
    private final UUID cleaningMachineId = UUID.randomUUID();

    @Setter
    private String name;

    @Setter
    private UUID spaceId;

    @Embedded
    @Setter
    private Coordinate coordinate;

    private Boolean spawned = false;

    @Transient
    @Setter
    private List<Order> orders = new ArrayList<>();

    public CleaningDevice(String name) {
        this.name = name;
    }

    protected CleaningDevice() {
    }


    public Boolean executeCommand(Order order, CleaningDeviceRepository cleaningDeviceRepository, SpaceRepository spaceRepository) {
        this.orders.add(order);
        switch (order.getOrderType()) {
            case NORTH:
            case SOUTH:
                return this.goVertikal(order.getNumberOfSteps(), cleaningDeviceRepository, spaceRepository, order.getOrderType());
            case EAST:
            case WEST:
                return this.goHorizontal(order.getNumberOfSteps(), cleaningDeviceRepository, spaceRepository, order.getOrderType());
            case TRANSPORT:
                return this.transport(order.getGridId(), cleaningDeviceRepository, spaceRepository);
            case ENTER:
                return this.enterSpace(order.getGridId(), cleaningDeviceRepository, spaceRepository);
        }
        throw new IllegalStateException("cleaningMachine.executeCommand()");
    }

    private Boolean goHorizontal(Integer numberOfSteps, CleaningDeviceRepository cleaningDeviceRepository, SpaceRepository spaceRepository, OrderType orderType) {
        for (int i = 0; i < numberOfSteps; i++) {
            this.printStatus();
            if (spaceRepository.findById(this.spaceId).get().checkVerticalObstacles(this.coordinate, orderType)) {
                if (!spaceRepository.findById(this.spaceId).get().checkCleaningMachinesInDirection(this.coordinate, cleaningDeviceRepository, orderType, this.cleaningMachineId)) {
                    switch (orderType) {
                        case EAST:
                            this.coordinate = new Coordinate((this.coordinate.getX() + 1), this.coordinate.getY());
                            break;
                        case WEST:
                            System.out.println(this.coordinate.getX());
                            this.coordinate = new Coordinate((this.coordinate.getX() - 1), this.coordinate.getY());
                            break;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private Boolean goVertikal(Integer numberOfSteps, CleaningDeviceRepository cleaningDeviceRepository, SpaceRepository spaceRepository, OrderType orderType) {
        for (int i = 0; i < numberOfSteps; i++) {
            this.printStatus();
            if (spaceRepository.findById(this.spaceId).get().checkHorizontalObstacles(this.coordinate, orderType)) {
                if (!spaceRepository.findById(this.spaceId).get().checkCleaningMachinesInDirection(this.coordinate, cleaningDeviceRepository, orderType, this.cleaningMachineId)) {
                    switch (orderType) {
                        case NORTH:
                            this.coordinate = new Coordinate(this.coordinate.getX(), (this.coordinate.getY() + 1));
                            break;
                        case SOUTH:
                            this.coordinate = new Coordinate(this.coordinate.getX(), (this.coordinate.getY() - 1));
                            break;
                    }
                }
            }
        }
        return true;
    }

    private Boolean transport(UUID gridId, CleaningDeviceRepository cleaningDeviceRepository, SpaceRepository spaceRepository) {

        AtomicReference<Boolean> result = new AtomicReference<>(false);
        //Für jede connection auf dem Feld wo der Droid steht...
        for (Map.Entry<UUID, Connection> connectionEntry : spaceRepository.findById(this.spaceId).get().getConnections().entrySet()) {
            System.out.println(connectionEntry.getValue().getSourceCoordinate());
            //wenn das aktuelle Feld auf dem der Droid steht ein Connection Feld ist...
            if (connectionEntry.getValue().getSourceCoordinate().equals(this.coordinate)) {
                //Für jeden Droid auf der Welt
                for (CleaningDevice cleaningMachineEntry : cleaningDeviceRepository.findAll()) {//Wenn er gespawned ist...
                    if (cleaningMachineEntry.getSpawned()) {
                        //Wenn er auf dem connection destination feld steht...
                        if (cleaningMachineEntry.getCoordinate().equals(connectionEntry.getValue().getDestinationCoordinate())) {
                            result.set(false);
                            break;
                        } else {
                            this.spaceId = connectionEntry.getValue().getDestinationSpaceId();
                            this.coordinate = connectionEntry.getValue().getDestinationCoordinate();
                            result.set(true);
                            break;
                        }
                    }
                }
            }
        }
        return result.get();
    }

    private Boolean enterSpace(UUID spaceId, CleaningDeviceRepository cleaningDeviceRepository, SpaceRepository spaceRepository) throws IllegalStateException {

        AtomicReference<Boolean> result = new AtomicReference<>(false);

        for (CleaningDevice cleaningMachineEntry : cleaningDeviceRepository.findAll()) {
            if (!cleaningMachineEntry.getCleaningMachineId().equals(this.cleaningMachineId)) {
                if (cleaningMachineEntry.getSpawned()) {
                    if (cleaningMachineEntry.getCoordinate().equals(new Coordinate(0, 0))) {
                        result.set(false);
                        break;
                    } else {
                        this.spawned = true;
                        this.coordinate = new Coordinate(0, 0);
                        this.spaceId = spaceId;
                        result.set(true);
                        break;
                    }
                } else {
                    this.spawned = true;
                    this.coordinate = new Coordinate(0, 0);
                    this.spaceId = spaceId;
                    result.set(true);
                    break;
                }
            }
        }
        return result.get();
    }

    private void printStatus() {
        System.out.println("SpaceId: " + this.spaceId);
        System.out.println("Coordinate: " + this.coordinate.toString());
        System.out.println("=========================");
    }
}
