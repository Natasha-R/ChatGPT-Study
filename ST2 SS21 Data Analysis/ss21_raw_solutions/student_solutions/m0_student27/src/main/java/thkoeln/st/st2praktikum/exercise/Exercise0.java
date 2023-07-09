package thkoeln.st.st2praktikum.exercise;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {

    private int x = 11;
    private int y = 7;

    //private ArrayList<String> firstBlockade;
    private List<String> firstBlockade = Arrays.asList("(6,2)", "(6,3)", "(6,4)", "(6,5)");
    private List<String> secondBlockade = Arrays.asList("(6,5)","(5,5)" );
    private List<String> thirdBlockade = Arrays.asList("(4,5)","(5,5)");
    private List<String> fourthBlockade = Arrays.asList("(6,6)", "(7,6)", "(8,6)", "(9,6)", "(10,6)","(11,6)","(12,6)");


    @Override
    public String walkTo(String walkCommandString) {
        //throw new UnsupportedOperationException();
        StringBuilder numberOfSteps = new StringBuilder();
        String resultString = "("+ x + "," + y + ")";



        //checks if the string-parameter contains a correct direction
        if(walkCommandString.contains("no") || walkCommandString.contains("ea") || walkCommandString.contains("so")
                || walkCommandString.contains("we")){



            //reads the digits in the String and converts it to an int
            for(char s : walkCommandString.toCharArray()){
                if(Character.isDigit(s))
                    numberOfSteps.append(s);

            }
            String stringSteps = numberOfSteps.toString();
            int intSteps = Integer.parseInt(stringSteps);


            if(walkCommandString.contains("no")){
                if(intSteps == 0){return resultString;}

                if(intSteps == 1){
                    y++;
                    resultString = "(" + x + "," + y + ")";
                    if(!secondBlockade.contains(resultString) && !fourthBlockade.contains(resultString)){
                        return resultString;
                    }else{
                        resultString = "(" + x + "," + y + ")";
                        return resultString;
                    }
                }

                y++;
                resultString = "(" + x + "," + y + ")";
                for(int i=0; i<intSteps; i++){
                    if(!secondBlockade.contains(resultString) && !fourthBlockade.contains(resultString)){
                        y++;
                        resultString = "(" + x + "," + y + ")";
                    } else {
                        resultString = "(" + x + "," + y + ")";
                        break;
                    }
                }

                if(y > 7){
                    y=7;
                    resultString = "(" + x + "," + y + ")";
                }

            } else if (walkCommandString.contains("so")){

                for(int i=0; i<intSteps; i++){
                    if(!secondBlockade.contains(resultString) && !fourthBlockade.contains(resultString)){
                        y--;
                        resultString = "(" + x + "," + y + ")";
                    } else {
                        resultString = "(" + x + "," + y + ")";
                        break;
                    }
                }

                if(y<0){
                    y=0;
                    resultString = "(" + x + "," + y + ")";
                }


            } else if (walkCommandString.contains("ea")){
                if(intSteps ==0){return resultString;}

                if(intSteps == 1){
                    x++;
                    resultString = "(" + x + "," + y + ")";
                    if(!firstBlockade.contains(resultString) && !thirdBlockade.contains(resultString)){
                        x--;
                        resultString = "(" + x + "," + y + ")";
                        return resultString;
                    }else{

                        return resultString;
                    }
                }

                x++;
                resultString = "(" + x + "," + y + ")";
                for(int i=0; i<intSteps-1; i++){
                    if(!firstBlockade.contains(resultString) && !thirdBlockade.contains(resultString)){
                        x++;
                        resultString = "(" + x + "," + y + ")";
                    } else {
                        x--;
                        resultString = "(" + x + "," + y + ")";
                        break;//
                    }
                }


                if(x > 11){
                    x=11;
                    resultString = "(" + x + "," + y + ")";
                }

            } else if(walkCommandString.contains("we")){
                /*x-=intSteps;*/
                for(int i=0; i<intSteps; i++){
                    if(!firstBlockade.contains(resultString) && !thirdBlockade.contains(resultString)){
                        x--;
                        resultString = "(" + x + "," + y + ")";
                    } else{
                        resultString = "(" + x + "," + y + ")";
                        break;}
                }

                if(x<0){
                    x=0;
                    resultString = "(" + x + "," + y + ")";
                }
            }


        }
        //resultString = "(" + x + "," + y + ")";

        return resultString;
    }
}