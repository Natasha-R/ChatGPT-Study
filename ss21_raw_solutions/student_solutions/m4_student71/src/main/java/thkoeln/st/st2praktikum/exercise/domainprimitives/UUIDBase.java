package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@MappedSuperclass
public abstract class UUIDBase implements Serializable {

    @Id
    protected UUID uuid;

    public UUIDBase(){
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }
}
