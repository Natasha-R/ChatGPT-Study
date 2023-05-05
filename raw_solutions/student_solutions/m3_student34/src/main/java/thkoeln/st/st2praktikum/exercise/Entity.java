package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public interface Entity {
    @Id
    UUID getId();
}
