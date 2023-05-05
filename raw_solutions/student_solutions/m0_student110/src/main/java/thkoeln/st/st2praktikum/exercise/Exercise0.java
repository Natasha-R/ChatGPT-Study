package thkoeln.st.st2praktikum.exercise;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;

public class Exercise0 implements GoAble {
    public Integer x = 0;
    public Integer y=2;






    @Override
    public String goTo(String goCommandString) {


                String wandpunkt1 = "3,0";
                String wandpunkt2 = "3,1";
                String wandpunkt3 = "3,2";
                String wandpunkt4 = "5,0";
                String wandpunkt5 = "5,1";
                String wandpunkt6 = "5,2";
                String wandpunkt7= "5,3";
                String wandpunkt8 = "7,9";
                String wandpunkt9 = "7,8";
                String wandpunkt10 = "7,7";
                String wandpunkt11 = "7,6";
                String wandpunkt12 = "5,5";
                String wandpunkt13 = "6,5";



        ArrayList<String> wandpunkte = new ArrayList<>();
        wandpunkte.add(wandpunkt1);
        wandpunkte.add(wandpunkt2);
        wandpunkte.add(wandpunkt3);
        wandpunkte.add(wandpunkt4);
        wandpunkte.add(wandpunkt5);
        wandpunkte.add(wandpunkt6);
        wandpunkte.add(wandpunkt7);
        wandpunkte.add(wandpunkt8);
        wandpunkte.add(wandpunkt9);
        wandpunkte.add(wandpunkt10);
        wandpunkte.add(wandpunkt11);
        wandpunkte.add(wandpunkt12);
        wandpunkte.add(wandpunkt13);



        int moveAmmount;
        Integer c;
        String startString = String.format ("[%d,%d]",x,y);
        String finalString;

        moveAmmount =Integer.parseInt(goCommandString.substring(4,5));





        if(goCommandString.contains("no")){


            for (int i = 0; i< wandpunkte.size(); i++){

                if (Integer.parseInt(wandpunkte.get(i).substring(2,3))>y && Integer.parseInt(wandpunkte.get(i).substring(2,3))<= (y+moveAmmount) && Integer.parseInt(wandpunkte.get(i).substring(0,1)) == x ){

                    y = Integer.parseInt(wandpunkte.get(i).substring(2,3))-1;
                    finalString = String.format("(%d,%d)",x,y);
                    return finalString;


                }

            }

            y = y + moveAmmount;

            if (y<0) y=0;
            if (y>8)y=8;
            finalString = String.format("(%d,%d)",x,y);
            return finalString;
        }
        else if (goCommandString.contains("so")){

            for (int b = 0; b< wandpunkte.size(); b++){

                if (Integer.parseInt(wandpunkte.get(b).substring(2,3))<y && Integer.parseInt(wandpunkte.get(b).substring(2,3))>= (y-moveAmmount) && Integer.parseInt(wandpunkte.get(b).substring(0,1)) == x ){

                    y =Integer.parseInt(wandpunkte.get(b).substring(2,3))+1;
                    finalString = String.format("(%d,%d)",x,y);
                    return finalString;

                }
            }

                y = y - moveAmmount;
                if (y<0) y=0;
                if (y>8)y=8;

            finalString = String.format("(%d,%d)",x,y);
            return finalString;

        }
        else if (goCommandString.contains("ea")){



            for (int a = 0; a< wandpunkte.size(); a++){

                if (Integer.parseInt(wandpunkte.get(a).substring(0,1))>x && Integer.parseInt(wandpunkte.get(a).substring(0,1))<= (x+moveAmmount) && Integer.parseInt(wandpunkte.get(a).substring(2,3)) == y ){

                    x = Integer.parseInt(wandpunkte.get(a).substring(0,1))-1;
                    finalString = String.format("(%d,%d)",x,y);
                    return finalString;


                }

            }


                 x = x+moveAmmount;
                if (x<0) x=0;
               if (x>12)x=12;

            finalString = String.format("(%d,%d)",x,y);
            return finalString;
        }
        else if (goCommandString.contains("we")){



            for (int i = 0; i< wandpunkte.size(); i++){
                    ///&& Integer.parseInt(wandpunkte.get(i).substring(2,3)) !=Integer.parseInt(wandpunkte.get(i+1).substring(2,3))  && Integer.parseInt(wandpunkte.get(i+1).substring(2,3)) !=Integer.parseInt(wandpunkte.get(i+2).substring(2,3))  && Integer.parseInt(wandpunkte.get(i+2).substring(2,3)) !=Integer.parseInt(wandpunkte.get(i+3).substring(2,3))

                if (Integer.parseInt(wandpunkte.get(i).substring(0,1))<x && Integer.parseInt(wandpunkte.get(i).substring(0,1))>= (x-moveAmmount) && Integer.parseInt(wandpunkte.get(i).substring(2,3)) == y ){

                    x = Integer.parseInt(wandpunkte.get(i).substring(0,1));
                    finalString = String.format("(%d,%d)",x,y);
                    return finalString;


                }

            }

            x = x - moveAmmount;
            if (x<0) x=0;
            if (x>12)x=12;
            finalString = String.format("(%d,%d)",x,y);
            return finalString;


        }

     else return null;
    }





}
