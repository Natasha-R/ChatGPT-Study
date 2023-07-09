package thkoeln.st.st2praktikum.exercise.map;

import lombok.Data;
import lombok.Getter;
import org.aspectj.apache.bcel.classfile.JavaClass;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashMap;
import java.util.UUID;

@Getter
public class DeckGraph{

    private UUID deckID = UUID.randomUUID();

    //TODO this is a bad smell since the deck knows the nodes and the node know the deck
    HashMap<String, Node> nodes = new HashMap<>();
    private int height;
    private int width;

    public DeckGraph(int height, int width) {
        this.height = height;
        this.width = width;
        for ( int x = 0; x < width; x++ ) {
            for (int y = 0; y < height; y++ ) {
                Node node = new Node(deckID, x, y );
                nodes.put(node.getCoordinates().toString(), node);
                if (y > 0){
                    Coordinates southCoordinates = new Coordinates( x, y - 1 );
                    node.makeSouthernConnection(nodes.get(southCoordinates.toString()));
                }
                if (x > 0){
                    Coordinates westCoordinates = new Coordinates(x - 1, y );
                    node.makeWesternConnection(nodes.get(westCoordinates.toString()));
                }
            }
        }
    }

    public void addBarriers(String coordinateString){
        String[] strings = coordinateString.split("-");
        // Convert Strings into coordinates
        String startCoordinates = strings[0];
        String endCoordinates = strings[1];
        Node startNode = nodes.get(startCoordinates);
        System.out.println();
        Node endNode = nodes.get(endCoordinates);
        // Remove connections between nodes

        if ( startNode.getCoordinates().getX() == endNode.getCoordinates().getX() ) {
            // vertical barrier
            startNode.removeEasternConnection(endNode.getSouth());

        } else if ( startNode.getCoordinates().getY() == endNode.getCoordinates().getY() ) {
            // horizontal barrier
            startNode.removeSouthernConnection(endNode.getWest());
        } else {
            System.out.println("This String is an illegal Argument");
        }
    }
}
