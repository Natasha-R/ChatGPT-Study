package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Space extends AbstractEntity {

    private Integer width;
    private Integer height;


    @ElementCollection(targetClass = Coordinate.class,fetch = FetchType.EAGER)
    protected List<Barrier> barriers = new ArrayList<>();

    @OneToMany
    protected List<CleaningDevice> cleaningDevices = new ArrayList<>();

    @OneToMany
    protected List<Connection> connections = new ArrayList<>();

    public void addConnection(Connection connection) {
            connections.add(connection);
    }
    public void addBarrier(Barrier barrier){

        // the Coordinate of the start and the end of the barrier must be inside the space
        if (barrier.getStart().getX()<=width && barrier.getEnd().getX()<=width && barrier.getStart().getY()<=height && barrier.getEnd().getY()<=height) {
            barriers.add(barrier);
        }else throw new RuntimeException();
    }
    public void addCleaningDevice(CleaningDevice cleaningDevice){
        cleaningDevices.add(cleaningDevice);
    }
    public List<CleaningDevice> getCleaningDevices(){
        return cleaningDevices;
    }
    public List<Barrier> getBarriers(){
        return barriers;
    }
    public Space(int width, int height){
        this.width = width;
        this.height = height;
    }

    public Connection fetchConnectionByDestinationSpaceId(UUID destinationSpace) {
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getDestinationSpace().equals(destinationSpace)) {
                return connections.get(i);
            }
        }
        throw new RuntimeException("There isn't a Connection with this destination ID ["+ destinationSpace + "]");
    }
}
