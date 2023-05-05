package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@Getter
public class TransportCategory {
    private String category;
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
