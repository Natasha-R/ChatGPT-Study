package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportSystem {

    @Id
    private UUID uuid;
    private String system;

    @OneToMany
    private Map<UUID,Connection> uuidConnectionHashMap = new HashMap<UUID,Connection>();


    public UUID getId() {
        return uuid;
    }

    public String getSystem() {
        return system;
    }

    public TransportSystem(String system){
        this.system = system;
        this.uuid = UUID.randomUUID();
    }

    public Connection createConnection(Field sourceField, Point sourcePoint, Field destinationField, Point destinationPoint){

        Room entryRoom = sourceField.getRoom(sourcePoint.getX(),sourcePoint.getY());
        Room exitRoom = destinationField.getRoom(destinationPoint.getX(),destinationPoint.getY());
        Connection connection = new Connection(entryRoom,exitRoom, this);
        //uuidConnectionHashMap.put(connection.getUuid(),connection);
        return connection;

    }


}
