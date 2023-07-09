package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.Core.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class TransportCategory extends AbstractEntity {

    private String name;

    @OneToMany(mappedBy="transportCategory", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Connection> connections;

    public TransportCategory() {
        this.connections = new ArrayList<>();
    }

    public TransportCategory(String name) {
        this();

        this.name = name;
    }
}
