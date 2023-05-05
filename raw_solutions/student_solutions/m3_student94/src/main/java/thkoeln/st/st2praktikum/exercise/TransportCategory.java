package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class TransportCategory {
    @Id
    protected UUID transportCategoryId;

    private String category;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.EAGER )
    private final List<Connection> connections = new ArrayList<>();

    public TransportCategory(String category){
        this.category=category;
        this.transportCategoryId=UUID.randomUUID();
    }

    public void addConnection(Connection connection){
        connections.add(connection);
    }

}
