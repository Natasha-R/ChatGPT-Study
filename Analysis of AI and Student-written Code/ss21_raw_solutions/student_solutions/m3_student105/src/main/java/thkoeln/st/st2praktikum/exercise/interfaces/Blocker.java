package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.MoveCommand;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.UUID;

public interface Blocker {

    @Id
    UUID getId();

    void setId(UUID id);

    boolean blocks(MoveCommand moveCommand);
}
