package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Walkable {
    private static final int x_max=12;
    private static final int y_max=9;
    private int posX=0;
    private int posY=2;
    private boolean[][] w = new boolean[x_max+1][y_max+1];

    public Exercise0() {
        for (int i = 0; i <= x_max; i++) {w[i][0] = true; w[i][y_max] = true;}
        for (int j = 0; j <= y_max; j++) {w[0][j] = true;w[x_max][j] = true;}
        setWall(3, 0, 3, 3);setWall(5, 0, 5, 4);setWall(4, 5, 7, 5);setWall(7, 5, 7, 9);
    }

    private void setWall(int a, int b, int c, int d) {
        if (a == c) {for (int j = b; j <= d; j++) {w[a][j] = true;}} else if (b == d) {for (int i = a; i <= c; i++) {w[i][b] = true;}}
    }

    @Override
    public String walk(String s) {
        Pattern p = Pattern.compile("\\[(\\w+),(\\d+)\\]");Matcher m = p.matcher(s);
        if (m.find()) {
            String dir = m.group(1);int step = Integer.parseInt(m.group(2));

            switch (dir) {case "no":for (int i = 0; i < step; i++) {if (!w[posX][posY+1]) posY++;}break;case "ea":for (int i = 0; i < step; i++) {if (!w[posX+1][posY]) posX++;}break;case "so":for (int i = 0; i < step; i++) {if (!w[posX][posY-1]) posY--;}break;case "we":for (int i = 0; i < step; i++) {if (!w[posX-1][posY]) posX--;}break;}
        }

        return String.format("(%d,%d)", posX, posY);
    }
}