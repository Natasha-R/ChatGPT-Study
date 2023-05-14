package thkoeln.st.st2praktikum.exercise;


public class Exercise0 implements GoAble {
    static String[][] grid = new String[11][8];
    int positionX = 1;
    int positionY = 6;

    public static void initialize() {
        grid[0][6] = "horizontal";
        grid[1][6] = "horizontal";

        grid[3][6] = "vertical";
        grid[3][7] = "vertical";

        grid[1][5] = "horizontal";
        grid[2][5] = "horizontal";
        grid[3][5] = "horizontal";
        grid[4][5] = "horizontal";
        grid[5][5] = "horizontal";
        grid[6][5] = "horizontal";
        grid[7][5] = "horizontal";
        grid[8][5] = "horizontal";

        grid[9][1] = "vertical";
        grid[9][2] = "vertical";
        grid[9][3] = "vertical";
        grid[9][4] = "vertical";
    }

    @Override
    public String go(String goCommandString) {
        initialize();

        String direction = "";
        String commandStart = "";
        String commandEnd = "";

        String[] stringSplit = goCommandString.split(",");

        if(stringSplit.length > 1) {
        direction = stringSplit[0].substring(1);
        commandStart = stringSplit[0].substring(0,1);


            commandEnd = stringSplit[1].substring(stringSplit[1].length() - 1);
        }


        if(commandStart.equals("[") && commandEnd.equals("]")) {

            int count;
            try {
                count = Integer.parseInt(stringSplit[1].substring(0, stringSplit[1].length() - 1));
            } catch (NumberFormatException e) {
                count = 0;
            }


            switch (direction) {
                case "no":
                    if (count >= 0) {
                        for (int counter = 0; counter < count; counter++) {
                            if (positionY + 1 < grid[1].length) {
                                if (grid[positionX][positionY + 1] == null || grid[positionX][positionY + 1] == "vertical") {
                                    positionY += 1;
                                } else if (grid[positionX][positionY + 1] == "horizontal") {
                                    break;
                                }
                            } else break;
                        }
                    } else if (count < 0) {
                        count *= -1;
                        go("[so" + "," + count + "]");
                    }
                    break;

                case "ea":
                    if (count >= 0) {
                        for (int counter = 0; counter < count; counter++) {
                            if (positionX + 1 < grid.length) {
                                if (grid[positionX + 1][positionY] == null || grid[positionX + 1][positionY] == "horizontal") {
                                    positionX += 1;
                                } else if (grid[positionX + 1][positionY] == "vertical") {
                                    break;
                                }
                            } else break;
                        }
                    } else if (count < 0) {
                        count *= -1;
                        go("[we" + "," + count + "]");
                    }
                    break;
                case "so":
                    if (count >= 0) {
                        if (grid[positionX][positionY] != "horizontal") {
                            for (int counter = 0; counter < count; counter++) {
                                if (positionY - 1 >= 0) {
                                    if (grid[positionX][positionY - 1] == null || grid[positionX][positionY - 1] == "vertical") {
                                        positionY -= 1;
                                    } else if (grid[positionX][positionY - 1] == "horizontal") {
                                        positionY -= 1;
                                        break;
                                    }
                                } else break;
                            }
                        }
                    } else if (count < 0) {
                        count *= -1;
                        go("[no" + "," + count + "]");
                    }
                    break;
                case "we":
                    if (count >= 0) {
                        if (grid[positionX][positionY] != "vertical") {
                            for (int counter = 0; counter < count; counter++) {
                                if (positionX - 1 >= 0) {
                                    if (grid[positionX - 1][positionY] == null || grid[positionX - 1][positionY] == "horizontal") {
                                        positionX -= 1;
                                    } else if (grid[positionX - 1][positionY] == "vertical") {
                                        positionX -= 1;
                                        break;
                                    }
                                } else break;
                            }
                        }
                    } else if (count < 0) {
                        count *= -1;
                        go("[ea" + "," + count + "]");
                    }
                    break;
            }

        }
        return "(" + positionX + "," + positionY + ")";
    }
}