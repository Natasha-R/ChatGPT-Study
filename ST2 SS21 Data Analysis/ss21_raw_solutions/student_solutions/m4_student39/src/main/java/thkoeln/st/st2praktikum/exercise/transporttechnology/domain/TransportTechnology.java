package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.field.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportTechnology extends AbstractEntity {
    private String technology;
    @Transient
    private Set<Connection> connections = new HashSet<Connection>();

    public TransportTechnology(String technology){
        this.technology = technology;
    }
}
