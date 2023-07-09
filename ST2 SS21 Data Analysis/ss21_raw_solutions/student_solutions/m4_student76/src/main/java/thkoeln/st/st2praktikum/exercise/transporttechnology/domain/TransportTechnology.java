package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TransportTechnology extends AbstractEntity {

    private String technology;

    @OneToMany
    private List<Connection> connections = new ArrayList<>();

    public TransportTechnology(String technology){
        this.technology = technology;
    }

}
