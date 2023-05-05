package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TransportTechnology {

    @Id
    private UUID transportTechnologyID;
    private String technology;

    @OneToMany
    private List<Connection> connectionList;

    protected TransportTechnology(){
        this.transportTechnologyID = UUID.randomUUID();
    }
    public TransportTechnology(String newTechnology){
        this.transportTechnologyID = UUID.randomUUID();
        this.technology = newTechnology;
        this.connectionList = new ArrayList<>();
    }
    public UUID getTransportTechnologyID() {
        return transportTechnologyID;
    }

    public String getTechnology() {
        return technology;
    }

    public void addConnection(Connection connection){
        connectionList.add(connection);
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }
}
