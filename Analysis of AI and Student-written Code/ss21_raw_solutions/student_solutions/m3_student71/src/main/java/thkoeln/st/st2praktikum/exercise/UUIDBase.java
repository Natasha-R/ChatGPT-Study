package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode
@MappedSuperclass
public abstract class UUIDBase {

    @Id
    protected UUID uuid;

    public UUIDBase(){
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }
}
