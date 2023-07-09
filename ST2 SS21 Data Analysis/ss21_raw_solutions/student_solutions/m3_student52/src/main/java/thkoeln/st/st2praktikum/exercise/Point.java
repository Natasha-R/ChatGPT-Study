package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;


@EqualsAndHashCode @Getter @Setter
@Embeddable @NoArgsConstructor
public class Point {
    private int x;
    private int y;

    // note to me: maybe put field here too?
    private void check(){
        if (x < 0 || y < 0)
            throw new InvalidCommandException("Point must be positive");
    }

    public Point(int x, int y){
        this.x = x;
        this.y = y;
        check();

    }
    public Point(String s){
        // parse numbers from (0,0)
        if (!s.startsWith("(") || !s.endsWith(")"))
            throw new InvalidCommandException("Invalid format (must start with ( and end with )");
        var arr = s.replace("(", "").replace(")","").split(",");
        if (arr.length != 2) throw new InvalidCommandException("Invalid arguments size, expected 2 got " + arr.length);
        x = Integer.parseInt(arr[0]);
        y = Integer.parseInt(arr[1]);
        check();
    }

    public void moveNo() { y = y + 1; }
    public void moveEa() { x = x + 1; }
    public void moveSo() { y = y - 1; }
    public void moveWe() { x = x - 1; }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Point copy() {
        return new Point(x,y);
    }
}
