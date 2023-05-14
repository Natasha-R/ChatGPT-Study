package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Exercise0 implements Walkable {

    @Override
    public String walk(String walkCommandString) {
        String command[]=walkCommandString.replace("[","")
                .replace("]","").split(",");
        String dir=command[0];
        int move =Integer.parseInt(command[1]);



        startmove(dir,move);
        return dvice.toString();
    }


    Point dvice;
    int max_x = 10, max_y = 7;
    List<Wall> wallList = new ArrayList<>();

    public Exercise0() {
        dvice = new Point(7, 7);

        Point wallpoint1 = new Point(10, 8);
        Point wallpoint2 = new Point(10, 1);
        Point wallpoint3 = new Point(2, 1);
        Point wallpoint4 = new Point(2, 6);
        Point wallpoint5 = new Point(7, 6);
        wallList.add(new Wall(wallpoint2, wallpoint1));
        wallList.add(new Wall(wallpoint3, wallpoint2));
        wallList.add(new Wall(wallpoint3, wallpoint4));
        wallList.add(new Wall(wallpoint4, wallpoint5));

    }

    String startmove(String dir,int movment) {

        switch (dir) {
            case "no":
                vertical(movment, 1);
                break;
            case "so":vertical(movment, -1);
                break;
            case "ea":horizontally(movment,1);
                break;
            case "we":horizontally(movment,-1);
                break;
        }
        return "";
    }

    private void horizontally (int movment, int dr) {
        int correct=0;if(dr>0)correct=1;

        for (int i = 1; i <= movment; i++) {
            boolean move = true;
            // check if dvice rich the end of map
            if (dvice.x + dr <= max_x && dvice.x + dr >= 0) {
                for (Wall wall : wallList) {
                    // check if wall vertically
                    if (wall.getStart().getX() == wall.getEnd().getX()) {
                        if (dvice.getX()+correct == wall.getStart().getX() && dvice.getY() >= wall.getStart().getY() && dvice.getY() < wall.getEnd().getY()) {
                            move = false;
                            break;
                        }
                    }


                }
            }else move = false;
            if (move) dvice.setX(dvice.getX()+dr);
            else break;

        }


    }



    private void vertical(int movment, int dr) {
        int correct=0;if(dr>0)correct=1;
        for (int i = 1; i <= movment; i++) {
            boolean move = true;
            // check if dvice rich the end of map
            if (dvice.y + dr <= max_y && dvice.y + dr >= 0) {
                for (Wall wall : wallList) {
                    // check if wall horizontally
                    if (wall.getStart().getY() == wall.getEnd().getY()) {
                        if (dvice.getY()+correct == wall.getStart().getY() && dvice.getX() >= wall.getStart().getX() && dvice.getX() < wall.getEnd().getX()) {
                            move = false;
                            break;
                        }
                    }


                }
            } else move = false;
            if (move) dvice.setY(dvice.getY()+dr);
            else break;

        }


    }


}
