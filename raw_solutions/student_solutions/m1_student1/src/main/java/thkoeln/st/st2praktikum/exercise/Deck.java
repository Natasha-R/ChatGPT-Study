package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Deck {
    private final UUID fieldID;
    private final int fieldWidth;
    private final int fieldHeight;

    private final ArrayList<Deck> decks = new ArrayList<>();//Deck und Field übergeordnet und detailliert
    private final ArrayList<Barrier> barriers = new ArrayList<>();
    private final ArrayList<Connection> connections = new ArrayList<>();


    public Deck() {
        this.fieldID = null;
        this.fieldHeight = 0;
        this.fieldWidth = 0;
    }

    public Deck(int width, int height) {
        this.fieldHeight = height;
        this.fieldWidth = width;
        this.fieldID = UUID.randomUUID();
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public UUID getFieldID() {
        return fieldID;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public void addBarrier(Barrier barrier) {
        barriers.add(barrier);
    }

    public List<Barrier> getBarriers() {
        return barriers;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

}