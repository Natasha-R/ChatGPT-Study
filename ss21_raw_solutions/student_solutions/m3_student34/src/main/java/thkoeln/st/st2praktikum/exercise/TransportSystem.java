package thkoeln.st.st2praktikum.exercise;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransportSystem extends AbstractEntity {
    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Connection> connections;
    private String system;

    public TransportSystem(String system) {
        this.connections = new HashSet<>();
        this.system = system;
    }

    public boolean addConnection(Connection connection) {
        return this.connections.add(connection);
    }

    public Set<Connection> findAllBySource(Position source) {
        return this.connections.stream()
                .filter(it -> it.getSource().equals(source))
                .collect(Collectors.toSet());
    }
}
