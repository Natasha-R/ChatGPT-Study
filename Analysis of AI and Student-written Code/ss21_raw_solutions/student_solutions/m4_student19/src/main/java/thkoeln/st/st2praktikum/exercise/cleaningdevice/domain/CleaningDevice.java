package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.repositories.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.repositories.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportCategoryRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class CleaningDevice {
    @Id
    @Getter
    private UUID id;

    @Getter
    @Setter
    private String name;

    @Getter
    private UUID spaceId;

    @Embedded
    private Vector2D coordinates;

    @Getter
    @ElementCollection
    private List<Task> tasks;

    public CleaningDevice() {}

    public CleaningDevice(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.coordinates = new Vector2D(0, 0);
        this.tasks = new ArrayList<>();
    }

    public Boolean traverseDevice(TransportCategoryRepository transportCategoryRepository, CleaningDeviceRepository cleaningDeviceRepository, UUID destinationSpaceId) {
        for (TransportCategory transportCategoryEntry : transportCategoryRepository.findAll()) {
            for (Connection connectionEntry : transportCategoryEntry.getConnections()) {
                if (this.spaceId != null) {
                    if (connectionEntry.getSourceSpaceId().compareTo(this.spaceId) == 0 &&
                            connectionEntry.getDestinationSpaceId().compareTo(destinationSpaceId) == 0) {

                        if (this.coordinates.equals(connectionEntry.getSourceVector2D())) {

                            if (!isCoordinateIsFree(connectionEntry.getDestinationVector2D(),
                                    connectionEntry.getDestinationSpaceId(), cleaningDeviceRepository)) {
                                return false;
                            }

                            Vector2D newCoordinates = connectionEntry.getDestinationVector2D();
                            this.traversedToSpace(newCoordinates, connectionEntry.getDestinationSpaceId());
                            return true;

                        } else return false;
                    }
                }
            }
        }

        return false;
    }

    public Boolean placeDevice(SpaceRepository spaceRepository, CleaningDeviceRepository cleaningDeviceRepository, UUID destinationSpaceId) {
        if (spaceRepository.findById(destinationSpaceId).isEmpty())
            throw new IllegalArgumentException("This destination space doesn't exist: " + destinationSpaceId);

        for (CleaningDevice cleaningDeviceEntry : cleaningDeviceRepository.findAll()) {
            if (cleaningDeviceEntry.getSpaceId() != null) {
                if (cleaningDeviceEntry.getSpaceId().compareTo(destinationSpaceId) == 0 &&
                        cleaningDeviceEntry.getVector2D().equals(new Vector2D(0, 0))) {
                    return false;
                }
            }
        }

        this.placedInSpace(destinationSpaceId);
        return true;
    }

    public Boolean moveNorth(Space space, int moveCount, CleaningDeviceRepository cleaningDeviceRepository) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).getBorderNorth() &&
                    this.getY() + 1 < space.getSpaceHeight() &&
                    this.isCoordinateIsFree(new Vector2D(this.getX(), this.getY() + 1), space.getId(), cleaningDeviceRepository)) {
                this.increaseY();
            } else break;
        }

        return true;
    }

    public Boolean moveEast(Space space, int moveCount, CleaningDeviceRepository cleaningDeviceRepository) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).getBorderEast() &&
                    this.getX() + 1 < space.getSpaceWidth() &&
                    this.isCoordinateIsFree(new Vector2D(this.getX() + 1, this.getY()), space.getId(), cleaningDeviceRepository)) {
                this.increaseX();
            } else break;
        }

        return true;
    }

    public Boolean moveSouth(Space space, int moveCount, CleaningDeviceRepository cleaningDeviceRepository) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).getBorderSouth() &&
                    this.getY() - 1 >= 0 &&
                    this.isCoordinateIsFree(new Vector2D(this.getX(), this.getY() - 1), space.getId(), cleaningDeviceRepository)) {
                this.decreaseY();
            } else break;
        }

        return true;
    }

    public Boolean moveWest(Space space, int moveCount, CleaningDeviceRepository cleaningDeviceRepository) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).getBorderWest() &&
                    this.getX() - 1 >= 0 &&
                    this.isCoordinateIsFree(new Vector2D(this.getX() - 1, this.getY()), space.getId(), cleaningDeviceRepository)) {
                this.decreaseX();
            } else break;
        }

        return true;
    }

    private Boolean isCoordinateIsFree(Vector2D coordinateToCheck, UUID spaceId, CleaningDeviceRepository cleaningDeviceRepository) {
        for (CleaningDevice cleaningDeviceEntry : cleaningDeviceRepository.findAll()) {
            if (cleaningDeviceEntry.spaceId != null) {
                if (cleaningDeviceEntry.spaceId.compareTo(spaceId) == 0 &&
                        cleaningDeviceEntry.coordinates.equals(coordinateToCheck)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void traversedToSpace(Vector2D newCoordinate, UUID newSpaceId) {
        this.setCoordinates(newCoordinate);
        this.spaceId = newSpaceId;
    }

    public int getX() { return this.coordinates.getX(); }
    public int getY() { return this.coordinates.getY(); }

    public Vector2D getVector2D() { return this.coordinates; }
    public void setCoordinates(int x, int y) { this.coordinates = new Vector2D(x, y); }
    public void setCoordinates(Vector2D newCoordinate) { this.coordinates = newCoordinate; }

    public void increaseX() {
        this.coordinates = new Vector2D(this.coordinates.getX() + 1, this.coordinates.getY());
    }

    public void increaseY() {
        this.coordinates = new Vector2D(this.coordinates.getX(), this.coordinates.getY() + 1);
    }

    public void decreaseX() {
        this.coordinates = new Vector2D(this.coordinates.getX() - 1, this.coordinates.getY());
    }

    public void decreaseY() {
        this.coordinates = new Vector2D(this.coordinates.getX(), this.coordinates.getY() - 1);
    }

    public void addTask(Task task) { this.tasks.add(task); }
    public void clearTasks() { this.tasks.clear(); }

    public void placedInSpace(UUID spaceId) {
        this.spaceId = spaceId;
    }
}
