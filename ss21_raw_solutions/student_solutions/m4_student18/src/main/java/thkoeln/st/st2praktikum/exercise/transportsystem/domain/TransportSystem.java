package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Entity
public class TransportSystem extends AbstractEntity {

    @Getter
    private String system;

    @Getter
    @OneToMany (cascade = CascadeType.REMOVE)
    private final List<Connection> connections = new ArrayList<>();

    protected TransportSystem(){}

}
