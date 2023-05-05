package thkoeln.st.st2praktikum.exercise.map;

import lombok.Data;
import thkoeln.st.st2praktikum.exercise.control.Environment;

import java.util.UUID;


@Data
public class Connection {
    UUID connectionID;
    Node sourceNode; // the coordinates of the entry point
    Node destinationNode; // the coordinates of the exit point

    public Connection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate, Environment environment) {
        Node source = environment.getDecks().get(sourceSpaceShipDeckId).getNodes().get(sourceCoordinate);
        Node destination = environment.getDecks().get(destinationSpaceShipDeckId).getNodes().get(destinationCoordinate);

        this.setConnectionID(UUID.randomUUID());
        this.setSourceNode(source);
        this.setDestinationNode(destination);

        source.setConnection(destination);

    }

    public Connection(Node sourceNode, Node destinationNode) {
        this.setConnectionID(UUID.randomUUID());
        this.setSourceNode(sourceNode);
        this.setDestinationNode(destinationNode);
        sourceNode.setConnection(destinationNode);
    }

}
