package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TransportSystem extends AbstractEntity {

    private String system;
    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Connection> connections = new HashSet<>();

    public TransportSystem(String system) {
        this();
        this.system = system;
    }

    public boolean addConnection(Connection connection) {
        return this.connections.add(connection);
    }

    public Set<Connection> getConnections() {
        return Collections.unmodifiableSet(this.connections);
    }
}
