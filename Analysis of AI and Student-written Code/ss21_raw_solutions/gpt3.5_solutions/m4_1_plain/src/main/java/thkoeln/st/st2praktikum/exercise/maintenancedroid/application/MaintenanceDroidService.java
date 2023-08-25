package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Connection;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeck;

import java.util.*;

public class MaintenanceDroidService {

    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;

    private Map<UUID, MaintenanceDroid> maintenanceDroids;

    public MaintenanceDroidService() {
        maintenanceDroids = new HashMap<>();
    }

    public UUID addMaintenanceDroid(String name) {
        UUID maintenanceDroidId = UUID.randomUUID();
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(maintenanceDroidId, name);
        maintenanceDroids.put(maintenanceDroidId, maintenanceDroid);
        return maintenanceDroidId;
    }

    public Boolean executeCommand(UUID maintenanceDroidId, String commandString, Map<UUID, SpaceShipDeck> spaceShipDecks, Map<UUID, Obstacle> obstacles, Map<UUID, Connection> connections) {
        MaintenanceDroid maintenanceDroid = maintenanceDroids.get(maintenanceDroidId);
        if (maintenanceDroid != null) {
            return maintenanceDroid.executeCommand(commandString, spaceShipDecks, obstacles, connections);
        } else {
            throw new IllegalArgumentException("MaintenanceDroid not found with ID: " + maintenanceDroidId);
        }
    }

    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        MaintenanceDroid maintenanceDroid = maintenanceDroids.get(maintenanceDroidId);
        if (maintenanceDroid != null) {
            return maintenanceDroid.getSpaceShipDeckId();
        } else {
            throw new IllegalArgumentException("MaintenanceDroid not found with ID: " + maintenanceDroidId);
        }
    }

    public String getCoordinates(UUID maintenanceDroidId) {
        MaintenanceDroid maintenanceDroid = maintenanceDroids.get(maintenanceDroidId);
        if (maintenanceDroid != null) {
            return maintenanceDroid.getCoordinates();
        } else {
            throw new IllegalArgumentException("MaintenanceDroid not found with ID: " + maintenanceDroidId);
        }
    }

    public void saveMaintenanceDroid(MaintenanceDroid droid) {
        maintenanceDroidRepository.save(droid);
    }

    public Optional<MaintenanceDroid> findMaintenanceDroidById(Long id) {
        return maintenanceDroidRepository.findById(id);
    }

    public void deleteMaintenanceDroid(MaintenanceDroid droid) {
        maintenanceDroidRepository.delete(droid);
    }

    public List<MaintenanceDroid> findAllMaintenanceDroids() {
        return maintenanceDroidRepository.findAll();
    }
}
