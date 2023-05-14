package thkoeln.st.st2praktikum.exercise.control;

import lombok.Data;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.droids.Droid;
import thkoeln.st.st2praktikum.exercise.map.Connection;
import thkoeln.st.st2praktikum.exercise.map.DeckGraph;
import thkoeln.st.st2praktikum.exercise.map.Node;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class Environment {
    private HashMap<UUID, DeckGraph> decks = new HashMap<>();
    private HashMap<UUID, Droid> droids = new HashMap<>();
    private HashMap<UUID, Connection> connections = new HashMap<>();

    //Singleton Pattern Quelle: https://www.youtube.com/watch?v=3cJbDs-zzpw
    //private static Environment environment = new Environment();

    public UUID addDroid(String name){
        Droid newDroid = new Droid(name);
        droids.put(newDroid.getDroidID(), newDroid);
        return newDroid.getDroidID();
    }

    public UUID addDeck(DeckGraph deck){
        decks.put(deck.getDeckID(), deck);
        return deck.getDeckID();
    }

    public UUID addDeck(int height, int width) {
        DeckGraph newDeck = new DeckGraph(height, width);
        addDeck(newDeck);
        return newDeck.getDeckID();
    }

    public UUID addConnection(Connection connection){
        connections.put(connection.getConnectionID(), connection);
        return connection.getConnectionID();
    }

    public Node getNode(UUID deckID, String coordinateString){
        return getDecks().get(deckID).getNodes().get(coordinateString);
    }

    public HashMap<String, Node> getNodes(UUID deckID){
        return getDecks().get(deckID).getNodes();
    }
}
