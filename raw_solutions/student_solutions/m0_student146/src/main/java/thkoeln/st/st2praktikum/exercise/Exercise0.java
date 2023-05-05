package thkoeln.st.st2praktikum.exercise;

import java.util.*;
import java.util.stream.Collectors;

public class Exercise0 implements Moveable {
    private final Map<WallType, List<String>> walls = new HashMap<>();

    private final int MAX_HEIGHT = 7;
    private final int MAX_WIDTH = 11;
    private int x = 5;
    private int y = 3;

    @Override
    public String moveTo(String moveCommandString) {
        List<String> tmp = new ArrayList<>(Arrays.asList("(4,1)", "(4,2)", "(6,2)", "(6,3)", "(6,4)", "(6,5)", "(6,6)", "(6,7)"));
        walls.put(WallType.HORIZONTAL, tmp);
        tmp = new ArrayList<>(Arrays.asList("(3,3)", "(4,3)", "(5,3)", "(6,3)", "(7,3)", "(8,3)", "(1,6)", "(2,6)", "(3,6)", "(4,6)", "(5,6)"));
        walls.put(WallType.VERTICAL, tmp);
        String direction = getSplit(moveCommandString, 0).replace("[", "");
        int value = Integer.parseInt(getSplit(moveCommandString, 1).replace("]", ""));
        switch (direction) {
            case "no":
                doY(value, true);
                break;
            case "ea":
                doX(value, true);
                break;
            case "so":
                doY(value, false);
                break;
            case "we":
                doX(value, false);
                break;
        }
        System.out.println(moveCommandString);
        System.out.println("(" + x + "," + y + ")");
        return "(" + x + "," + y + ")";
    }

    private void doX(int value, boolean plus) {
        List<String> list;
        if (plus) {
            list = walls.get(WallType.HORIZONTAL).stream().filter(s -> {
                int a = getY(s);
                return a == y && x < getX(s) && (x + value) >= getX(s);
            }).collect(Collectors.toList());
            if (list.isEmpty())
                x += value;
            else
                x = list.stream().mapToInt(this::getX).min().getAsInt() - 1;
            if (x > MAX_WIDTH)
                x = MAX_WIDTH;
        } else {
            list = walls.get(WallType.HORIZONTAL).stream().filter(s -> {
                int a = getY(s);
                return a == y && x >= getX(s) && (x - value) < getX(s);
            }).collect(Collectors.toList());
            if (list.isEmpty())
                x -= value;
            else
                x = list.stream().mapToInt(this::getX).max().getAsInt();
            if (x < 0)
                x = 0;
        }
    }

    private void doY(int value, boolean plus) {
        List<String> list;
        if (plus) {
            list = walls.get(WallType.VERTICAL).stream().filter(s -> {
                int a = getX(s);
                return a == x && y < getY(s) && (y + value) >= getY(s);
            }).collect(Collectors.toList());
            if (list.isEmpty())
                y += value;
            else
                y = list.stream().mapToInt(this::getY).min().getAsInt() - 1;
            if (y > MAX_HEIGHT)
                y = MAX_HEIGHT;
        } else {
            list = walls.get(WallType.VERTICAL).stream().filter(s -> {
                int a = getX(s);
                return a == x && y >= getY(s) && (y - value) < getY(s);
            }).collect(Collectors.toList());
            if (list.isEmpty())
                y -= value;
            else
                y = list.stream().mapToInt(this::getY).max().getAsInt();
            if (y < 0)
                y = 0;
        }
    }

    private int getX(String s) {
        return Integer.parseInt(getSplit(s, 0).replace("(", ""));
    }

    private int getY(String s) {
        return Integer.parseInt(getSplit(s, 1).replace(")", ""));
    }

    private String getSplit(String s, int index) {
        return s.split(",")[index].trim();
    }

    private enum WallType {
        HORIZONTAL,
        VERTICAL
    }
}
