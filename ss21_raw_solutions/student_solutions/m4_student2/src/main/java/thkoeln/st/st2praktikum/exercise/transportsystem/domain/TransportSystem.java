package thkoeln.st.st2praktikum.exercise.transportsystem.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.transportsystem.TransportSystemInterface;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class TransportSystem extends AbstractEntity implements TransportSystemInterface {
    @Getter
    private String name;

    @Getter
    @OneToMany
    private List<Connection> connections;

    public TransportSystem(String name){
        this.name = name;
        this.connections = new ArrayList<Connection>();
    }

    @Override
    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    @Override
    public void deleteConnection(Connection connection) {
        connections.remove(connection);
    }
}
