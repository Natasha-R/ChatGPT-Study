package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@EqualsAndHashCode
public class TransportTechnology {

    @Id
    @Getter
    private final UUID ID = UUID.randomUUID();

    @Getter
    private final String technology;


    public TransportTechnology(String technology){
        this.technology = technology;
    }

    protected TransportTechnology(){ technology = null;}
}
