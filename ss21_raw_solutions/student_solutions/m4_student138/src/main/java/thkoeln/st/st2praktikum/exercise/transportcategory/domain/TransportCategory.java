package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class TransportCategory {
    private String category;
    @Id
    private UUID id;

    public TransportCategory(String category)
    {
        this.category = category;
        id = UUID.randomUUID();
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public UUID getId() {
        return id;
    }

}
