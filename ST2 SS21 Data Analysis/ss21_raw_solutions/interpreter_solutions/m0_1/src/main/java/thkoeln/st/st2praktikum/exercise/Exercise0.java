package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Moveable {
    private int x = 7;
    private int y = 7;
    private final int roomWidth = 11;
    private final int roomHeight = 8;
    private List<String> barriers = new ArrayList<>();

    public Exercise0() {
        for (int i = 2; i <= 10; i++)
            barriers.add(i + "," + 1);
        for (int i = 1; i <= 6; i++)
            barriers.add(2 + "," + i);
        for (int i = 2; i <= 7; i++)
            barriers.add(i + "," + 6);
        for (int i = 1; i <= 8; i++)
            barriers.add(10 + "," + i);
    }

    public String move(String moveCommandString) {
        String direction = moveCommandString.substring(1, 3);
        int steps = Integer.parseInt(moveCommandString.substring(4, moveCommandString.length() - 1));

        for (int i = 0; i < steps; i++) {
            if (direction.equals("no") && y + 1 < roomHeight && !barriers.contains(x + "," + (y + 1)))
                y += 1;
            else if (direction.equals("ea") && x + 1 < roomWidth && !barriers.contains((x + 1) + "," + y))
                x += 1;
            else if (direction.equals("so") && y - 1 >= 0 && !barriers.contains(x + "," + (y - 1)))
                y -= 1;
            else if (direction.equals("we") && x - 1 >= 0 && !barriers.contains((x - 1) + "," + y))
                x -= 1;
            else
                break;
        }

        return "(" + x + "," + y + ")";
    }
}



