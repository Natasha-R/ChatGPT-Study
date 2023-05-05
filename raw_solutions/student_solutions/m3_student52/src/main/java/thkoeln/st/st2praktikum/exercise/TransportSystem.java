package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity @AllArgsConstructor @NoArgsConstructor
public class TransportSystem extends AbstractEntity {

    @Getter
    @OneToMany(cascade = CascadeType.REMOVE)
    private final List<Connection> connections = new ArrayList<>();

    @Getter private String name;
}
