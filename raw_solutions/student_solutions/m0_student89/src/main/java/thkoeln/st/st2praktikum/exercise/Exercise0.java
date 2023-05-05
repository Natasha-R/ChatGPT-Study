package thkoeln.st.st2praktikum.exercise;

import javassist.bytecode.stackmap.BasicBlock;

import java.io.Console;

public class Exercise0 implements Moveable {

    public static String [][] position = new String[11][8];
    int xstart=7;
    int ystart=7;
    /*Wand1
    String wall1_1="(9,1)-(9,7)";
    String wall2_1="(9,1)-(2,1)";
    String wall3_1="(2,1)-(2,5)";
    String wall4_1="(2,5)-(6,5)";
    Wand2
    String wall1="(10,0)-(10,7)";
    String wall2="(10,0)-(1,0)";
    String wall3="(1,0)-(1,6)";
    String wall4="(1,6)-(6,6)";*/


    @Override
    public String move(String moveCommandString) {
        String direction=""+moveCommandString.toCharArray()[1]+moveCommandString.toCharArray()[2]+"";
        char c =(moveCommandString.charAt(4));
        int moves= Integer.parseInt(moveCommandString.replaceAll("\\D", ""));
        System.out.println(moves);
        Waendebauen();

        int takensteps=0;
        System.out.println(moves);

        try {

            if (direction.equals("no")) {
                if (moves>position[0].length-1-ystart)
                {
                    moves=position[0].length-1-ystart;
                }

                while (takensteps < moves) {
                    if (position[xstart][ystart + takensteps] == "Wand1" && position[xstart][ystart + takensteps + 1] == "Wand2") {
                        ystart = ystart + takensteps;
                        return "(" + xstart + "," + ystart + ")";
                    }


                    if (position[xstart][ystart + takensteps] == "Wand2" && position[xstart][ystart + takensteps + 1] == "Wand1") {
                        ystart = ystart + takensteps;
                        return "(" + xstart + "," + ystart + ")";
                    } else {
                        takensteps += 1;
                    }
                }

                    ystart = ystart + takensteps;

                }

            if (direction.equals("ea")) {

                if (moves>position.length-1-xstart)
                {
                    moves=position.length-1-xstart;
                }
                while (takensteps < moves ) {
                    if (position[xstart + takensteps ][ystart] == "Wand1"&&position[xstart + takensteps + 1][ystart] == "Wand2")
                    {
                        xstart=xstart+takensteps;
                        return "(" + xstart + "," + ystart + ")";
                    }
                    if (position[xstart + takensteps ][ystart] == "Wand2"&&position[xstart + takensteps + 1][ystart] == "Wand1")
                    {
                        xstart=xstart+takensteps;
                        return "(" + xstart + "," + ystart + ")";
                    }
                    else{
                        takensteps += 1;
                    }
                }
                xstart = xstart + takensteps;

            }
            if (direction.equals("so")) {

                if (ystart-moves<0)
                {
                    moves=ystart;
                }
                while (takensteps < moves ) {
                    if (position[xstart][ystart - takensteps ] == "Wand1"&&position[xstart][ystart - takensteps - 1] == "Wand2")
                    {
                        ystart = ystart - takensteps;
                        return "(" + xstart + "," + ystart + ")";
                    }
                    if (position[xstart][ystart - takensteps ] == "Wand2"&&position[xstart][ystart - takensteps - 1] == "Wand1")
                    {
                        ystart = ystart - takensteps;
                        return "(" + xstart + "," + ystart + ")";
                    }

                    else {
                        takensteps += 1;
                    }
                }
                ystart = ystart - takensteps;


            }
            if (direction.equals("we")) {
                if (xstart-moves<0)
                {
                    moves=xstart;
                }

                while (takensteps < moves ) {
                    if (position[xstart - takensteps ][ystart] == "Wand1"&&position[xstart - takensteps - 1][ystart] == "Wand2")
                    {
                        xstart=xstart-takensteps;
                        return "(" + xstart + "," + ystart + ")";
                    }
                    if (position[xstart - takensteps ][ystart] == "Wand2"&&position[xstart - takensteps - 1][ystart] == "Wand1")
                    {
                        xstart=xstart-takensteps;
                        return "(" + xstart + "," + ystart + ")";
                    }
                    else
                    {
                        takensteps += 1;
                    }
                }
                xstart = xstart - takensteps;

            }
            return "(" + xstart + "," + ystart + ")";

        } catch(java.lang.ArrayIndexOutOfBoundsException use)
        {
            switch (direction) {
                case "no": ystart = ystart + takensteps;
                    return "(" + xstart + "," + ystart + ")";
                case "ea": xstart = xstart + takensteps;
                    return "(" + xstart + "," + ystart + ")";
                case "so": ystart = ystart - takensteps;
                    return "(" + xstart + "," + ystart + ")";
                case "we": xstart = xstart - takensteps;
                    return "(" + xstart + "," + ystart + ")";
            }
            return "(" + xstart + "," + ystart + ")";
        }
    }



    public static void Waendebauen() {
        //Wand1
        String wall1_1="(9,1)-(9,7)";
        String wall2_1="(9,1)-(2,1)";
        String wall3_1="(2,1)-(2,5)";
        String wall4_1="(2,5)-(6,5)";
        //Wand2
        String wall1="(10,0)-(10,7)";
        String wall2="(10,0)-(1,0)";
        String wall3="(1,0)-(1,6)";
        String wall4="(1,6)-(6,6)";
//wand 1
        for (int j = 1; j <= 7; j++) {
            position[9][j] = "Wand1";
        }


        for (int j = 2; j <= 9; j++) {
            position[j][1] = "Wand1";
        }


        for (int j = 1; j <= 5; j++) {
            position[2][j] = "Wand1";
        }


        for (int j = 2; j <= 6; j++) {
            position[j][5] = "Wand1";
        }





        //wand 2
        for (int j = 0; j <= 7; j++) {
            position[10][j] = "Wand2";
        }


        for (int j = 1; j <= 10; j++) {
            position[j][0] = "Wand2";
        }


        for (int j = 0; j <= 6; j++) {
            position[1][j] = "Wand2";
        }


        for (int j = 1; j <= 6; j++) {
            position[j][6] = "Wand2";
        }


    }public static void main(String[] args) {
        Waendebauen();
        System.out.println(position[1][1]);

      /*  for ( int zeile = 0; zeile < position.length; zeile++ )
        {
            System.out.print("Zeile " + zeile + ": ");
            for ( int spalte=0; spalte < position[zeile].length; spalte++ )
                System.out.print( position[zeile][spalte] + " ");
            System.out.println();

        }*/


    }


}
