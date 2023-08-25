package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import thkoeln.st.st2praktikum.AbstractEntity;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class SpaceShipDeck extends AbstractEntity {

    private int width;
    private int height;

   protected ArrayList<Barrier> barriers = new ArrayList<>();
   protected ArrayList<MaintenanceDroid> maintenanceDroids = new ArrayList<>();
   protected ArrayList<Connection> connections= new ArrayList<>();

    protected Connection connection(UUID destinationSpaceShipDeckId) {
        for (Connection connection : connections) {
            if (connection.getDestinationSpaceShipDeckId().equals(destinationSpaceShipDeckId)) {
                return connection;
            }
        }
       return null;
    }

    protected void addBarrier(String barrierString){ barriers.add(new Barrier(barrierString)); }

}
