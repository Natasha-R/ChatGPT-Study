package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Field
{
    @Id
    private UUID uuid;

    @ElementCollection(targetClass = Barrier.class, fetch = FetchType.EAGER)
    private List<Barrier> barriers;
    private Integer height;
    private Integer width;

    public Field(Integer height, Integer width)
    {
        uuid = UUID.randomUUID();

        this.height = height;
        this.width = width;
        barriers = new ArrayList<Barrier>();
    }
}