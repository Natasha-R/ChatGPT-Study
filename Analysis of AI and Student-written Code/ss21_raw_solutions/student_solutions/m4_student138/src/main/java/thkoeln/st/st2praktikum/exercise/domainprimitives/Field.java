package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

//@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Field {
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //private Long id;
    private boolean southBarrier;
    private boolean westBarrier;
    private boolean hasCleaner;
    //private HashMap<UUID, UUID> connectionHashMap = new HashMap<UUID, UUID>();
    private Connection connection;
    private boolean hasConnection = false;

    public boolean isHasConnection() {
        return hasConnection;
    }

    public void setSouthBarrier(boolean southBarrier) {
        this.southBarrier = southBarrier;
    }

    public void setWestBarrier(boolean westBarrier) {
        this.westBarrier = westBarrier;
    }

    public void setConnection(Connection connection) {
        //connectionHashMap.put(sourceSpace, connection);
        this.connection = connection;
        hasConnection = true;
    }

    public Connection getConnection() {
        return connection;
        //return connectionHashMap.get(sourceSpace);
    }

    public boolean isSouthBarrier() {
        return southBarrier;
    }

    public boolean isHasCleaner() {
        return hasCleaner;
    }

    public void setHasCleaner(boolean hasCleaner) {
        this.hasCleaner = hasCleaner;
    }

    public boolean isWestBarrier() {
        return westBarrier;
    }

    public boolean canMoveOut(String direction){
        if ((direction.equals("so") && southBarrier) || (direction.equals("we") && westBarrier))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean canMoveIn(String direction){
        if ((direction.equals("no") && southBarrier) || (direction.equals("ea") && westBarrier) || hasCleaner)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
