package thkoeln.st.st2praktikum.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TaskCommandFactory {

    private final SpaceShipDeckRepository spaceShipDeckRepository;
    private final TransportSystemRepository transportSystemRepository;
    private final MaintenanceDroidRepository maintenanceDroidRepository;

    public Command createCommand(Task task, UUID maintenanceDroidId) {
        switch (task.getTaskType()) {
            case ENTER:
                var spaceShipDeck = this.spaceShipDeckRepository
                        .findById(task.getGridId())
                        .orElseThrow(NoSuchElementException::new);
                return Command.createEnterCommand(spaceShipDeck);
            case TRANSPORT:
                var maintenanceDroid = this.maintenanceDroidRepository.findById(maintenanceDroidId)
                        .orElseThrow(NoSuchElementException::new);
                var connection = StreamUtils.createStreamFromIterator(
                        this.transportSystemRepository.findAll().iterator()
                ).flatMap(it -> it.findAllBySource(maintenanceDroid.getPosition()).stream())
                        .filter(it -> it.getTarget().getMap().getId()
                                .equals(task.getGridId()))
                        .findAny().orElseThrow(NoSuchElementException::new);

                return Command.createConnectionCommand(connection);
            case NORTH:
                return Command.createMapCommand(Direction.NORTH, task.getNumberOfSteps());
            case EAST:
                return Command.createMapCommand(Direction.EAST, task.getNumberOfSteps());
            case SOUTH:
                return Command.createMapCommand(Direction.SOUTH, task.getNumberOfSteps());
            case WEST:
                return Command.createMapCommand(Direction.WEST, task.getNumberOfSteps());
            default:
                throw new IllegalArgumentException(task.getTaskType() + " is unknown");
        }
    }
}
