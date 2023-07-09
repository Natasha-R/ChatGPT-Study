package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Walkable {
    private Position position;
    private Room room;
    private List<String> history;

    public Exercise0() {
        this.room = new Room(12, 8);
        this.position = new Position(3, 0);
        this.history = new ArrayList<>();
        this.room.addWall(3, 0, 3, 3);
        this.room.addWall(7, 0, 7, 2);
        this.room.addWall(4, 3, 7, 3);
        this.room.addWall(1, 4, 8, 4);
        addHistory();
    }

    public void addWall(int x1, int y1, int x2, int y2) {
        this.room.addWall(x1, y1, x2, y2);
    }

    @Override
    public String walkTo(String walkCommandString) {
        Matcher matcher = Pattern.compile("\\[(.*?),(.*?)\\]").matcher(walkCommandString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid command format");
        }
        String direction = matcher.group(1);
        int steps = Integer.parseInt(matcher.group(2));

        for (int i = 0; i < steps; i++) {
            switch (direction) {
                case "no":
                    if (position.y < room.height - 1 && !room.walls[position.x][position.y+1]) {
                        position.y++;
                    }
                    break;
                case "ea":
                    if (position.x < room.width - 1 && !room.walls[position.x+1][position.y]) {
                        position.x++;
                    }
                    break;
                case "so":
                    if (position.y > 0 && !room.walls[position.x][position.y-1]) {
                        position.y--;
                    }
                    break;
                case "we":
                    if (position.x > 0 && !room.walls[position.x-1][position.y]) {
                        position.x--;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown direction: " + direction);
            }
        }
        addHistory();
        return "(" + position.x + "," + position.y + ")";
    }

    private void addHistory() {
        history.add("(" + position.x + "," + position.y + ")");
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
}

// 10 empty lines follow







