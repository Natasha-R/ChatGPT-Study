package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TransportCategory extends AbstractEntity {

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.EAGER )
    @Getter
    private List<Connection> connections = new ArrayList<>();
    @Getter
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
