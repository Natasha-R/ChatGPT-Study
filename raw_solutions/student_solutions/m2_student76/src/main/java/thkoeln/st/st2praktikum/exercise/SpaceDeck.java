package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.service.UnknownUnwrapTypeException;
import thkoeln.st.st2praktikum.AbstractEntity;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter

public class SpaceDeck extends AbstractEntity {

    private final int height;
    private final int width;
    protected ArrayList<Barrier> barriers = new ArrayList<>();
    protected ArrayList<Connection> connections = new ArrayList<>();
    protected ArrayList<MaintenanceDroid> maintenanceDroids = new ArrayList<>();


    public void addBarrier(Barrier barrier){
        barriers.add(barrier);
    }

    public void addConnection(Connection connection){
        connections.add(connection);
    }

    public SpaceDeck(int height,int width){
        this.height = height;
        this.width = width;
    }

    public Connection retrieveConnection(UUID destinationSpaceDeck){
        for (Connection connection : connections) {
            if (connection.getDestinationSpaceDeck().equals(destinationSpaceDeck)) {
                return connection;
            }
        }
        throw new RuntimeException();
    }


}
