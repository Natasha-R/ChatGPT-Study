package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    int max_x = 11;
    int max_y = 8;
    Coordinate currentPos = new Coordinate(8,3, "");
    Coordinate[] blockedFields =
                            {new Coordinate(4,1, "we"), new Coordinate(4,2, "we"), new Coordinate(4,3, "we"),
                            new Coordinate(4,4, "we"), new Coordinate(4,5, "we"), new Coordinate(4,6, "we"),
                                    new Coordinate(3,1, "ea"), new Coordinate(3,2, "ea"), new Coordinate(3,3, "ea"),
                                    new Coordinate(3,4, "ea"), new Coordinate(3,5, "ea"), new Coordinate(3,6, "ea"),
                            new Coordinate(8,1, "no"), new Coordinate(7,1, "no"), new Coordinate(6,1, "no"),
                            new Coordinate(5,2, "ea"), new Coordinate(5,3, "ea"), new Coordinate(5,4, "ea"),
                            new Coordinate(6,5, "so"), new Coordinate(7,5, "so"), new Coordinate(8,5, "so"),
                            new Coordinate(6,2, "so"), new Coordinate(6,2, "we"), new Coordinate(7,2, "so"), new Coordinate(8,2, "so"),
                            new Coordinate(6,4, "no"), new Coordinate(6,4, "we"), new Coordinate(7,4, "no"), new Coordinate(8,4, "no"),
                            new Coordinate(6,3,"we")};


    @Override
    public String moveTo(String moveCommandString) {

        char[] moveString = moveCommandString.toCharArray();
        String direction = "" + moveString[1] + moveString[2];
        int amount;
        String temp = "";
        boolean onWall = false;

        for(int i = 4; i<=moveString.length-2; i++){
            temp += moveString[i];
        }

        amount = Integer.parseInt(temp);

        System.out.println(direction);
        System.out.println(amount);

        switch(direction){
            case "no":
                onWall = false;

                for(int i = amount; i>0; i--){
                    if(currentPos.y+1<= max_y){
                        for(int j = 0; j<blockedFields.length; j++){

                            if(currentPos.x == blockedFields[j].x && currentPos.y == blockedFields[j].y && "no" == blockedFields[j].blockedDir){
                                onWall = true;
                            }
                        }
                        if(onWall == false){
                            currentPos.y += 1;
                            System.out.println("no");
                        }
                    }
                }
                break;
            case "ea":
                onWall = false;

                for(int i = amount; i>0; i--){
                    if(currentPos.x+1<= max_x){
                        for(int j = 0; j<blockedFields.length; j++){
                            if(currentPos.x == blockedFields[j].x && currentPos.y == blockedFields[j].y && "ea" == blockedFields[j].blockedDir){
                                onWall = true;
                            }
                        }
                        if(onWall == false){
                            currentPos.x += 1;
                            System.out.println("ea");
                        }
                    }
                }
                break;
            case "so":
                onWall = false;

                for(int i = amount; i>0; i--){
                    if(currentPos.y-1>=0){
                        for(int j = 0; j<blockedFields.length; j++){
                            if(currentPos.x == blockedFields[j].x && currentPos.y == blockedFields[j].y && "so" == blockedFields[j].blockedDir){
                                onWall = true;
                            }
                        }
                        if(onWall == false){
                            currentPos.y -= 1;
                            System.out.println("so");
                        }
                    }
                }
                break;
            case "we":
                onWall = false;

                for(int i = amount; i>0; i--){
                    if(currentPos.x-1>=0){
                        for(int j = 0; j<blockedFields.length; j++){
                            if(currentPos.x == blockedFields[j].x && currentPos.y == blockedFields[j].y && "we" == blockedFields[j].blockedDir){
                                onWall = true;
                            }
                        }
                        if(onWall == false){
                            currentPos.x -= 1;
                            System.out.println("we");
                        }
                    }
                }
                break;
        }
        String result = "(" + currentPos.x + "," + currentPos.y + ")";

        return result;
    }

}

