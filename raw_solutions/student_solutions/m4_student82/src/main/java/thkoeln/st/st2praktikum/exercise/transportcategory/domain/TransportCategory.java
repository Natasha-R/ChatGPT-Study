package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransportCategory extends AbstractEntity {

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.EAGER )
    List<Connection> connections = new ArrayList<>();

    private String category;

    @Id
    private UUID transportCategorieId = UUID.randomUUID();

    public TransportCategory(String category) {
        this.category = category;
        this.transportCategorieId = UUID.randomUUID();
    }

}
