package thkoeln.st.st2praktikum.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DroidService {

    private final MaintenanceDroidRepository maintenanceDroidRepository;
    private final SpaceShipDeckRepository spaceShipDeckRepository;
    private final TaskCommandFactory taskCommandFactory;

    public UUID addDroid(String name) {
        return this.maintenanceDroidRepository
                .save(new MaintenanceDroid(new StartPosition(), name)).getId();
    }

    public boolean moveDroid(UUID droidId, Task task) {
        Command command;
        try {
            command = this.taskCommandFactory.createCommand(task, droidId);
        } catch (NoSuchElementException e) {
            return false;
        }

        return this.maintenanceDroidRepository.findById(droidId)
                .map(it -> it.move(command))
                .orElseThrow(NoSuchElementException::new);
    }

    public UUID getDroidMapId(UUID maintenanceDroidId) {
        return this.maintenanceDroidRepository.findById(maintenanceDroidId)
                .orElseThrow(NoSuchElementException::new)
                .getPosition()
                .getMap()
                .getId();
    }

    public Coordinate getDroidCoordinates(UUID maintenanceDroidId) {
        return new Coordinate(
                this.maintenanceDroidRepository.findById(maintenanceDroidId)
                        .orElseThrow(NoSuchElementException::new)
                        .getPosition()
                        .getCoordinates()
        );
    }
}
