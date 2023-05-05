package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@AllArgsConstructor() @EqualsAndHashCode
@Getter
public class Coords {
    private int x;
    private int y;

    // note to me: maybe put field here too?

    public Coords(String s){
        // parse numbers from (0,0)
        var arr = s.replace("(", "").replace(")","").split(",");
        x = Integer.parseInt(arr[0]);
        y = Integer.parseInt(arr[1]);
    }

    public void moveNo() { y = y + 1; }
    public void moveEa() { x = x + 1; }
    public void moveSo() { y = y - 1; }
    public void moveWe() { x = x - 1; }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Coords copy() {
        return new Coords(x,y);
    }
}
