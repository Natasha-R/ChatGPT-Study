package thkoeln.st.st2praktikum.exercise;
import java.util.Arrays;
public class Exercise0 implements GoAble {
    Integer startUpDown = 2;
    Integer startLeftRight = 0;

    public Integer change(Integer anderungswert,Integer upDownOrLeftRight){
        if(anderungswert == 0){
            anderungswert = upDownOrLeftRight;
        }
        return anderungswert;
    }
    public Integer wallCheck(Integer startUpDown,Integer startLeftRight,Integer newLeftRight,Integer newUpDown){
        Integer anderungswert;
        anderungswert = 0;
        //Section1 Begin
        if(startLeftRight<=2 && startUpDown<=2){
            if(newLeftRight>2){
                anderungswert = 2;
            }
        }
        //Section1 End
        //Section2 Begin
        if(startLeftRight<=4 && startLeftRight >=3 && startUpDown<=2){
            if(newLeftRight<3){
                anderungswert=3;
            }
            if(newLeftRight>4){
                anderungswert=4;
            }
        }
        //Section2 End
        //Section3 Begin
        if(startLeftRight>=5&&startUpDown<=3){
            if(newLeftRight<5){
                anderungswert=5;
            }
        }
        //Section3 End
        //Section4 Begin
        if(startLeftRight>=7&&startUpDown>=5){
            if(newLeftRight<7){
                anderungswert=7;
            }
        }
        //Section4 End
        //Section5 Begin
        if(startLeftRight<=4&&startUpDown==3){
            if(newLeftRight>4){
                anderungswert=4;
            }
        }
        //Section5 End
        //Section6 Begin
        if(startLeftRight<=6&&startUpDown>=5){
            if(newLeftRight>6){
                anderungswert=6;
            }
            if(startLeftRight>=4){
                if(newUpDown<5&&newLeftRight==0){
                    anderungswert=5;
                }
            }
        }
        //Section6 End
        //Section7 Begin
        if(startLeftRight>=4&&startLeftRight<=6&&startUpDown<=4){
            if(newUpDown>4){
                anderungswert=4;
            }
        }
        return anderungswert ;
    }

    public Integer findAmount(String goCommandString){
        if(goCommandString.substring(5,6).equals("]")){
            return Integer.parseInt(goCommandString.substring(4,5));
        }
        if(goCommandString.substring(7,7).equals("]")){
            return Integer.parseInt(goCommandString.substring(4,7));
        }
        else{
            return Integer.parseInt(goCommandString.substring(4,6));
        }
    }

    @Override
    public String go(String goCommandString) {
        Integer newUpDown,newLeftRight,amount,upDown,leftRight,maxUpDown, minUpDown,maxLeftRight,minLeftRight;
        String direction,finish;

        direction = goCommandString.substring(1,3);
        amount = findAmount(goCommandString);
        upDown = startUpDown;
        leftRight = startLeftRight;
        maxLeftRight = 11;
        maxUpDown = 8;
        minLeftRight = 0;
        minUpDown = 0;
        newLeftRight = 0;
        newUpDown = 0;

        if(direction.equals("no")||direction.equals("ea")){
            if(direction.equals("no")){
                newUpDown = amount + upDown;
                newUpDown = change(wallCheck(startUpDown,startLeftRight,newLeftRight,newUpDown),newUpDown);
                if(newUpDown>maxUpDown){
                    newUpDown = maxUpDown;
                }
                if(newUpDown<minUpDown){
                    newUpDown = minUpDown;
                }
                startUpDown = newUpDown;
            }
            else{
                newLeftRight = amount+leftRight;
                newLeftRight = change(wallCheck(startUpDown,startLeftRight,newLeftRight,newUpDown),newLeftRight);
                if(newLeftRight>maxLeftRight){
                    newLeftRight = maxLeftRight;
                }
                if(newLeftRight<minLeftRight){
                    newLeftRight = minLeftRight;
                }
                startLeftRight = newLeftRight;
            }
        }
        if(direction.equals("so")||direction.equals("we")){
            if(direction.equals("so")){
                newUpDown = upDown - amount;
                if(newUpDown<0){
                    newUpDown = 0;
                }
                newUpDown = change(wallCheck(startUpDown,startLeftRight,newLeftRight,newUpDown),newUpDown);
                if(newUpDown<minUpDown){
                    newUpDown = minUpDown;
                }
                startUpDown = newUpDown;
            }
            else{
                newLeftRight = leftRight - amount;
                if(newLeftRight<0){
                    newLeftRight = 0;
                }
                newLeftRight = change(wallCheck(startUpDown,startLeftRight,newLeftRight,newUpDown),newLeftRight);
                if(newLeftRight<minLeftRight){
                    newLeftRight = minLeftRight;
                }
                startLeftRight = newLeftRight;
            }
        }
        finish = "("+startLeftRight+","+startUpDown+")";
        return finish;
    }
}
