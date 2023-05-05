package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TransportSystem extends AbstractEntity {
    @Getter
    private String name;

    @Getter
    @OneToMany
    private List<Connection> connections;

    public TransportSystem(){}

    public TransportSystem(String name){
        this.name = name;
        this.connections = new ArrayList<Connection>();
    }

    public void addConnection(Connection connection){
        connections.add(connection);
    }

    public void deleteConnection(Connection connection){
        connections.remove(connection);
    }
}
