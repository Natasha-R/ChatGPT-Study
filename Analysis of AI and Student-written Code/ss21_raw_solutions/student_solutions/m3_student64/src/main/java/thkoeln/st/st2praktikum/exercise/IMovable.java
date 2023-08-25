package thkoeln.st.st2praktikum.exercise;

public interface IMovable {
    boolean executeTask(Task task);

    Space getSpace();
    void setSpace(Space space);

    Point getPosition();
    void setPosition(Point point);
}
