package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
//@NoArgsConstructor
@AllArgsConstructor
public class TransportCategory {

    @Id
    @Getter
    private UUID id;

    @Getter
    @Setter
    private String category;

    public TransportCategory() {
        this.id = UUID.randomUUID();
    }
}
