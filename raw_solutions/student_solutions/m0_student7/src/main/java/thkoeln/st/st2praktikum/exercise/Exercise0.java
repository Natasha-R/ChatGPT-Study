package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {
    int[] limit = new int[]{12,9};
    int[] robot = {8, 3};
    int[][] walls = {{4, 1, 4, 7}, {6, 2, 6, 5}, {6, 2, 9, 2} ,{6, 5, 9, 5}};

    @Override
    public String move(String moveCommandString) {
        String result = result = "("+robot[0]+","+robot[1]+")";
        String[] command = moveCommandString.substring(1,moveCommandString.length()-1).split(",");
        int[]movement;
        switch (command[0]){    //direction
            case "no":
                movement = new int[]{0, 1};
                break;
            case "ea":
                movement = new int[]{1, 0};
                break;
            case "so":
                movement = new int[]{0, -1};
                break;
            case "we":
                movement = new int[]{-1, 0};
                break;
            default:
                return result;
        }
        for (int i = 0; i < Integer.parseInt(command[1]); i++){ //walls
            if(movement[1] == 1){
                int dest = robot [1] + movement[1];
                for (int[] elem: walls) {
                    if(elem[1] == elem[3] && elem[3]  == dest
                            && robot[0] >= elem[0] && robot[0] < elem[2]){
                        return result;
                    }
                }
            }if(movement[1] == -1){
                for (int[] elem: walls) {
                    if(elem[1] == elem[3] && elem[3]  == robot[1]
                            && robot[0] >= elem[0] && robot[0] < elem[2]){
                        return result;
                    }
                }
            }if(movement[0] == 1){
                int dest = robot [0] + movement[0];
                for (int[] elem: walls) {
                    if(elem[0] == elem[2] && elem[2]  == dest
                            && robot[1] >= elem[1] && robot[1] < elem[3]){
                        return result;
                    }
                }
            }if(movement[0] == -1){
                for (int[] elem: walls) {
                    if(elem[0] == elem[2] && elem[2]  == robot[0]
                            && robot[1] >= elem[1] && robot[1] < elem[3]){
                        return result;
                    }
                }
            }
            if(robot[0] + movement[0] < 0 || robot[0] + movement[0] > limit[0]-1 || robot[1] + movement[1] < 0 || robot[1] + movement[1] > limit[1]-1)
                return result;  //boundary
            robot[0] += movement[0];
            robot[1] += movement[1];
            result = "("+robot[0]+","+robot[1]+")";
        }
        return result;
    }
}

