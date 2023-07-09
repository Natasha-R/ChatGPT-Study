package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.exceptions.NotFoundException;
import thkoeln.st.st2praktikum.exercise.interfaces.*;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Deck;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Service
public class Spaceship implements Controller {
    private final Map<UUID, Grid> grids = new HashMap<>();

    @Autowired
    MaintenanceDroidRepository droidRepo;

    @Autowired
    TransportTechnologyRepository techRepo;

    public Deck findDeckById(UUID deckId) {
        return (Deck) this.grids.get(deckId);
    }

    public void addDeck(Deck deck) {
        this.grids.put(deck.id, deck);
    }

    public Connection findTransporter(Transportable droid, Deck targetDeck) throws NotFoundException {
        for (TransportTechnology technology : techRepo.findAll())
            for (Transporter transporter : technology.getTransporters()) {
                if (droid.getGrid().equals(transporter.getSource())
                        && droid.getCurrentPosition().equals(transporter.getSourceCoordinate())
                        && targetDeck.equals(transporter.getDestination())) {
                    return (Connection) transporter;
                }
            }
        throw new NotFoundException("No Connection found");
    }

    public void addConnection(Connection con, TransportTechnology tech) {
        tech.addTransporter(con);
    }

    public TransportTechnology addTechnology(String technologyType) {
        TransportTechnology t = new TransportTechnology(technologyType);
        techRepo.save(t);
        return t;
    }

    public TransportTechnology findTechnologyById(UUID techId) {
        return techRepo.findById(techId).orElseThrow(RuntimeException::new);
    }

    public MaintenanceDroid findDroidById(UUID droidId) {
        return droidRepo.findById(droidId).orElseThrow(RuntimeException::new);
    }

    public void addDroid(MaintenanceDroid droid) {
        this.droidRepo.save(droid);
    }

    public boolean transport(TransportCommand cmd) {
        try {
            Connection connection = this.findTransporter(cmd.getAffected(), cmd.getDestination());
            cmd.getAffected().transport(connection.getDestination(), connection.getDestinationCoordinate(), connection.getSource());
            return true;
        } catch (NotFoundException nfe) {
            return false;
        }
    }

    public boolean spawn(SpawnCommand cmd) {
        Deck grid = cmd.getDeck();
        for (Moveable d : this.droidRepo.findAll()) {
            if (d.getGrid() == grid && d.getCurrentPosition().equals(new Coordinate(0, 0))) {
                return false;
            }
        }
        cmd.getAffected().spawn(grid, new Coordinate(0, 0));
        return true;
    }

    public void move(MoveCommand cmd) {
        ArrayList<MoveCommand> moveCommands = cmd.getSingleSteps();
        Coordinate endPos = null;
        for (MoveCommand c : moveCommands) {
            if (cmd.getAffected().getGrid().isBlocked(c)) {
                endPos = c.getStartPosition();
                break;
            }
        }
        if (endPos == null) endPos = cmd.getEndPosition();
        cmd.getAffected().setCurrentPosition(endPos);
    }

    public boolean execute(Order order, MaintenanceDroid affected) {
        switch (order.getOrderType()) {
            case TRANSPORT: return (new TransportCommand(findDeckById(order.getGridId()), affected)).executeOn(this);
            case ENTER: return (new SpawnCommand(findDeckById(order.getGridId()), affected)).executeOn(this);
            case NORTH: return (new MoveCommand(affected.currentPosition, affected.currentPosition.add(order.getNumberOfSteps(), Direction.NORTH), affected)).executeOn(this);
            case SOUTH: return (new MoveCommand(affected.currentPosition, affected.currentPosition.add(order.getNumberOfSteps(), Direction.SOUTH), affected)).executeOn(this);
            case EAST: return (new MoveCommand(affected.currentPosition, affected.currentPosition.add(order.getNumberOfSteps(), Direction.EAST), affected)).executeOn(this);
            case WEST: return (new MoveCommand(affected.currentPosition, affected.currentPosition.add(order.getNumberOfSteps(), Direction.WEST), affected)).executeOn(this);
        }
        throw new RuntimeException("Order type unknown");
    }
}
