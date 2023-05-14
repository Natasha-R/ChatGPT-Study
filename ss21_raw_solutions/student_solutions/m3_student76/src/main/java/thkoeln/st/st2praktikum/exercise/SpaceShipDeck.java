package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor

public class SpaceShipDeck extends AbstractEntity {

    private  int height;
    private  int width;

    @ElementCollection(targetClass = Point.class, fetch = FetchType.EAGER)
    protected List<Barrier> barriers = new ArrayList<>();
    @OneToMany
    protected List<Connection> connections = new ArrayList<>();
    @OneToMany
    protected List<MaintenanceDroid> maintenanceDroids = new ArrayList<>();


    public void addBarrier(Barrier barrier){
        if (barrier.getStart().getX()<=width && barrier.getEnd().getX()<=width && barrier.getStart().getY()<=height && barrier.getEnd().getY()<=height) {
            barriers.add(barrier);
        }else throw new RuntimeException();
    }


    public void addConnection(Connection connection){
        connections.add(connection);
    }


    public SpaceShipDeck(int width, int height){
        this.height = height;
        this.width = width;
    }


    public Connection retrieveConnection(UUID destinationSpaceShipDeck){
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getDestinationSpaceShipDeck().equals(destinationSpaceShipDeck)) {
                return connections.get(i);
            }
        }
        throw new RuntimeException();
    }


}
