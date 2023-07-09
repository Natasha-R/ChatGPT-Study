package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class TransportCategory {
    private String name;
    @Id
    private final UUID transportCategoryUUID = UUID.randomUUID();

    protected TransportCategory() {
    }

    public TransportCategory(String name) {
        this.name = name;
    }
}


