package thkoeln.st.st2praktikum.exercise;

import javax.persistence.criteria.CriteriaBuilder;

public class Exercise0 implements GoAble {


    static int [][] spaceship = new int[12][9];
    int startx = 7;
    int starty = 7;

   public static void startpunktmarkieren(){
       spaceship[7][7] = 2;



   }


    public static void waendebauen(){
        //wand 1
        for(int x = 1; x < 8; x++){
                    spaceship[10][x] = 1;
        }

        //wand2
         for(int x = 2; x <=8; x++){
                    spaceship[x][1] = 3;
        }

        //wand3
         for(int x = 1; x <=6; x++){
                    spaceship[2][x] = 1;
        }
        //wand4
         for(int x = 2; x <=7; x++){
                    spaceship[x][6] = 3;
        }
         //rand
        for(int x = 0; x < 12; x++){
            spaceship[x][0] = 5;
        }
        for(int x = 0; x < 12; x++){
            spaceship[x][8] = 4;
        }
        for(int x = 0; x < 9; x++){
            spaceship[11][x] = 4;
        }
        for(int x = 0; x < 9; x++){
            spaceship[0][x] = 4;
        }


    }




    @Override
    public String go(String goCommandString) {

       waendebauen();
       char zs = goCommandString.charAt(4);
       int zahl = Integer.parseInt(String.valueOf(zs));

       String richtung=""+goCommandString.toCharArray()[1]+goCommandString.toCharArray()[2]+"";


       if(richtung.equals("no")){
           for (int i = 1; i <= zahl; i++) {
               if (starty < 7  && spaceship[startx][starty+1] != 3) {
                   starty ++;
               }
           }
       }
        if (richtung.equals("so")) {
            for (int i = 1; i <= zahl; i++) {
                if(starty >= 1  && startx < spaceship.length - 1 && spaceship[startx+1][starty] != 3 || starty >= 1  && spaceship[startx][starty] != 3 ) {
                        starty--;
                    }
                }
        }
            if (richtung.equals("ea")) {
                for(int i = 1; i <= zahl; i++ ) {
                if ( startx <= spaceship.length && startx >= 0 && starty <= spaceship[0].length && spaceship[startx][starty] != 1
                        && spaceship[startx+1][starty] != 4 && spaceship[startx+1][starty] != 1) {
                    startx ++;
                }
            }
        }
        if(richtung.equals("we")) {
            for (int i = 1; i <= zahl; i++) {
                if (spaceship[startx][starty] != 4 && spaceship[startx][starty] != 1 && startx < spaceship.length) {
                    startx --;
                }
            }
        }
       String endposition = "("+ String.valueOf(startx)+"," + String.valueOf(starty)+ ")";

        System.out.println(richtung);
        System.out.println(endposition);


        return endposition;
    }

    public static void main (String[] args) {

        System.out.println(spaceship.length);
        waendebauen();
        startpunktmarkieren();
        for ( int zeile = 0; zeile < spaceship.length; zeile++ )
        {
            System.out.print("Zeile " + zeile + ": ");
            for ( int spalte=0; spalte < spaceship[zeile].length; spalte++ )
                System.out.print( spaceship[zeile][spalte] + " ");
            System.out.println();
        }


    }



}



