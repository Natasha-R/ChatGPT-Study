package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class Space extends AbstractEntity {

    private int width;
    private int height;
    protected ArrayList<String> barrierrs = new ArrayList<>();
    protected ArrayList<CleaningDevice> cleaningDevices = new ArrayList<>();
    protected ArrayList<Connection> connections = new ArrayList<>();

    public Space(int width, int height){
        this.width = width;
        this.height = height;
    }

    protected Connection fetchConnectionByDestinationSpaceId(UUID destinationSpace) {
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getDestinationSpace().equals(destinationSpace)) {
                return connections.get(i);
            }
        }
        return null;
    }
}
