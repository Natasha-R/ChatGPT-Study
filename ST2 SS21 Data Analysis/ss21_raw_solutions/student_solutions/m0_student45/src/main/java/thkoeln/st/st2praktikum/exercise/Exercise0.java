package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    final int WIDTH = 12;
    final int HEIGHT = 9;

    private Field[][] area = new Field[HEIGHT][WIDTH];
    private int start_x = 8;
    private int start_y = 5;


    public Exercise0() {
        init();
        printField();
    }

    @Override
    public String goTo(String goCommandString) {
        String str = goCommandString
                .replace("[", "")
                .replace("]", "");
        String[] command = str.split(",");

        return goTo(command[0], Integer.parseInt(command[1]));
    }

    public String goTo(String direction, int steps) {
        int amount = 0;

        switch (direction){
            case "no":
                while (amount < steps && hasNoBorder(start_x, start_y - amount, direction))
                    amount++;
                start_y -= amount;
                break;

            case "ea":
                while (amount < steps && hasNoBorder(start_x + amount, start_y, direction))
                    amount++;
                start_x += amount;
                break;

            case "so":
                while (amount < steps && hasNoBorder(start_x, start_y + amount, direction))
                    amount++;
                start_y += amount;
                break;

            case "we":
                while (amount < steps && hasNoBorder(start_x - amount, start_y, direction))
                    amount++;
                start_x -= amount;
                break;

            default:
                return "invalid command.";
        }

        return String.format("(%d,%d)", start_x, HEIGHT-1 - start_y);
    }

    private boolean hasNoBorder(int x, int y, String border) {
        switch (border){
            case "no":
                return !area[y][x].north;
            case "ea":
                return !area[y][x].east;
            case "so":
                return !area[y][x].south;
            default:
                return !area[y][x].west;
        }
    }

    class Field {
        private boolean north, east, south, west;

        public void set(String border){
            switch (border){
                case "no":
                    north = true;
                    break;
                case "ea":
                    east = true;
                    break;
                case "so":
                    south = true;
                    break;
                case "we":
                    west = true;
            }
        }
    }

    private void init() {
        // init area
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                area[y][x] = new Field();
            }
        }

        // set top frame border
        for (int x = 0; x < WIDTH; x++) {
            area[0][x].set("no");
        }

        // set right frame border
        for (int y = 0; y < HEIGHT; y++) {
            area[y][WIDTH - 1].set("ea");
        }

        // set bottom frame border
        for (int x = 0; x < WIDTH; x++) {
            area[HEIGHT - 1][x].set("so");
        }

        // set left frame border
        for (int y = 0; y < HEIGHT; y++) {
            area[y][0].set("we");
        }

        // #1 wall
        for (int y = 2; y < 8; y++) {
            area[y][3].set("ea");
            area[y][4].set("we");
        }

        // #2 wall
        for (int y = 4; y < 7; y++) {
            area[y][5].set("ea");
            area[y][6].set("we");
        }

        // #3 wall
        for (int y = 3; y < 7; y += 3) {
            area[y][6].set("so");
            area[y][7].set("so");
            area[y][8].set("so");

            area[y + 1][6].set("no");
            area[y + 1][7].set("no");
            area[y + 1][8].set("no");
        }
    }

    private void printField(){
        String tmp = "";

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (area[y][x].north)
                    tmp += "n";
                else
                    tmp += "_";

                if (area[y][x].east)
                    tmp += "e";
                else
                    tmp += "_";

                if (area[y][x].west)
                    tmp += "w";
                else
                    tmp += "_";

                if (area[y][x].south)
                    tmp += "s";
                else
                    tmp += "_";

                System.out.printf("%s ", tmp);
                tmp = "";
            }
            System.out.println();
        }
    }
}
