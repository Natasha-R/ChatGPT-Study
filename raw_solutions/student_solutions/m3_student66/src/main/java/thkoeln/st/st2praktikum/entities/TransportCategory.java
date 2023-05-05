package thkoeln.st.st2praktikum.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TransportCategory extends AbstractEntity{

    @OneToMany
    private List<Connection> connections = new ArrayList<>();
    private String category;

    public TransportCategory(String category){
        this.category = category;
    }

    protected TransportCategory(){}

    public void addConnection(Connection newConnection){
        connections.add(newConnection);
    }

    public void removeConnection(Connection oldConnection){
        connections.remove(oldConnection);
    }

}
