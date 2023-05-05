package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransportSystem extends thkoeln.st.st1praktikum.core.AbstractEntity {
    @Getter
    private String name;

    @Transient
    private List<Connection> connections = new ArrayList<>();

    public TransportSystem(String system) {
        name = system;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public String getName() {
        return name;
    }

}
