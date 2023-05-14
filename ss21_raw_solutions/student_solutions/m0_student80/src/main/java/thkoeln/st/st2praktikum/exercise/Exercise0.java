package thkoeln.st.st2praktikum.exercise;


public class Exercise0 implements Walkable {
    String robotPosition = "3,0";

    @Override
    public String walk(String walkCommandString) {
        int xMax = 11;
        int yMax = 7;
        Boolean theBreak = false;

        int commandsAmount = walkCommandString.length();

        String[] verticalWallPostions = new String[]{
                "3,0",
                "3,1",
                "3,2",
                "3,3",
                "7,0",
                "7,1",
                "7,2",
        };
        String[] horizontalWallPostions = new String[]{
                "4,3",
                "5,3",
                "6,3",
                "7,3",
                "1,4",
                "2,4",
                "3,4",
                "4,4",
                "5,4",
                "6,4",
                "7,4",
                "8,4",
        };

        if (walkCommandString.charAt(1) == 'n') {
            StringBuilder intXRobotPosition = new StringBuilder();
            StringBuilder intYRobotPosition = new StringBuilder();
            StringBuilder integerString = new StringBuilder();
            for (int i = 4; i < walkCommandString.length() - 1; i++) {
                integerString.append(walkCommandString.charAt(i));
            }
            int steps = Integer.parseInt(integerString.toString());
            for (int i = 2; i < robotPosition.length(); i++) {
                intYRobotPosition.append(robotPosition.charAt(i));
            }
            int robotYPosition = Integer.parseInt(intYRobotPosition.toString());
            if (robotPosition.charAt(1) == ',') {
                intXRobotPosition.append(robotPosition.charAt(0));
            } else {
                intXRobotPosition.append(robotPosition.charAt(0));
                intXRobotPosition.append(robotPosition.charAt(1));
            }
            int robotXPosition = Integer.parseInt(intXRobotPosition.toString());


            for (int j = 1; j <= steps; j++) {
                int temp = robotYPosition + 1;
                String tempString = robotXPosition + "," + temp;
                for (String horizontalWallPostion : horizontalWallPostions) {
                    if (tempString.equals(horizontalWallPostion)) {
                        theBreak = true;

                    }
                }
                if (temp <= yMax && !theBreak) {
                    robotYPosition += 1;
                } else {
                    break;
                }
            }
            robotPosition = +robotXPosition + "," + robotYPosition;

        }
        if (walkCommandString.charAt(1) == 'e') {
            StringBuilder intXRobotPosition = new StringBuilder();
            StringBuilder intYRobotPosition = new StringBuilder();
            StringBuilder integerString = new StringBuilder();
            for (int i = 4; i < walkCommandString.length() - 1; i++) {
                integerString.append(walkCommandString.charAt(i));
            }
            int steps = Integer.parseInt(integerString.toString());
            for (int i = 2; i < robotPosition.length(); i++) {
                intYRobotPosition.append(robotPosition.charAt(i));
            }
            int robotYPosition = Integer.parseInt(intYRobotPosition.toString());
            if (robotPosition.charAt(1) == ',') {
                intXRobotPosition.append(robotPosition.charAt(0));
            } else {
                intXRobotPosition.append(robotPosition.charAt(0));
                intXRobotPosition.append(robotPosition.charAt(1));
            }
            int robotXPosition = Integer.parseInt(intXRobotPosition.toString());

            for (int j = 1; j <= steps; j++) {
                int temp = robotXPosition + 1;
                String tempString = temp + "," + robotYPosition;
                for (String verticalWallPostion : verticalWallPostions) {
                    if (tempString.equals(verticalWallPostion)) {
                        theBreak = true;

                    }
                }
                if (temp <= xMax && !theBreak) {
                    robotXPosition += 1;
                } else {
                    break;
                }
            }
            robotPosition = +robotXPosition + "," + robotYPosition;

        }
        if (walkCommandString.charAt(1) == 's') {
            StringBuilder intXRobotPosition = new StringBuilder();
            StringBuilder intYRobotPosition = new StringBuilder();
            StringBuilder integerString = new StringBuilder();
            for (int i = 4; i < walkCommandString.length() - 1; i++) {
                integerString.append(walkCommandString.charAt(i));
            }
            int steps = Integer.parseInt(integerString.toString());
            for (int i = 2; i < robotPosition.length(); i++) {
                intYRobotPosition.append(robotPosition.charAt(i));
            }
            int robotYPosition = Integer.parseInt(intYRobotPosition.toString());
            if (robotPosition.charAt(1) == ',') {
                intXRobotPosition.append(robotPosition.charAt(0));
            } else {
                intXRobotPosition.append(robotPosition.charAt(0));
                intXRobotPosition.append(robotPosition.charAt(1));
            }
            int robotXPosition = Integer.parseInt(intXRobotPosition.toString());

            for (int j = 1; j <= steps; j++) {
                int temp = robotYPosition - 1;
                String tempString = robotXPosition + "," + temp;
                for (String horizontalWallPostion : horizontalWallPostions) {
                    if (tempString.equals(horizontalWallPostion)) {
                        theBreak = true;
                        robotYPosition -= 1;

                    }
                }
                if (temp >= 0 && !theBreak) {
                    robotYPosition -= 1;
                } else {
                    break;
                }
            }
            robotPosition = +robotXPosition + "," + robotYPosition;

        }
        if (walkCommandString.charAt(1) == 'w') {
            StringBuilder intXRobotPosition = new StringBuilder();
            StringBuilder intYRobotPosition = new StringBuilder();
            StringBuilder integerString = new StringBuilder();
            for (int i = 4; i < walkCommandString.length() - 1; i++) {
                integerString.append(walkCommandString.charAt(i));
            }
            int steps = Integer.parseInt(integerString.toString());
            for (int i = 2; i < robotPosition.length(); i++) {
                intYRobotPosition.append(robotPosition.charAt(i));
            }
            int robotYPosition = Integer.parseInt(intYRobotPosition.toString());
            if (robotPosition.charAt(1) == ',') {
                intXRobotPosition.append(robotPosition.charAt(0));
            } else {
                intXRobotPosition.append(robotPosition.charAt(0));
                intXRobotPosition.append(robotPosition.charAt(1));
            }
            int robotXPosition = Integer.parseInt(intXRobotPosition.toString());

            for (int j = 1; j <= steps; j++) {
                int temp = robotXPosition - 1;
                String tempString = temp + "," + robotYPosition;
                for (String verticalWallPostion : verticalWallPostions) {
                    if (tempString.equals(verticalWallPostion)) {
                        theBreak = true;
                        robotXPosition -= 1;

                    }
                }
                if (temp >= 0 && !theBreak) {
                    robotXPosition -= 1;
                } else {
                    break;
                }
            }
            robotPosition = +robotXPosition + "," + robotYPosition;


        }


        return "(" + robotPosition + ")";
    }
}



