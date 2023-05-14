package thkoeln.st.st2praktikum.exercise.map.connection;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@Getter
public class TransportCategory {
    private UUID transportCategoryId = UUID.randomUUID();
    private String description;

    public TransportCategory(String description){
        this.description = description;
    }

}
