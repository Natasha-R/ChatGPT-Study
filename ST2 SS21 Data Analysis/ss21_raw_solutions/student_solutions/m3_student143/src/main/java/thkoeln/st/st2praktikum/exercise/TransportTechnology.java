package thkoeln.st.st2praktikum.exercise;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class TransportTechnology {

    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @Getter
    private String technology;

    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Getter
    private List<Connection> connections = new ArrayList<>();

    public TransportTechnology(String technology){
        this.technology = technology;
    }

    void addConnection(Connection connection){
        this.connections.add(connection);
    }


}
