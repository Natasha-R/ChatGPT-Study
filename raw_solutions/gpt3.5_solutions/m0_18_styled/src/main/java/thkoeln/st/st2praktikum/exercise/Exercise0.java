package thkoeln.st.st2praktikum.exercise;
public class Exercise0 implements GoAble {
    public String goTo(String goCommandString) {
        String direction = goCommandString.substring(1,3);
        int steps = Integer.parseInt(goCommandString.substring(4, goCommandString.length() - 1));
        int x = 11, y = 7;
        int maxX = 12, maxY = 8;
        int[][] obstacles = {{6,2,6,5},{5,5,6,5},{5,5,5,6},{5,6,12,6}};
        for (int i = 0; i < steps; i++) {
            switch(direction) {
                case "no":
                    y++;
                    break;
                case "ea":
                    x++;
                    break;
                case "so":
                    y--;
                    break;
                case "we":
                    x--;
                    break;
            }
            if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
                switch(direction) {
                    case "no":
                        y--;
                        break;
                    case "ea":
                        x--;
                        break;
                    case "so":
                        y++;
                        break;
                    case "we":
                        x++;
                        break;
                }
                break;
            }
            for (int j = 0; j < obstacles.length; j++) {
                int x1 = obstacles[j][0];
                int y1 = obstacles[j][1];
                int x2 = obstacles[j][2];
                int y2 = obstacles[j][3];
                if (x == x1 && y >= y1 && y <= y2) {
                    y = y2 + 1;
                    break;
                } else if (x == x2 && y >= y1 && y <= y2) {
                    y = y1 - 1;
                    break;
                } else if (y == y1 && x >= x1 && x <= x2) {
                    x = x2 + 1;
                    break;
                } else if (y == y2 && x >= x1 && x <= x2) {
                    x = x1 - 1;
                    break;
                }
            }
        }
        return "(" + x + "," + y + ")";
    }
}
