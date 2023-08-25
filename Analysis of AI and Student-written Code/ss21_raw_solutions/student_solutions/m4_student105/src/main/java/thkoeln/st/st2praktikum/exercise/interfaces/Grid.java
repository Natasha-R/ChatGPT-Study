package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.MoveCommand;

import javax.persistence.Id;
import java.util.UUID;

public interface Grid {

    void addBlocker(Blocker blocker);
    void removeBlocker(Blocker blocker);

    @Id
    UUID getId();

    void setId(UUID id);

    int getWidth();
    int getHeight();

    void setWidth(int width);
    void setHeight(int height);

    boolean isBlocked(MoveCommand mc);
}
