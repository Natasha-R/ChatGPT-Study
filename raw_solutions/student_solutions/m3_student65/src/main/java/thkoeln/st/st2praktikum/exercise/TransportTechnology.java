package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class TransportTechnology {
    @Id
    private UUID id;
    private String technology;

    public TransportTechnology(String technology, UUID id) {
        this.technology = technology;
        this.id = id;
    }


    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
