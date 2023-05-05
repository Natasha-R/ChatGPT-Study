package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.field.domain.Barrier;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Field {
    @Id
    private UUID fieldID;

    private int width;
    private int height;

    @ElementCollection(targetClass = Barrier.class,fetch = FetchType.EAGER)
    private final List<Barrier> Barriers = new ArrayList<>();




    public Field(int width, int height) {
        this.height = height;
        this.width = width;
        this.fieldID = UUID.randomUUID();
    }


}