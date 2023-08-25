package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractEntity implements Entity{

    @Id
    @Getter
    protected final UUID id;

    protected AbstractEntity() {
        this.id = UUID.randomUUID();
    }

    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }

        if(!(other instanceof AbstractEntity)) {
            return false;
        }
        AbstractEntity otherEntity = (AbstractEntity) other;
        return this.id.equals(otherEntity.getId());
    }
}

