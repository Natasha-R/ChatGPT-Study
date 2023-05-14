package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportTechnology extends AbstractEntity {
    private String technology;
    @OneToMany
    private Set<Connection> connections = new HashSet<Connection>();

    public TransportTechnology(String technology){
        this.technology = technology;
    }
}
