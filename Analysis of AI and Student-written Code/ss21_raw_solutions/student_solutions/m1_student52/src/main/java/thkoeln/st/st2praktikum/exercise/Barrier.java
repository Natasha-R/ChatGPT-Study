package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Barrier {
    private final Coords start;
    private final Coords end;

    public Barrier(int startX, int startY, int endX, int endY){
        start = new Coords(startX, startY);
        end = new Coords(endX, endY);
    }

    public Barrier(String s){
        var arr = s.split("-");
        start = new Coords(arr[0]);
        end = new Coords(arr[1]);
    }

    public Boolean blocks(Coords coords , Command.Name dir){
        if (dir == Command.Name.no && start.getY() == coords.getY() + 1 && coords.getX() >= start.getX() && coords.getX() < end.getX())
            return true;
        if (dir == Command.Name.ea && start.getX() == coords.getX() + 1 && coords.getY() >= start.getY() && coords.getY() < end.getY())
            return true;
        if (dir == Command.Name.so && start.getY() == coords.getY() && coords.getX() >= start.getX() && coords.getX() < end.getX())
            return true;
        if (dir == Command.Name.we && start.getX() == coords.getX() && coords.getY() >= start.getY() && coords.getY() < end.getY())
            return true;
        return false;
    }
}
