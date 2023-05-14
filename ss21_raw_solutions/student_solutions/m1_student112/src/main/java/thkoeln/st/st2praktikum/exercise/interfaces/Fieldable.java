package thkoeln.st.st2praktikum.exercise.interfaces;

import java.util.UUID;

public interface Fieldable {
    UUID getFieldId();
    Integer getHeight();
    void setHeight(Integer height);
    Integer getWidth();
    void setWidth(Integer width);
}
