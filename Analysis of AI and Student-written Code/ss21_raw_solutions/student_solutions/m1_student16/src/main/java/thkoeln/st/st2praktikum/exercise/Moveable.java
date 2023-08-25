package thkoeln.st.st2praktikum.exercise;


import java.util.List;
import java.util.UUID;

interface Movable {
    boolean executeCommand(String commandString, List<Field> fields, List<Movable> movables);
    boolean move(String direction, Integer steps, List<Field> fields, List<Movable> movables);
    boolean traverse(List<Movable> movables, List<Field> fields);
    boolean enter(List<Movable> movables, UUID targetId);
    void setPosition(Integer newX, Integer newY);
    Integer getX();
    Integer getY();
    UUID getId();
    UUID getFieldId();
    void setFieldId(UUID id);
    boolean isPlaced();

}
