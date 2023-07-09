package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {

    //initialisieren und deklarieren der Startwerte x und y
    private int x = 4;
    private int y = 0;

    //Initialiesieren der Barrieren als listen
    private List<String> firstBarrier = Arrays.asList("(1,0)", "(1,1)", "(1,2)", "(1,3)", "(1,4)", "(1,5)");
    private List<String> secondBarrier = Arrays.asList("(1,6)","(2,6)", "(3,6)");
    private List<String> thirdBarrier = Arrays.asList("(5,6)","(6,6)");
    private List<String> fourthBarrier = Arrays.asList("(7,1)", "(7,2)", "(7,3)", "(7,4)", "(7,5)");


    //Implementierung der Methode walkTo()
    @Override
    public String walkTo(String walkCommandString) {
        //throw new UnsupportedOperationException();


        StringBuilder numberOfSteps = new StringBuilder();

        String finalStringValue = "("+ x + "," + y + ")";



        //prüft die richtung in der der Bagger fährt
        if(walkCommandString.contains("no") || walkCommandString.contains("ea") || walkCommandString.contains("so")
            || walkCommandString.contains("we")){



            //liest die Zahlen im string
            for(char s : walkCommandString.toCharArray()){
                if(Character.isDigit(s))
                    numberOfSteps.append(s);

            }
            String stringSteps = numberOfSteps.toString();
            int intSteps = Integer.parseInt(stringSteps);


            if(walkCommandString.contains("no")){   // wird ausgeführt wenn der string 'no' enthält
                if(intSteps == 0){return finalStringValue;}

                if(intSteps == 1){
                    y++;
                    finalStringValue = "(" + x + "," + y + ")";
                    if(!secondBarrier.contains(finalStringValue) && !thirdBarrier.contains(finalStringValue)){
                        return finalStringValue;
                    }else{
                        y--;
                        finalStringValue = "(" + x + "," + y + ")";
                        return finalStringValue;
                    }
                }

                y++;
                finalStringValue = "(" + x + "," + y + ")";
                for(int i=0; i<intSteps-1; i++){
                    if(!secondBarrier.contains(finalStringValue) && !thirdBarrier.contains(finalStringValue)){
                        y++;
                        finalStringValue = "(" + x + "," + y + ")";
                    } else {
                        y--;
                        finalStringValue = "(" + x + "," + y + ")";
                        break;
                    }
                }

                if(y > 7){
                    y=7;
                    finalStringValue = "(" + x + "," + y + ")";
                }

            } else if (walkCommandString.contains("so")){   //wird ausgeführt wenn der string so enthält

                for(int iterator=0; iterator<intSteps; iterator++){
                    if(!secondBarrier.contains(finalStringValue) && !thirdBarrier.contains(finalStringValue)){
                        y--;
                        finalStringValue = "(" + x + "," + y + ")";
                    } else
                        break;
                }

                if(y<0){
                    y=0;
                    finalStringValue = "(" + x + "," + y + ")";
                }


            } else if (walkCommandString.contains("ea")){   // wird ausgeführt wenn der string ea enthält
                if(intSteps ==0){return finalStringValue;}

                if(intSteps == 1){
                    x++;
                    finalStringValue = "(" + x + "," + y + ")";
                    if(!firstBarrier.contains(finalStringValue) && !fourthBarrier.contains(finalStringValue)){
                        return finalStringValue;
                    }else{
                        x--;
                        finalStringValue = "(" + x + "," + y + ")";
                        return finalStringValue;
                    }
                }

                x++;
                finalStringValue = "(" + x + "," + y + ")";
                for(int iterator=0; iterator<intSteps-1; iterator++){


                    if(!firstBarrier.contains(finalStringValue) && !fourthBarrier.contains(finalStringValue)){
                        x++;
                        finalStringValue = "(" + x + "," + y + ")";
                    } else {
                        x--;
                        finalStringValue = "(" + x + "," + y + ")";
                        break;//
                    }
                }


                if(x > 10){
                    x=10;
                    finalStringValue = "(" + x + "," + y + ")";
                }

            } else if(walkCommandString.contains("we")){ //Wird ausgeführt, wenn der String 'we' enthält

                for(int iterator=0; iterator<intSteps; iterator++){
                    if(!firstBarrier.contains(finalStringValue) && !fourthBarrier.contains(finalStringValue)){
                        x--;
                        finalStringValue = "(" + x + "," + y + ")";
                    } else
                        break;
                }

                if(x<0){
                    x=0;
                    finalStringValue = "(" + x + "," + y + ")";
                }
            }


        }


        return finalStringValue;
    }
}
