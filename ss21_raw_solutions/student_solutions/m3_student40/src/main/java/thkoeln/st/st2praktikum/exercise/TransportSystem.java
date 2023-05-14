package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class TransportSystem {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    @OneToMany (cascade = CascadeType.REMOVE)
    private final List<Connection> connections = new ArrayList<>();

    public TransportSystem(String name) {
        this.name = name;
    }
}
