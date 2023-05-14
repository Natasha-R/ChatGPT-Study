package thkoeln.st.st2praktikum.exercise.abstractions.abstractClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode
@MappedSuperclass
public class AbstractEntity {

    @Id
    @Getter
    protected final UUID id = UUID.randomUUID();

}
