package thkoeln.st.st2praktikum.Core;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.IHasUUID;

@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Getter
    protected UUID id;

    protected AbstractEntity() {
        this.id = UUID.randomUUID();
    }
}
