package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransportSystem {
    private String system;

    @Id
    private UUID tsID;

    @ElementCollection(targetClass = Connection.class,fetch = FetchType.EAGER)
    private final List<Connection> connections = new ArrayList<>();

    public TransportSystem(String s){
        this.tsID = UUID.randomUUID();
        this.system = s;
    }
}