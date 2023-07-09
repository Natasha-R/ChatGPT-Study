package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;

public class Space extends  AbstractEntity{

    @Getter
    private int height;
    @Getter
    private int width;
    @Getter
    private ArrayList<Wall> walls = new ArrayList<>();

    public Space (int height, int width){
        this.height = height;
        this.width = width;
    }





}

/*
* private int checkCollisionwithWallForMoveableDistance(UUID cleaningDeviceId, String movementAxis, int input_distance, int direction) {
        UUID tempSpace = getObjectFromArraylist(cleaningDevices, cleaningDeviceId).getCurrentSpace();
        ArrayList<Wall> tempWalls = getObjectFromArraylist(spaces, tempSpace).getWalls();
        int x = getObjectFromArraylist(cleaningDevices, cleaningDeviceId).getX();
        int y = getObjectFromArraylist(cleaningDevices, cleaningDeviceId).getY();
        int temp_x = x + direction * input_distance;
        int temp_y = y + direction * input_distance;

        for (Wall wall: tempWalls) {


            String segments[] = wall.getWallstring().split("-");

            if (movementAxis == "x") {
                int wallLengthY1 = getXorYfromString(segments[0], "y");
                int wallLengthY2 = getXorYfromString(segments[1], "y");
                int x1 = getXorYfromString(segments[0], "x");

                if (wallLengthY1 < y && y < wallLengthY2) {
                    if (x < x1 && x1 < temp_x) {
                        temp_x = x1-1;
                    } else if (x1 < x && temp_x < x1) {
                        temp_x = x1+1;
                    }
                }

            } else if (movementAxis == "y") {
                int wallLengthx1 = getXorYfromString(segments[0], "x");
                int wallLengthx2 = getXorYfromString(segments[1], "x");
                int y1 = getXorYfromString(segments[0], "y");

                if(wallLengthx1 < x && x < wallLengthx2) {
                    if (y < y1 && y1 < temp_y) {
                        temp_y = y1-1;
                    } else if (y1 < y && temp_y < y1) {
                        temp_y = y1+1;
                    }
                }

            }



        }
*
* */

/*
* private int getXorYfromString(String coordinates, String xy){
        String segments[] = coordinates.split(",");

        if(xy == "x"){
            return Integer.parseInt(segments[0].replace("("," ").trim());

        }else if(xy == "y"){
            return Integer.parseInt(segments[1].replace(")"," ").trim());
        }
        //wrong input
        return -1;

    }
* */