package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransportCategory extends AbstractEntity {

    private String category;

    @Id
    private UUID transportCategorieId = UUID.randomUUID();

    public TransportCategory(String category) {
        this.category = category;
        this.transportCategorieId = UUID.randomUUID();
    }

}
