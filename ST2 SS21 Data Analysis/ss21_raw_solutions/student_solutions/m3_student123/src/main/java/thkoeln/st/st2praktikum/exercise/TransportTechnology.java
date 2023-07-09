package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class TransportTechnology {

    @Id
    private UUID transportTechnologyID;
    private String technology;

    protected TransportTechnology(){
        this.transportTechnologyID = UUID.randomUUID();
    }
    public TransportTechnology(String newTechnology){
        this.transportTechnologyID = UUID.randomUUID();
        this.technology = newTechnology;
    }
    public UUID getTransportTechnologyID() {
        return transportTechnologyID;
    }

    public String getTechnology() {
        return technology;
    }
}
