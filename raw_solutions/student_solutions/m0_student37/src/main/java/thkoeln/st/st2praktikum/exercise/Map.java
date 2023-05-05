package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Map {
    private int x = 12;
    private int y = 9;

    int[][] map = new int[x][y];



}

