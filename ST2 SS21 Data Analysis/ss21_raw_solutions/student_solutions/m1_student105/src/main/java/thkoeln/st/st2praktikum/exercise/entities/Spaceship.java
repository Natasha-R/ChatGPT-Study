package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.entities.commands.*;
import thkoeln.st.st2praktikum.exercise.exceptions.NotFoundException;
import thkoeln.st.st2praktikum.exercise.interfaces.*;


import java.util.*;

@Getter
public class Spaceship {
    Map<UUID, Grid> grids = new HashMap<>();
    List<Transporter> transporters = new ArrayList<>();
    Map<UUID, MaintenanceDroid> droids = new HashMap<>();

    public Deck findDeckById(UUID deckId) {
        return (Deck) this.grids.get(deckId);
    }

    public void addDeck(Deck deck) {
        this.grids.put(deck.id, deck);
    }

    public Connection findTransporter(Transportable droid, Deck targetDeck) throws NotFoundException {
        for (Transporter transporter : transporters) {
            if (droid.getGrid().equals(transporter.getSource())
                    && droid.getCurrentPosition().equals(transporter.getSourceCoordinate())
                    && targetDeck.equals(transporter.getDestination())) {
                return (Connection) transporter;
            }
        }
        throw new NotFoundException("No Connection found");
    }

    public void addConnection(Connection con) {
        this.transporters.add(con);
    }

    public MaintenanceDroid findDroidById(UUID droidId) {
        return this.droids.get(droidId);
    }

    public void addDroid(MaintenanceDroid droid) {
        this.droids.put(droid.id, droid);
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

    public boolean spawnDroid(SpawnCommand cmd) {
        Grid grid = cmd.getDeck();
        for (Moveable d : this.getDroids().values()) {
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
}
