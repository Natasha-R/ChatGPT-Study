package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractEntity {

    @Getter @Id
    protected UUID uuid;

    protected AbstractEntity() {
        this.uuid = UUID.randomUUID();
    }
}
