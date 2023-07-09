package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class Category {

    @Id
    private final UUID categoryId = UUID.randomUUID();

    private String categoryName;

    public Category(String category) {
        this.categoryName = category;
    }

    protected Category(){}
}
