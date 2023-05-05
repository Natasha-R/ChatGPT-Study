package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

public class Exercise0 implements Moveable {
    Cords cords = new Cords(1,7);
    ArrayList<Barrier> barriers = createBarriers();

    @Override
    public String move(String moveCommandString) {
        var s = moveCommandString.replace("[","").replace("]","").split(",");
        var dir = Direction.valueOf(s[0]);
        var steps = Integer.parseInt(s[1]);

        for (int i = 0; i < steps; i++) { moveStep(dir); }

        var debug = moveCommandString + " -> " + cords.toString();
        System.out.println(debug);
        return cords.toString();
    }

    private void moveStep(Direction dir){
        for (Barrier b : barriers){ if (b.blocks(dir)) return; }

        if (dir == Direction.no) cords.y++;
        if (dir == Direction.ea) cords.x++;
        if (dir == Direction.so) cords.y--;
        if (dir == Direction.we) cords.x--;
    }

    enum Direction {
        no, ea, so, we
    }

    @AllArgsConstructor()
    public class Cords {
        public Integer x; public Integer y;
        @Override
        public String toString() {
            return "(" + x.toString() + "," + y.toString() + ")";
        }
    }

    @AllArgsConstructor
    public class Barrier{
        public Cords start;
        public Cords end;

        public Boolean blocks(Direction dir){
            var b = false;
            if (dir == Direction.no && start.y == cords.y + 1 && cords.x >= start.x && cords.x < end.x)
                b = true;
            if (dir == Direction.ea && start.x == cords.x + 1 && cords.y >= start.y && cords.y < end.y)
                b = true;
            if (dir == Direction.so && start.y.equals(cords.y) && cords.x >= start.x && cords.x < end.x)
                b = true;
            if (dir == Direction.we && start.x.equals(cords.x) && cords.y >= start.y && cords.y < end.y)
                b = true;
            return b;
        }
    }


    private Barrier horLine(Integer st, Integer end, Integer y){
        return new Barrier(new Cords(st,y), new Cords(end,y));
    }
    private Barrier verLine(Integer st, Integer end, Integer x){
        return new Barrier(new Cords(x,st), new Cords(x,end));
    }

    private ArrayList<Barrier> createBarriers(){
        var l = new ArrayList<Barrier>();

        l.add(verLine(0,9,0));
        l.add(verLine(0,9,12));
        l.add(horLine(0,12,0));
        l.add(horLine(0,12,9));

        l.add(verLine(3,9,3));
        l.add(verLine(0,2,5));
        l.add(verLine(0,4,6));

        l.add(horLine(3,5,3));

        return l;
    }

}
