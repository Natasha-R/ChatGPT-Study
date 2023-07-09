package thkoeln.st.st2praktikum;

import lombok.Getter;

import javax.persistence.Id;
import java.util.UUID;


public abstract class AbstractEntity {
    @Id
    @Getter
    protected UUID id;

    protected  AbstractEntity(){ this.id = UUID.randomUUID();}
}
