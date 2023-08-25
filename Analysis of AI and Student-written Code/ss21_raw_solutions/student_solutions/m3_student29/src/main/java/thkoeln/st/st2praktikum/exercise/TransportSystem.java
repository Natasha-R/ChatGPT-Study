package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class TransportSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ElementCollection(targetClass = Connection.class, fetch = FetchType.EAGER)
    private List<Connection> connections;
}
