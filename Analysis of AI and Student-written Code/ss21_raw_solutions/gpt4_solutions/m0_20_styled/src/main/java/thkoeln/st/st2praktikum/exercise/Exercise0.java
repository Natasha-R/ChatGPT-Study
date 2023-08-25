package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Exercise0 implements Walkable {
    private Point machineLocation;
    private int fieldWidth = 12;
    private int fieldHeight = 9;
    private boolean[][] walls;

    public Exercise0() { machineLocation = new Point(0, 2);
        walls = new boolean[fieldHeight + 1][fieldWidth + 1];
        setWall(3, 0, 3, 3);
        setWall(5, 0, 5, 4);
        setWall(4, 5, 7, 5);setWall(7, 5, 7, 9);}

    private void setWall(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {for (int i = y1; i <= y2; i++) {walls[i][x1] = true;}} else {for (int i = x1; i <= x2; i++) {walls[y1][i] = true;}}}

    @Override
    public String walk(String walkCommandString) {
        String direction = walkCommandString.substring(1, 3);
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1));

        switch (direction) {case "no":for (int i = 0; i < steps; i++) {if (machineLocation.y + 1 < fieldHeight && !walls[machineLocation.y + 1][machineLocation.x]) {machineLocation.y++;}}break;case "ea":for (int i = 0; i < steps; i++) {if (machineLocation.x + 1 < fieldWidth && !walls[machineLocation.y][machineLocation.x + 1]) {machineLocation.x++;}}break;case "so":for (int i = 0; i < steps; i++) {if (machineLocation.y - 1 >= 0 && !walls[machineLocation.y - 1][machineLocation.x]) {machineLocation.y--;}}break;case "we":for (int i = 0; i < steps; i++) {if (machineLocation.x - 1 >= 0 && !walls[machineLocation.y][machineLocation.x - 1]) {machineLocation.x--;}}break;}

        return "(" + machineLocation.x + "," + machineLocation.y + ")";
    }}

