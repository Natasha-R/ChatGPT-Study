package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractEntity {

    @Getter
    protected UUID uuid;

    protected AbstractEntity() {
        this.uuid = UUID.randomUUID();
    }
}
