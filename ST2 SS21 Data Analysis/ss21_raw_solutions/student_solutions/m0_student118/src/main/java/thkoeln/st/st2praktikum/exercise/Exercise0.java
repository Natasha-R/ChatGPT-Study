package thkoeln.st.st2praktikum.exercise;

import org.modelmapper.internal.Pair;

import javax.persistence.criteria.CriteriaBuilder;

public class Exercise0 implements GoAble {

    int X_MAX = 10;
    int Y_MAX = 7;
    int X = 1;
    int Y = 6;


    @Override
    public String go(String goCommandString) {

        int steps = Integer.parseInt(String.valueOf(goCommandString.charAt(4)));

        //north
        if (goCommandString.charAt(1)=='n') {
            if ((X>=1 && X<=8) && Y<5) {
                if (Y+steps>4) Y=4;
                else Y +=steps;

            }
            else if (X==0 && Y<6){
                if (Y+steps>5) Y=5;
                else Y-=steps;

            }
            else if (X==1&&Y==5) Y=5;
            else if (steps+Y>Y_MAX) Y= Y_MAX;
            else Y +=steps;
        }
        //south
        if (goCommandString.charAt(1)=='s') {
            if ((X>=2 && X<=8) && Y>5){
                if (Y-steps<5) Y=5;
                else Y-=steps;

            }
            else if (X<2 && Y>5){
                if (Y-steps<6) Y=6;
                else Y-=steps;

            }
            else if (X==1 && Y==5) Y=5;
            else if (Y-steps<0) Y= 0;
            else Y-=steps;

        }
        //east
        if (goCommandString.charAt(1)=='e'){
            if ((Y>=1 && Y<=4) && X<9){
                if (X+steps>8) X=8;
                else X+=steps;


            }
            else if (Y>5 && X<3){
                if (X+steps>2) X=2;
                else X+=steps;
            }
            else if (steps+X>X_MAX) X=X_MAX;
            else X+=steps;
        }
        //west
        if (goCommandString.charAt(1)=='w'){
            if ((Y>=1 && Y<=4) && X>8){
                if (X-steps<9) X=9;
                else X-=steps;

            }
            else if (Y>5 && X>2){
                if (X-steps<3) X=3;
                else X-=steps;

            }
            else if (X-steps<0) X=0;
            else X-=steps;
        }

        return "("+X+","+Y+")";
    }
    public static void main(String args[]){

        Exercise0 ex = new Exercise0();


        System.out.println(ex.go("[no,2]"));
        System.out.println(ex.go("[ea,5]"));
        System.out.println(ex.go("[we,3]"));
        System.out.println(ex.go("[we,4]"));

    }
}

