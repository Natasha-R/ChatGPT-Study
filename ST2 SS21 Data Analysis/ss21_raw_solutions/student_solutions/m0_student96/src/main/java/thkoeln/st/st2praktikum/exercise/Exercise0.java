package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    //Initialize coordinates x+y and distance d
    int[][][] horizontalBarriers = {
            {{-1, -1}, {10, -1}},
            {{-1, 7}, {10, 7}},
            {{-1, 5}, {1, 5}},
            {{1, 4}, {8, 4}}
    };

    int[][][] verticalBarriers = {
            {{-1, -1}, {-1, 7}},
            {{10, -1}, {10, 7}},
            {{2, 6}, {2, 7}},
            {{8, 1}, {8, 4}}
    };

    int x = 1;
    int y = 6;



    public String barrierDetected(){
        //System.out.println("Barrier detected");
        //System.out.println( currentPosition() + " abort mission");
        return currentPosition();
    }

    public String step(int steps, int i){
        //System.out.println("Steps " + (steps - i) +" left");
        //System.out.println("New position " + currentPosition());
        return currentPosition();
    }

    public String currentPosition(){
        return "(" + x + "," + y + ")";
    }


    @Override
    public String goTo(String goCommandString) {
        System.out.println(goCommandString);
       int steps = Integer.parseInt(goCommandString.replaceAll("[^0-9]", ""));

        // Determine direction in if statement

        // going up
        if (goCommandString.contains("no")) {

            //Make a step while i < steps
            for (int i = 0; i < steps; i++) {

                // Check Barriers

                // Iterate over barriers
                for (int[][] horizontalBarrier : horizontalBarriers) {
                    //System.out.println("Check for barrier: " + j + " at " + currentPosition());
                    // Check if y Position is at hight of a barrier
                    if (y == horizontalBarrier[0][1]) {
                        // Check if x position is in between start and end of barrier
                        if (horizontalBarrier[0][0] <= x && x <= horizontalBarrier[1][0]) {
                            return barrierDetected();
                        }
                    }
                }
                y++;
                step(steps, i );
            }
            return currentPosition();

        } else if (goCommandString.contains("so")) {
            //Make a step while d
            for (int i = 0; i < steps; i++) {

                // Check Barriers

                // Iterate over barriers
                for (int[][] horizontalBarrier : horizontalBarriers) {
                    //System.out.println("Check for barrier: " + j + " at " + currentPosition());

                    // Check if y Position is at hight of a barrier
                    if (y == horizontalBarrier[0][1] + 1) {
                        // Check if x position is in between start and end of barrier
                        if (horizontalBarrier[0][0] <= x && x <= horizontalBarrier[1][0]) {
                            return barrierDetected();
                        }
                    }
                }
                y--;
                step(steps, i );
            }
            return currentPosition();


        } else if (goCommandString.contains("ea")) {
            //System.out.println("Going RIGHT from " + currentPosition());
            //Make a step while i < steps
            for (int i = 0; i < steps; i++) {

                // Check Barriers

                // Iterate over barriers
                for (int[][] verticalBarrier : verticalBarriers) {
                    //System.out.println("Check for barrier: " + j + " at " + currentPosition());

                    // Check if x Position is at hight of a barrier

                    if (x == verticalBarrier[0][0] && x != 0) {
                        // Check if y position is in between start and end of barrier
                        if (verticalBarrier[0][1] <= y && y <= verticalBarrier[1][1]) {
                            return barrierDetected();
                        }
                    }
                }
                // Make a step
                x++;
                step(steps, i);
            }
            return currentPosition();


        // Go LEFT
        } else if (goCommandString.contains("we")) {
            //System.out.println("Going LEFT from " + currentPosition());
            for (int i = 0; i < steps; i++) {

                // Check Barriers

                // Iterate over barriers
                for (int[][] verticalBarrier : verticalBarriers) {

                    // Check if x Position is at hight of a barrier
                    if (x == verticalBarrier[0][0] + 1) {
                        //System.out.println("Check for barrier: " + i);
                        // Check if y position is in between start and end of barrier
                        if (verticalBarrier[0][1] <= y && y <= verticalBarrier[1][1]) {
                            return barrierDetected();
                        }
                    }
                }
                // Make a step
                x--;
                step(steps, i);
            }
            return currentPosition();
        }

        return currentPosition();


    }


}