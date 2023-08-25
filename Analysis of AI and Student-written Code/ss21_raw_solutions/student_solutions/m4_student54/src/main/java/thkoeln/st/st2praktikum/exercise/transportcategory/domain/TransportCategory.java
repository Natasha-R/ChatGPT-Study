package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class TransportCategory {

    @Id
    private java.util.UUID id;
    private String category;
    @ElementCollection
    private List<Connection> connections;

    public TransportCategory(UUID id, String category)
    {
        this.id = id;
        this.category = category;
        connections = new ArrayList<>();
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
