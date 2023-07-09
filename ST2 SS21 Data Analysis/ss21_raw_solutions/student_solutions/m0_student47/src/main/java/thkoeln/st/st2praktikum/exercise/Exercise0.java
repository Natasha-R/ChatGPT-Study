package thkoeln.st.st2praktikum.exercise;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Exercise0 implements Walkable {

Dot startpunkt = new Dot (5,3);


/*
    IntStream verticalEDGE1 = IntStream.range(2,8); // @ X = 6
    IntStream verticalEDGE2 = IntStream.range(1,3); // @ X = 4
    IntStream horizontalEDGE1 =  IntStream.range(3,9); //@ Y = 3
    IntStream horizontalEDGE2 =  IntStream.range(1,6); //@ Y = 6

 */
         /* borders
         (Y >= 0 && Y <=8 ) @X=0 /outer left border
                            @X=12/outer right border
          (X>=0 && X <= 12) @Y=0 /bottom border
                            @Y=8 /top border
          */


    @Override
    public String walk(String walkCommandString) {

        System.out.println("punkt:"+startpunkt.X+","+startpunkt.Y);

        String input = removeFirstandLast(walkCommandString);
        System.out.println("incoming STRING: "+input);

        String[] inputArray = input.split(",");
        System.out.println("command: "+Arrays.toString(inputArray));

        String input1 = inputArray[0];
        System.out.println("direction: "+input1);

        int input2 = Integer.parseInt(inputArray[1]);
        System.out.println( "Strecke: "+input2);





        switch(input1) {
            case ("we"):for(int i = 0; i<input2;i++){

                /*if(startpunkt.X == 4 && startpunkt.Y >= 1 && startpunkt.Y <= 3){
                    System.out.println("vertical barrier reached");
                    break;
                }
                
                 */
                if(startpunkt.X == 6 && startpunkt.Y >= 2 && startpunkt.Y <= 8){
                    System.out.println("vertical barrier reached");
                    break;
                }

                if(startpunkt.X==0 && startpunkt.Y >= 0 && startpunkt.Y <=8 ){
                    System.out.println("outer left border reached");
                    break;
                }
                startpunkt.X--;
            }
                break;

            case ("so"):for(int i = 0; i<input2;i++) {

                if(startpunkt.Y == 3 && startpunkt.X >= 3 && startpunkt.X <= 9){
                    System.out.println("horizontal barrier reached");
                    break;
                }
                if(startpunkt.Y == 6 && startpunkt.X >= 1 && startpunkt.X <= 6){
                    System.out.println("horizontal barrier reached");
                    break;
                }

                if(startpunkt.Y==0 && startpunkt.X >= 0 && startpunkt.X <=12 ){
                    System.out.println("bottom border reached");
                    break;
                }

                startpunkt.Y--;
            }
                break;

            case ("ea"):for(int i = 0; i<input2;i++) {

                if(startpunkt.X+1 == 4 && startpunkt.Y >= 1 && startpunkt.Y <= 3){
                    System.out.println("vertikal barrier reached");
                    break;
                }
                if(startpunkt.X+1 == 6 && startpunkt.Y >= 2 && startpunkt.Y <= 8){
                    System.out.println("vertikal barrier reached");
                    break;
                }

                if(startpunkt.X+1==0 && startpunkt.Y >= 0 && startpunkt.Y <=8 ){
                    System.out.println("outer right border reached");
                    break;
                }
                startpunkt.X++;
            }
                break;

            case ("no"):for(int i = 0; i<input2;i++) {

                if(startpunkt.Y+1 == 3 && startpunkt.X >= 3 && startpunkt.X <= 9){
                    System.out.println("horizontal barrier reached");
                    break;
                }
                if(startpunkt.Y+1 == 6 && startpunkt.X >= 1 && startpunkt.X <= 6){
                    System.out.println("horizontal barrier reached");
                    break;
                }

                if(startpunkt.Y+1==8 && startpunkt.X >= 0 && startpunkt.X <=12 ){
                    System.out.println("top border reached");
                    break;
                }

                startpunkt.Y++;
            }


                break;
        }

        System.out.println("neuer punkt: "+"("+startpunkt.X +","+ startpunkt.Y+")");
        return "("+startpunkt.X +","+ startpunkt.Y+")";
    }
    public static String
    removeFirstandLast(String str)
    {

        // Creating a StringBuilder object
        StringBuilder sb = new StringBuilder(str);

        // Removing the last character
        // of a string
        sb.deleteCharAt(str.length() - 1);

        // Removing the first character
        // of a string
        sb.deleteCharAt(0);

        // Converting StringBuilder into a string
        // and return the modified string
        return sb.toString();
    }
    public static String

    addFirstandLast(String str)
    {

        // Creating a StringBuilder object
        StringBuilder sb = new StringBuilder(str);

        sb.insert(0,"(").append(")");

        // Converting StringBuilder into a string
        // and return the modified string
        return sb.toString();
    }


}
