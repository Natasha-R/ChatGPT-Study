package thkoeln.st.st2praktikum.exercise;

import javax.persistence.criteria.CriteriaBuilder;

public class Exercise0 implements Moveable {

    Integer horizontal = 5;
    Integer vertikal   = 3;

    @Override
    public String move(String moveCommandString) {
/*
        String barrier1 = "(1,6)-(5,6)";
        String barrier2 = "(3,3)-(8,3)";
        String barrier3 = "(4,1)-(4,2)";
        String barrier4 = "(6,2)-(6,7)";

 */





        int steps = Integer.parseInt(String.valueOf(moveCommandString.charAt(4)));


        if ( moveCommandString.charAt(1) == 'n') {


            if (horizontal >= 3 && horizontal <= 8) {
                if (vertikal < 3) {
                    if (vertikal + steps >= 2) {
                        vertikal = 2;
                        return "("+horizontal+","+vertikal+")";
                    }
                }
            }

            if ( horizontal >= 1 && horizontal <= 5) {
                if ( vertikal < 7 ){
                    if ( vertikal + steps >= 7){
                        vertikal = 6;
                        return "("+horizontal+","+vertikal+")";
                    }
                }
            }

            if (vertikal + steps > 7) {
                vertikal = 7;
                return "("+horizontal+","+vertikal+")";
            }



                vertikal += steps;
                return "("+horizontal+","+vertikal+")";

        }





        if ( moveCommandString.charAt(1) == 'e'){

            if ( vertikal == 1 || vertikal == 2 ) {
                if (horizontal < 4) {
                    if (horizontal + steps > 3) {
                        horizontal = 3;
                        return "("+horizontal+","+vertikal+")";
                    }
                }
            }

            if (vertikal >= 2 && vertikal <= 7) {
                if (horizontal < 6) {
                    if (horizontal + steps > 5) {
                        horizontal = 5;
                        return "("+horizontal+","+vertikal+")";
                    }
                }
            }

            if ( horizontal > 11 ){
                horizontal = 11;
                return "("+horizontal+","+vertikal+")";
            }

                horizontal += steps;
                return "("+horizontal+","+vertikal+")";

        }






        if ( moveCommandString.charAt(1) == 's'){

            if ( horizontal >=1 && horizontal <= 5 ){
                if ( vertikal > 5){
                    if ( vertikal - steps < 6){
                        vertikal = 6;
                        return "("+horizontal+","+vertikal+")";
                    }
                }
            }

            if ( horizontal >= 3 && horizontal <= 8){
                if (vertikal > 2 ){
                    if ( vertikal - steps < 3){
                        vertikal = 3;
                        return "("+horizontal+","+vertikal+")";
                    }
                }
            }

            if ( vertikal - steps < 0 ){
                vertikal = 0;
                return "("+horizontal+","+vertikal+")";
            }

                vertikal -= steps;
                return "("+horizontal+","+vertikal+")";
        }




        if ( moveCommandString.charAt(1) == 'w'){

            if (vertikal >= 2 && vertikal <= 7){
                if ( horizontal > 5 ){
                    if ( horizontal - steps < 6){
                        vertikal = 6;
                        return "("+horizontal+","+vertikal+")";
                    }
                }
            }

            if ( vertikal == 1 || vertikal == 2  ){
                if ( horizontal > 3){
                    if ( horizontal - steps < 4) {
                        horizontal = 4;
                        return "("+horizontal+","+vertikal+")";
                    }
                }
            }

            if ( horizontal - steps <= 0 ){
                horizontal = 0;
                return "("+horizontal+","+vertikal+")";
            }
                horizontal -= steps;
                return "("+horizontal+","+vertikal+")";
        }

        return "("+horizontal+","+vertikal+")";
    }
}
