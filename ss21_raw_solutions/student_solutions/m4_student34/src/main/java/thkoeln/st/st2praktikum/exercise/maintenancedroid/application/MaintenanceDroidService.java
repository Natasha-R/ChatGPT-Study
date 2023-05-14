package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import lombok.AllArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystemRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
public class MaintenanceDroidService {

    private final MaintenanceDroidRepository maintenanceDroidRepository;
    private final SpaceShipDeckRepository spaceShipDeckRepository;
    private final TransportSystemRepository transportSystemRepository;

    /**
     * This method adds a new maintenance droid
     *
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        var maintenanceDroid = new MaintenanceDroid(name);
        return this.maintenanceDroidRepository.save(maintenanceDroid).getId();
    }

    /**
     * This method lets the maintenance droid execute a task.
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param task               the given task
     *                           NORTH, WEST, SOUTH, EAST for movement
     *                           TRANSPORT for transport - only works on squares with a connection to another spaceship deck
     *                           ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     * another maintenance droid, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Task task) {
        var maintenanceDroid = this.maintenanceDroidRepository.findById(maintenanceDroidId)
                .orElseThrow(NoSuchElementException::new);
        var spaceShipDecks = this.spaceShipDeckRepository.findAll();
        var connections = StreamUtils
                .createStreamFromIterator(this.transportSystemRepository.findAll().iterator())
                .flatMap(it -> it.getConnections().stream())
                .collect(Collectors.toList());

        var wasExecuted = maintenanceDroid.executeTask(task, spaceShipDecks, connections);

        this.maintenanceDroidRepository.save(maintenanceDroid);
        this.spaceShipDeckRepository.saveAll(spaceShipDecks);

        return wasExecuted;
    }

    public Optional<MaintenanceDroid> findById(UUID id) {
        return this.maintenanceDroidRepository.findById(id);
    }

    public Iterable<MaintenanceDroid> findAll() {
        return this.maintenanceDroidRepository.findAll();
    }

    public void deleteById(UUID id) {
        this.maintenanceDroidRepository.deleteById(id);
    }

    public void deleteTaskHistory(UUID id) {
        var maintenanceDroid = this.maintenanceDroidRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        maintenanceDroid.deleteTaskHistory();
        this.maintenanceDroidRepository.save(maintenanceDroid);
    }

    public void changeMaintenanceDroidName(UUID id, String name) {
        var maintenanceDroid = this.maintenanceDroidRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        maintenanceDroid.setName(name);
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        var maintenanceDroid = this.maintenanceDroidRepository.findById(maintenanceDroidId)
                .orElseThrow(NoSuchElementException::new);
        return this.spaceShipDeckRepository.findFirstByDroidsContains(maintenanceDroid)
                .map(SpaceShipDeck::getId)
                .orElse(null);
    }

    /**
     * This method returns the coordinate a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordinate of the maintenance droid
     */
    public Coordinate getMaintenanceDroidCoordinate(UUID maintenanceDroidId) {
        var maintenanceDroid = this.maintenanceDroidRepository.findById(maintenanceDroidId)
                .orElseThrow(NoSuchElementException::new);
        return maintenanceDroid.getCoordinate();
    }
}
