package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class TransportCategory {

    @Getter
    @Id
    private UUID id = UUID.randomUUID();

    @Getter
    private String transportCategory;

    @OneToMany //(cascade = CascadeType.REMOVE)
    private List<Connection> connections = new ArrayList<>();

    public TransportCategory(String name){
        transportCategory = name;
    }

    public void addConnection(Connection connection){
        connections.add(connection);
    }
}
