package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    private static final Integer startX = 3;
    private static final Integer startY = 0;
    //obstacles
    private static final String[] obstacles = {"(1,4)-(8,4)", "(3,0)-(3,3)", "(4,3)-(7,3)", "(7,0)-(7,2)"};
    private Integer x = startX;
    private Integer y = startY;

    @Override
    public String walkTo(String walkCommandString) {
        //Parse Command String
        String direction = parseCommandDirection(walkCommandString); //north, east, south, west
        Integer distance = parseCommandDistance(walkCommandString); //Integer

        return move(distance, direction);
    }


    private String parseCommandDirection(String walkCommandString) {
        if (walkCommandString.contains("no")) {
            return "north";
        } else if (walkCommandString.contains("ea")) {
            return "east";
        } else if (walkCommandString.contains("so")) {
            return "south";
        } else if (walkCommandString.contains("we")) {
            return "west";
        } else {
            return "";
        }
    }

    private Integer parseCommandDistance(String walkCommandString) {
        return Character.getNumericValue(walkCommandString.toCharArray()[4]);
    }

    private String move(Integer dis, String dir) {
        if (dis > 0) {
            switch (dir) {
                case "north":
                    if (canMove(dir)) this.y++;
                    break;
                case "east":
                    if (canMove(dir)) this.x++;
                    break;
                case "south":
                    if (canMove(dir)) this.y--;
                    break;
                case "west":
                    if (canMove(dir)) this.x--;
                    break;
            }
            return move(dis - 1, dir);
        } else {
            return "(" + x + "," + y + ")";
        }
    }

    private Boolean canMove(String dir) {
        for (String s : obstacles) {
            char[] obstacle = s.toCharArray();
            Integer x1 = Character.getNumericValue(obstacle[1]);
            Integer x2 = Character.getNumericValue(obstacle[7]);
            Integer y1 = Character.getNumericValue(obstacle[3]);
            Integer y2 = Character.getNumericValue(obstacle[9]);

            if (x == 0 && dir.equals("west")) return false;
            if (y == 0 && dir.equals("south")) return false;
            if (x == 11 && dir.equals("east")) return false;
            if (y == 7 && dir.equals("north")) return false;

            if (x1.equals(x2) && y1 <= y && y < y2) {
                if (x.equals(x1) && dir.equals("west")) {
                    return false;
                } else if (x.equals(x1 - 1) && dir.equals("east")) {
                    return false;
                }
            } else if (y1.equals(y2) && x1 <= x && x < x2) {
                if (y.equals(y1) && dir.equals("south")) {
                    return false;
                } else if (y.equals(y1 - 1) && dir.equals("north")) {
                    return false;
                }
            }
        }

        return true;
    }
}