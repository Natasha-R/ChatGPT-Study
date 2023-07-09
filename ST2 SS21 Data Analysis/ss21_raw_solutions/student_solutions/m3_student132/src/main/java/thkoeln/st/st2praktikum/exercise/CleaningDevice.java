package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.services.ObstacleService;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Entity
public class CleaningDevice {

    @Id
    private final UUID cleaningDeviceId = UUID.randomUUID();

    private String name;

    @Setter
    private UUID spaceId;

    @Embedded
    @Setter
    private Coordinate coordinate;

    private Boolean spawned = false;

    public CleaningDevice(String name) {
        this.name = name;
    }

    protected CleaningDevice() {
    }


    public Boolean executeCommand(Order order, Cloud cloud) {
        switch (order.getOrderType()) {
            case NORTH:
            case SOUTH:
                return this.goVertical(order.getNumberOfSteps(), cloud, order.getOrderType());
            case EAST:
            case WEST:
                return this.goHorizontal(order.getNumberOfSteps(), cloud, order.getOrderType());
            case TRANSPORT:
                return this.transport(order.getGridId(), cloud);
            case ENTER:
                return this.enterField(order.getGridId(), cloud);
        }
        throw new IllegalStateException("cleaningDevice.executeCommand()");
    }

    private Boolean goHorizontal(Integer numberOfSteps, Cloud cloud, OrderType orderType) {
        for (int i = 0; i < numberOfSteps; i++) {
            this.printStatus();
            if (ObstacleService.checkVerticalObstacles(this.spaceId, this.coordinate, cloud, orderType)) {
                if (!Cloud.checkCleaningDevicesInDirection(this.spaceId, this.coordinate, cloud, orderType, this.cleaningDeviceId)) {
                    switch (orderType) {
                        case EAST:
                            this.coordinate = new Coordinate((this.coordinate.getX() + 1), this.coordinate.getY());
                            break;
                        case WEST:
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

    private Boolean goVertical(Integer numberOfSteps, Cloud cloud, OrderType orderType) {
        for (int i = 0; i < numberOfSteps; i++) {
            this.printStatus();
            if (ObstacleService.checkHorizontalObstacles(this.spaceId, this.coordinate, cloud, orderType)) {
                if (!Cloud.checkCleaningDevicesInDirection(this.spaceId, this.coordinate, cloud, orderType, this.cleaningDeviceId)) {
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

    private Boolean transport(UUID gridId, Cloud world) {

        AtomicReference<Boolean> result = new AtomicReference<>(false);
        //Für jede connection auf dem Feld wo das cleaningDevice steht...
        for (Map.Entry<UUID, Connection> connectionEntry : world.getSpaces().findById(this.spaceId).get().getConnections().entrySet()) {
            //wenn das aktuelle Feld auf dem das cleaningDevice steht ein Connection Feld ist...
            if (connectionEntry.getValue().getSourceCoordinate().equals(this.coordinate)) {
                //Für jeden cleaningDevice auf der Welt
                for (CleaningDevice cleaningDeviceEntry : world.getCleaningDevices().findAll()) {//Wenn er gespawned ist...
                    if (cleaningDeviceEntry.getSpawned()) {
                        //Wenn er auf dem connection destination feld steht...
                        if (cleaningDeviceEntry.getCoordinate().equals(connectionEntry.getValue().getDestinationCoordinate())) {
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

    private Boolean enterField(UUID fieldId, Cloud world) throws IllegalStateException {

        AtomicReference<Boolean> result = new AtomicReference<>(false);

        for (CleaningDevice cleaningDeviceEntry : world.getCleaningDevices().findAll()) {
            if (!cleaningDeviceEntry.getCleaningDeviceId().equals(this.cleaningDeviceId)) {
                if (cleaningDeviceEntry.getSpawned()) {
                    if (cleaningDeviceEntry.getCoordinate().equals(new Coordinate(0, 0))) {
                        result.set(false);
                        break;
                    } else {
                        this.spawned = true;
                        this.coordinate = new Coordinate(0, 0);
                        this.spaceId = fieldId;
                        result.set(true);
                        break;
                    }
                } else {
                    this.spawned = true;
                    this.coordinate = new Coordinate(0, 0);
                    this.spaceId = fieldId;
                    result.set(true);
                    break;
                }
            }
        }
        return result.get();
    }

    private void printStatus() {
        System.out.println("FieldId: " + this.spaceId);
        System.out.println("Coordinate: " + this.coordinate.toString());
        System.out.println("=========================");
    }
}
