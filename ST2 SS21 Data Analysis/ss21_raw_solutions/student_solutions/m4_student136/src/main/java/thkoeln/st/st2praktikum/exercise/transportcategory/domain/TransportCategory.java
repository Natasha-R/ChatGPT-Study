package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportCategory
{
    @Id
    private UUID uuid;
    private String category;

    public TransportCategory(String category)
    {
        uuid = UUID.randomUUID();
        this.category = category;
    }
}