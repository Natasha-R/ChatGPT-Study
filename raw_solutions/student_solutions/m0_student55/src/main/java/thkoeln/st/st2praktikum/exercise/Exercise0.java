/*Atidy-uprobotisusedtotidyuparoom-collectgarbage,sortstuff,etc.
Inmilestone0,youhavethetasktosteersuchatidy-uprobotacrossaroomwithobstacles.
Theroomissquare-shaped.Inthefollowingimage,youseeahowtheroomismodelledasa
systemofcells.*/

package thkoeln.st.st2praktikum.exercise;


        public class Exercise0 implements GoAble{

        Integer x=5;
        Integer y=3;

@Override
public String goTo(String goCommandString){


        int steps=Integer.parseInt(String.valueOf(goCommandString.charAt(4)));


        if(goCommandString.charAt(1)=='n'){


        if(x>=3&&x<=8){
            if(y<3){
                if(y+steps>=2){
                y=2;
                return"("+x+","+y+")";
                }
            }
        }

        if(x>0&&x<=5){
            if(y<6){
                if(y+steps>=5){
                    y=5;
                    return"("+x+","+y+")";
                }
            }
        }

            if (x == 4 && y == 0) {
                y = 0;
                return"("+x+","+y+")";
            }

        if(y+steps>=7){
            y=7;
            return"("+x+","+y+")";
        }



        y+=steps;
        return"("+x+","+y+")";

        }





        if(goCommandString.charAt(1)=='e'){

            if(y==1||y==2){
                if(x<4){
                    if(x+steps>3){
                        x=3;
                        return"("+x+","+y+")";
                    }
                }
            }

            if(y>=2&&y<=7){
                if(x<6){
                    if(x+steps>5){
                        x=5;
                        return"("+x+","+y+")";
                    }
                }
            }

            if(x>11){
                x=11;
                return"("+x+","+y+")";
            }
            if(x>0&&x<5){
                if(y==6){
                    x=0;
                    return"("+x+","+y+")";
                }
            }

        x+=steps;
        return"("+x+","+y+")";

        }






        if(goCommandString.charAt(1)=='s'){

            if(x>=1&&x<=5){
                if(y>=6){
                    if(y-steps<=5){
                        y=6;
                        return"("+x+","+y+")";
                    }
                }
            }

            if(x>=3&&y<=8){
                if(y>=3){
                    if(y-steps<3){
                        y=3;
                        return"("+x+","+y+")";
                    }
                }
             }

            if(y-steps<0){
                y=0;
                return"("+x+","+y+")";
            }

        y-=steps;
        return"("+x+","+y+")";
        }




        if(goCommandString.charAt(1)=='w'){

            if(y>=2&&y<=7){
                if(x>5){
                    if(x-steps<=6){
                        y=6;
                        return"("+x+","+y+")";
                    }
                }
            }

        if(y==1||y==2){
            if(x>3){
                if(x-steps<=4){
                    x=4;
                    return"("+x+","+y+")";
                }
            }
        }

        if(x-steps<=0){
            x=0;
            return"("+x+","+y+")";
        }
                x-=steps;
                return"("+x+","+y+")";
            }

            return"("+x+","+y+")";
            }
        }

