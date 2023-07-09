package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;

class Robot {
    private Cell cell;
    private final boolean[][] room;
    private static final HashMap<String, int[]> DIRECTIONS = new HashMap<>();

    static {
        DIRECTIONS.put("no", new int[]{0, 1});
        DIRECTIONS.put("ea", new int[]{1, 0});
        DIRECTIONS.put("so", new int[]{0, -1});
        DIRECTIONS.put("we", new int[]{-1, 0});
    }

    public Robot(Cell cell, boolean[][] room) {
        this.cell = cell;
        this.room = room;
    }

    public Cell walk(String walkCommandString) throws IllegalArgumentException {
        String[] command = parseCommand(walkCommandString);
        int[] direction = DIRECTIONS.get(command[0]);
        int steps = Integer.parseInt(command[1]);

        for (int i = 0; i < steps; i++) {
            int newX = cell.getX() + direction[0];
            int newY = cell.getY() + direction[1];

            if (newX < 0 || newX >= room.length || newY < 0 || newY >= room[0].length || !room[newX][newY]) {
                break;
            }

            cell = new Cell(newX, newY);
        }

        return cell;
    }

    private String[] parseCommand(String walkCommandString) throws IllegalArgumentException {
        String[] command = walkCommandString.replace("[", "").replace("]", "").split(",");
        if (command.length != 2 || !DIRECTIONS.containsKey(command[0]) || !isInteger(command[1])) {
            throw new IllegalArgumentException("Invalid command: " + walkCommandString);
        }
        return command;
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
