package thkoeln.st.st2praktikum.exercise;

import javassist.compiler.ast.Pair;

import java.awt.*;

public class Exercise0 implements Walkable {

    String Robotposition = new String("0,2");

    @Override
    public String walk(String walkCommandString) {
        int xMax = 11;
        int yMax = 8;
        boolean stop = false;


        String[] wallpositions = {"3,0","3,1","3,2","5,0","5,1","5,2","5,3","4,5","5,5","6,5","7,5",
                                  "7,6","7,7","7,8" };

        if(walkCommandString.charAt(1)== 'n') {
            StringBuilder intXRobotPosition = new StringBuilder();
            StringBuilder intYRobotPosition = new StringBuilder();
            StringBuilder integerString = new StringBuilder();
            StringBuilder intXwallposition = new StringBuilder();
            StringBuilder intYwallposition = new StringBuilder();
            for(int i=4; i<walkCommandString.length()-1; i++){
                integerString.append(walkCommandString.charAt(i));
            }
            int steps = Integer.parseInt(integerString.toString());
            if(Robotposition.charAt(2) != ',') {
                for (int i = 2; i < Robotposition.length(); i++) {
                    intYRobotPosition.append(Robotposition.charAt(i));
                }
            }
            if(Robotposition.charAt(2) == ',') {
                for (int i = 3; i < Robotposition.length(); i++) {
                    intYRobotPosition.append(Robotposition.charAt(i));
                }
            }
            int RobotYPosition = Integer.parseInt(intYRobotPosition.toString());
            if(Robotposition.charAt(1) == ',') {
                intXRobotPosition.append(Robotposition.charAt(0));
            } else {
                intXRobotPosition.append(Robotposition.charAt(0));
                intXRobotPosition.append(Robotposition.charAt(1));
            }
            int RobotXPosition = Integer.parseInt(intXRobotPosition.toString());



            for(int j=1; j<=steps; j++) {
                int X2Wallposition = 0;
               for (int i=8; i<=10;i++) {
                   char int2Xwallposition = wallpositions[i].charAt(0);
                   int Xwallposition = Integer.parseInt(String.valueOf(int2Xwallposition));
                   char int2Ywallposition = wallpositions[i].charAt(2);
                   int Ywallposition = Integer.parseInt(String.valueOf(int2Ywallposition));
                   if (Ywallposition == RobotYPosition + 1 && Xwallposition == RobotXPosition+1) {
                       X2Wallposition = RobotXPosition - 1;
                   }
               }
                if(X2Wallposition==0) {
                    int temp = RobotYPosition + 1;
                    if (temp <= yMax) {
                        RobotYPosition += 1;
                    } else {
                        break;
                    }
                }
            }
            Robotposition = RobotXPosition+","+RobotYPosition;
        }
        //Osten
        if(walkCommandString.charAt(1)== 'e') {
            StringBuilder intXRobotPosition = new StringBuilder();
            StringBuilder intYRobotPosition = new StringBuilder();
            StringBuilder integerString = new StringBuilder();
            for(int i=4; i<walkCommandString.length()-1; i++){
                integerString.append(walkCommandString.charAt(i));
            }
            int steps = Integer.parseInt(integerString.toString());

            if(Robotposition.charAt(2) != ',') {
                for (int i = 2; i < Robotposition.length(); i++) {
                    intYRobotPosition.append(Robotposition.charAt(i));
                }
            }
            if(Robotposition.charAt(2) == ',') {
                for (int i = 3; i < Robotposition.length(); i++) {
                    intYRobotPosition.append(Robotposition.charAt(i));
                }
            }
            int RobotYPosition = Integer.parseInt(intYRobotPosition.toString());
            if(Robotposition.charAt(1) == ',') {
                intXRobotPosition.append(Robotposition.charAt(0));
            } else {
                intXRobotPosition.append(Robotposition.charAt(0));
                intXRobotPosition.append(Robotposition.charAt(1));
            }

            int RobotXPosition = Integer.parseInt(intXRobotPosition.toString());
            int X2Wallposition = 0;
            for(int j=1; j<=steps; j++) {

               for (int i = 0; i <= wallpositions.length-1; i++) {
                   char int2Xwallposition = wallpositions[i].charAt(0);
                   int Xwallposition = Integer.parseInt(String.valueOf(int2Xwallposition));
                   char int2Ywallposition = wallpositions[i].charAt(2);
                   int Ywallposition = Integer.parseInt(String.valueOf(int2Ywallposition));
                   if (Ywallposition == RobotYPosition && Xwallposition == RobotXPosition + 1 ||
                   Ywallposition == RobotYPosition && Xwallposition == RobotXPosition + 1) {
                       RobotXPosition = Xwallposition-1;
                       X2Wallposition = RobotXPosition;

                   }
               }
                        if(X2Wallposition==0) {
                            int temp = RobotXPosition + 1;
                            if (temp <= xMax) {
                                RobotXPosition += 1;
                            } else {
                                break;

                            }
                        }
                }


            Robotposition = RobotXPosition+","+RobotYPosition;

        }
        //Süden
        if(walkCommandString.charAt(1)== 's') {
            StringBuilder intXRobotPosition = new StringBuilder();
            StringBuilder intYRobotPosition = new StringBuilder();
            StringBuilder integerString = new StringBuilder();
            for(int i=4; i<walkCommandString.length()-1; i++){
                integerString.append(walkCommandString.charAt(i));
            }
            int steps = Integer.parseInt(integerString.toString());
            if(Robotposition.charAt(2) != ',') {
                for (int i = 2; i < Robotposition.length(); i++) {
                    intYRobotPosition.append(Robotposition.charAt(i));
                }
            }
            if(Robotposition.charAt(2) == ',') {
                for (int i = 3; i < Robotposition.length(); i++) {
                    intYRobotPosition.append(Robotposition.charAt(i));
                }
            }
            int RobotYPosition = Integer.parseInt(intYRobotPosition.toString());
            if(Robotposition.charAt(1) == ',') {
                intXRobotPosition.append(Robotposition.charAt(0));
            } else {
                intXRobotPosition.append(Robotposition.charAt(0));
                intXRobotPosition.append(Robotposition.charAt(1));
            }
            int RobotXPosition = Integer.parseInt(intXRobotPosition.toString());

            for(int j=1; j<=steps; j++) {
                int X2Wallposition = 0;
                for (int i = 8; i <= 10; i++) {
                    char int2Xwallposition = wallpositions[i].charAt(0);
                    int Xwallposition = Integer.parseInt(String.valueOf(int2Xwallposition));
                    char int2Ywallposition = wallpositions[i].charAt(2);
                    int Ywallposition = Integer.parseInt(String.valueOf(int2Ywallposition));

                    if (Ywallposition == RobotYPosition + 1 && Xwallposition == RobotXPosition + 1) {
                        X2Wallposition = RobotXPosition - 1;
                        break;
                    }

                }
                if(X2Wallposition==0) {
                    int temp = RobotYPosition - 1;
                    if (temp >= 0) {
                        RobotYPosition -= 1;
                    } else {
                        break;
                    }
                }

            }
            Robotposition = RobotXPosition+","+RobotYPosition;

        }
        //Westen
        if(walkCommandString.charAt(1)== 'w') {

            StringBuilder intXRobotPosition = new StringBuilder();
            StringBuilder intYRobotPosition = new StringBuilder();
            StringBuilder integerString = new StringBuilder();
            for(int i=4; i<walkCommandString.length()-1; i++){
                integerString.append(walkCommandString.charAt(i));
            }
            int steps = Integer.parseInt(integerString.toString());
            if(Robotposition.charAt(2) != ',') {
                for (int i = 2; i < Robotposition.length(); i++) {
                    intYRobotPosition.append(Robotposition.charAt(i));
                }
            }
            if(Robotposition.charAt(2) == ',') {
                for (int i = 3; i < Robotposition.length(); i++) {
                    intYRobotPosition.append(Robotposition.charAt(i));
                }
            }

            int RobotYPosition = Integer.parseInt(intYRobotPosition.toString());
            if(Robotposition.charAt(1) == ',') {
                intXRobotPosition.append(Robotposition.charAt(0));
            } else {
                intXRobotPosition.append(Robotposition.charAt(0));
                intXRobotPosition.append(Robotposition.charAt(1));
            }
            int RobotXPosition = Integer.parseInt(intXRobotPosition.toString());
            int X2Wallposition = 0;
            for(int j=1; j<=steps; j++) {

                for (int i = 0; i <= wallpositions.length - 1; i++) {
                    char int2Xwallposition = wallpositions[i].charAt(0);
                    int Xwallposition = Integer.parseInt(String.valueOf(int2Xwallposition));
                    char int2Ywallposition = wallpositions[i].charAt(2);
                    int Ywallposition = Integer.parseInt(String.valueOf(int2Ywallposition));
                    if (Ywallposition == RobotYPosition && Xwallposition == RobotXPosition - 1 ||
                            Ywallposition == RobotYPosition && Xwallposition == RobotXPosition - 1) {
                        RobotXPosition = Xwallposition;
                        X2Wallposition = RobotXPosition;
                        RobotYPosition = Ywallposition;

                    }
                }
                if (X2Wallposition==0) {
                    int temp = RobotXPosition - 1;
                    if (temp >= 0) {
                        RobotXPosition -= 1;
                    } else {
                        break;
                    }
                }
            }
            Robotposition = RobotXPosition+","+RobotYPosition;

        }
        return "("+Robotposition+")";

    }
}
