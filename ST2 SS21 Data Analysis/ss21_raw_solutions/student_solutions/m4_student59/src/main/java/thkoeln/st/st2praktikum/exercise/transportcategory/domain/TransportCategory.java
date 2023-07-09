package thkoeln.st.st2praktikum.exercise.transportcategory.domain;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class TransportCategory {

    @Id
    private final UUID categoryId = UUID.randomUUID();

    private String categoryName;

    public TransportCategory(String category) {
        this.categoryName = category;
    }

    protected TransportCategory(){}
}
