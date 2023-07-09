package thkoeln.st.st2praktikum.droid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.map.StartPosition;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceDroidService {

    private final MaintenanceDroidRepository maintenanceDroidRepository;
    private final TaskCommandFactory taskCommandFactory;

    public UUID addMaintenanceDroid(String name) {
        return this.maintenanceDroidRepository
                .save(new MaintenanceDroid(new StartPosition(), name)).getId();
    }

    public boolean moveMaintenanceDroid(UUID maintenanceDroidId, Task task) {
        Command command;
        try {
            command = this.taskCommandFactory.createCommand(task, maintenanceDroidId);
        } catch (NoSuchElementException e) {
            return false;
        }

        return this.findMaintenanceDroid(maintenanceDroidId)
                .map(it -> it.move(command))
                .orElseThrow(NoSuchElementException::new);
    }

    public UUID getMaintenanceDroidMapId(UUID maintenanceDroidId) {
        return this.maintenanceDroidRepository.findById(maintenanceDroidId)
                .orElseThrow(NoSuchElementException::new)
                .getPosition()
                .getMap()
                .getId();
    }

    public Coordinate getMaintenanceDroidCoordinates(UUID maintenanceDroidId) {
        return new Coordinate(
                this.maintenanceDroidRepository.findById(maintenanceDroidId)
                .orElseThrow(NoSuchElementException::new)
                .getPosition()
                .getCoordinates()
        );
    }

    public Optional<MaintenanceDroid> findMaintenanceDroid(UUID maintenanceDroidId) {
        return this.maintenanceDroidRepository.findById(maintenanceDroidId);
    }
}
