package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {
    Grid myMap= new Grid();

    @Override
    public String move(String moveCommandString) {
       int lastX = myMap.getX();
       int lastY = myMap.getY();

        //check format [Direction,number]
        if (checkFormatMoveComandString(moveCommandString)) {
            //Seperate the parts of the command into the Direction and the number of tiles to move
            String[] commands = moveCommandString.replace("[", "").replace("]", "").split(",");

            //turn the Sting into a Direction
            Direction direc = Direction.valueOf(commands[0]);

            int number = Integer.parseInt(commands[1]);

            //now move accordingly:
            switch (direc){
                case no: myMap.setY(myMap.getY()+number); break;
                case ea: myMap.setX(myMap.getX()+number); break;
                case so: myMap.setY(myMap.getY()-number); break;
                case we: myMap.setX(myMap.getX()-number); break;
            }

            myMap.checkForWallsInTheWay(direc,lastX,lastY);
        }
        return myMap.getPosition();
    }

    private boolean checkFormatMoveComandString (String moveCommandString){
        // TODO: checkFormatMoveComandString
        return true;
    }
}
