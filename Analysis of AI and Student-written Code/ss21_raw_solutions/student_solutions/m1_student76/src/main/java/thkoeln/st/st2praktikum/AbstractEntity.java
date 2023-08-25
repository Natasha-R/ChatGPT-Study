package thkoeln.st.st2praktikum;

import lombok.EqualsAndHashCode;
import  lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Getter
    protected  UUID id;

    protected AbstractEntity(){this.id = UUID.randomUUID();}
}
