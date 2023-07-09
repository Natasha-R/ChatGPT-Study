package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    int[][] room = new int[12][9];
    int[] robot = {8, 3};

    private void init() {
        // fill room
        for(int i = 0; i < 11; i++)
            for(int j = 0; j < 8; j++)
                room[i][j] = 0;

        // wall 1
        room[4][1] = 1;
        room[4][2] = 1;
        room[4][3] = 1;
        room[4][4] = 1;
        room[4][5] = 1;
        room[4][6] = 1;

        // wall 2
        room[7][5] = 1;
        room[8][5] = 1;
        room[9][5] = 1;

        // wall 3
        room[6][2] = 1;
        room[6][3] = 1;
        room[6][4] = 1;
        room[6][5] = 1;

        // wall 4
        room[7][2] = 1;
        room[8][2] = 1;
        room[9][2] = 1;
    }

    @Override
    public String go(String goCommandString) {

        init();
        String dir = goCommandString.substring(1, 3);
        int len = Integer.parseInt(goCommandString.substring(4, 5));

        switch (dir) {
            case "no":
                for (int i = 0; i < len; i++)
                    if(robot[1] == 8 || (room[robot[0]][robot[1] + 1] == 1 && room[robot[0] + 1][robot[1] + 1] == 1))
                        return "(" + robot[0] + "," + robot[1] + ")";
                    else robot[1] ++;
                return "(" + robot[0] + "," + robot[1] + ")";
            case "ea":
                for (int i = 0; i < len; i++)
                    if(robot[0] == 11 || (room[robot[0] + 1][robot[1]] == 1 && room[robot[0] + 1][robot[1] + 1] == 1))
                        return "(" + robot[0] + "," + robot[1] + ")";
                    else robot[0] ++;
                return "(" + robot[0] + "," + robot[1] + ")";
            case "so":
                for (int i = 0; i < len; i++)
                    if(robot[1] == 0 || (room[robot[0]][robot[1]] == 1 && room[robot[0] + 1][robot[1]] == 1))
                        return "(" + robot[0] + "," + robot[1] + ")";
                    else robot[1] --;
                return "(" + robot[0] + "," + robot[1] + ")";
            case "we":
                for (int i = 0; i < len; i++)
                    if(robot[0] == 0 || (room[robot[0]][robot[1]] == 1 && room[robot[0]][robot[1] + 1] == 1))
                        return "(" + robot[0] + "," + robot[1] + ")";
                    else robot[0] --;
                return "(" + robot[0] + "," + robot[1] + ")";
            default: return "";
        }
    }
}
