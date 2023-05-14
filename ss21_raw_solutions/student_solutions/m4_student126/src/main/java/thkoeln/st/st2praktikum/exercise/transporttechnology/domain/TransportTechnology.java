package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransportTechnology extends AbstractEntity {

    private String name;
    @OneToMany
    private final List<FieldConnection> connectionList = new ArrayList<>();

}
