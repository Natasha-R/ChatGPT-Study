package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements  Walkable {

    Position cleaningdvice;
    int endof_x = 11, endof_y = 8;
    List<Dam> damList = new ArrayList<>();
    public Exercise0() {
        cleaningdvice = new Position(1, 7);
        Position damposition1 = new Position(3, 3);
        Position damposition2 = new Position(3, 9);
        Position damposition3 = new Position(5, 3);
        Position damposition4 = new Position(5, 0);
        Position damposition5 = new Position(5, 2);
        Position damposition6 = new Position(6, 0);
        Position damposition7 = new Position(6, 4);
        damList.add(new Dam(damposition1, damposition2));
        damList.add(new Dam(damposition1, damposition3));
        damList.add(new Dam(damposition4, damposition5));
        damList.add(new Dam(damposition6, damposition7));
    }


    String run_commend(String direction, int steps) {
        switch (direction) {
            case "no":
                up_and_down(steps, 1);
                break;
            case "so":
                up_and_down(steps, -1);
                break;
            case "ea":
                right_and_left(steps,1);
                break;
            case "we":
                right_and_left(steps,-1);
                break;
        }
        return "";
    }

    private void right_and_left(int movment, int right_or_left) {
        int right=0;if(right_or_left>0)right=1;
        for (int i = 1; i <= movment; i++) {
            boolean can_move = true;
            if (cleaningdvice.getX() + right_or_left <= endof_x && cleaningdvice.getX() + right_or_left >= 0) {
                for (Dam dam : damList) {
                    if (dam.getFrom().getX() == dam.getTo().getX()) {
                        if (cleaningdvice.getX()+right == dam.getFrom().getX() && cleaningdvice.getY() >= dam.getFrom().getY() && cleaningdvice.getY() < dam.getTo().getY()) {
                            can_move = false;
                            break;
                        }
                    }
                }
            }else can_move = false;
            if (can_move) cleaningdvice.setX(cleaningdvice.getX()+right_or_left);
            else break;
        }
    }



    private void up_and_down(int movment, int up_or_down) {
        int up=0;if(up_or_down>0)up=1;
        for (int i = 1; i <= movment; i++) {
            boolean can_move = true;
            if (cleaningdvice.getY() + up_or_down <= endof_y && cleaningdvice.getY() + up_or_down >= 0) {
                for (Dam dam : damList) {
                    if (dam.getFrom().getY() == dam.getTo().getY()) {
                        if (cleaningdvice.getY()+up == dam.getFrom().getY() && cleaningdvice.getX() >= dam.getFrom().getX() && cleaningdvice.getX() < dam.getTo().getX()) {
                            can_move = false;
                            break;
                        }
                    }
                }
            } else can_move = false;
            if (can_move) cleaningdvice.setY(cleaningdvice.getY()+up_or_down);
            else break;
        }
    }


    @Override
    public String walkTo(String walkCommandString) {
        String newcommand[]=walkCommandString.replace("[","").replace("]","").split(",");
        String direction=newcommand[0];
        int steps =Integer.parseInt(newcommand[1]);



        run_commend(direction,steps);
        return cleaningdvice.toString();
    }



}
