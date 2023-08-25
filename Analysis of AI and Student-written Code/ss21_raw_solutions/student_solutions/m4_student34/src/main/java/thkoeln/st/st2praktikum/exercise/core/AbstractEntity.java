package thkoeln.st.st2praktikum.exercise.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@EqualsAndHashCode
public abstract class AbstractEntity {

    @Id
    @Getter
    private final UUID id;

    protected AbstractEntity() {
        this.id = UUID.randomUUID();
    }
}
