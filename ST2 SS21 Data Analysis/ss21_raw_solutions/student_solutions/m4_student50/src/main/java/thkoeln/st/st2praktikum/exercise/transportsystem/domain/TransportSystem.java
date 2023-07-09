package thkoeln.st.st2praktikum.exercise.transportsystem.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TransportSystem extends AbstractEntity {

    private String technology;

    @OneToMany
    private List<Connection> connections = new ArrayList<>();

    public TransportSystem(String technology){
        this.technology = technology;
    }

}