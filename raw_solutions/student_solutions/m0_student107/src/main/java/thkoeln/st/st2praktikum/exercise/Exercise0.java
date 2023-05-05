package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Basic Object ✔
 * TODO Command Parser ✔
 * TODO Movement Detection ✔
 * TODO Apply Movement ✔
 * TODO Collision detection ✔
 *
 * TODO OPTIONAL STUFF
 *
**/

public class Exercise0 implements Moveable {


    private Coordinate robotcoordinate;
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private int areax;
    private int areay;



    public Exercise0() {
        //START POINT
        this.robotcoordinate = new Coordinate(7,7);
        //DIMENSION | Obstacle
        this.areax = 11;
        this.areay = 8;

        this.genobstacle(new Coordinate(2,6) , new Command("ea", 5));
        this.genobstacle(new Coordinate(2,1) , new Command("no", 5));
        this.genobstacle(new Coordinate(2,1) , new Command("ea", 8));
        this.genobstacle(new Coordinate(10,1) , new Command("no", 7));
    }

    public Exercise0(int x , int y){
    this();
    this.robotcoordinate = new Coordinate(x,y);
    }

    public void genobstacle(Coordinate cord){
        this.obstacles.add(new Obstacle(new Command("static" , 0 ) , cord));

    }

    public void genobstacle(Coordinate cordstart , Command command ){
        Coordinate tmpCoordinate = new Coordinate(cordstart.getX(), cordstart.getY());

            this.obstacles.add(new Obstacle(command , cordstart));
        }


    //COMMAND : [DIRECTION,DISTANCE]
    @Override
    public String move(String moveCommandString) {

        Command command = this.commandparser(moveCommandString);
        boolean breakpoint = false;
        Coordinate tmpCoordinate = new Coordinate(robotcoordinate.getX(), robotcoordinate.getY());
        Coordinate saved = new Coordinate(tmpCoordinate.getX(), tmpCoordinate.getY());;
        String commanddir = command.getDirection();
        for(int i = command.getDistance(); i>= 0 & !breakpoint ; i-- ){
            saved.setX(tmpCoordinate.getX());
            saved.setY(tmpCoordinate.getY());
            switch (command.getDirection()){
                case "no":
                    tmpCoordinate.setY(tmpCoordinate.getY() + 1);
                    break;
                case "ea":
                    tmpCoordinate.setX(tmpCoordinate.getX() + 1);
                    break;
                case "so":
                    tmpCoordinate.setY(tmpCoordinate.getY() - 1);
                    break;
                case "we":
                    tmpCoordinate.setX(tmpCoordinate.getX() - 1);
                    break;
            }
            //Check if it touches border | only when coordinate is in the area
            if(tmpCoordinate.getX() >= 0 & tmpCoordinate.getX() < this.areax & tmpCoordinate.getY() >= 0 & tmpCoordinate.getY() < this.areay){
                //Check if Coordinate is not colliding with any obstacle

                for (Obstacle c: this.obstacles
                     ) {
                    //horizontal border | moving down (south) |
                    if((c.command.getDirection().equals("we") || c.command.getDirection().equals("ea")) && (commanddir.equals("so") || commanddir.equals("no")) ){
//                        System.out.println("    Checking WE | EA");
                        for(int j = c.command.getDistance(); j >= 0 ; j--){
                            //west movement
                            if(c.command.getDirection().equals("we")){
                                //not startpoint
//                                System.out.println("        Checking WE");
                                if(j != 0){
                                    if(c.anchor.getX() - j == tmpCoordinate.getX() && ((c.anchor.getY() == tmpCoordinate.getY() && commanddir.equals("no")) || (c.anchor.getY()-1 == tmpCoordinate.getY() && commanddir.equals("so"))) ){
//                                        System.out.println("            Collision");
                                        breakpoint = true;
                                    }
                                }
                            }
                            //east movement
                            else if(c.command.getDirection().equals("ea")){
                                //not endpoint
//                                System.out.println("        Checking EA");
                                if(j != c.command.getDistance()){
                                    if(c.anchor.getX() + j == tmpCoordinate.getX() && ((c.anchor.getY() == tmpCoordinate.getY() && commanddir.equals("no")) || (c.anchor.getY()-1 == tmpCoordinate.getY() && commanddir.equals("so")))){
//                                        System.out.println("            Collision");
                                        breakpoint = true;
                                    }
                                }
                            }
                        }
                    }else if((c.command.getDirection().equals("no") || c.command.getDirection().equals("so")) && (commanddir.equals("ea") || commanddir.equals("we")) ){

//                        System.out.println("    Checking NO | SO");
                        for(int j = c.command.getDistance(); j >= 0 ; j--){

                            //no movement
                            if(c.command.getDirection().equals("so")){
                                //not startpoint
//                                System.out.println("        Checking SO");
                                if(j != 0){
                                    if(c.anchor.getY() - j == tmpCoordinate.getY() && ((c.anchor.getX()  == tmpCoordinate.getX() && commanddir.equals("ea")) || (c.anchor.getX()-1 == tmpCoordinate.getX() && commanddir.equals("we"))) ){
                                        breakpoint = true;
                                    }
                                }
                            }
                            //so movement
                            else if(c.command.getDirection().equals("no")){
                                //not endpoint
//                                System.out.println("        Checking NO");
                                if(j != c.command.getDistance()){
                                    if(c.anchor.getY() + j == tmpCoordinate.getY() && ((c.anchor.getX() == tmpCoordinate.getX() && commanddir.equals("ea")) || (c.anchor.getX()-1 == tmpCoordinate.getX() && commanddir.equals("we")))){
                                        breakpoint = true;
                                    }
                                }
                            }
                        }
                    }

                }
            }else {
//                System.out.println("    border collision");
                breakpoint = true;

            }

        }

    this.robotcoordinate.setX(saved.getX());
    this.robotcoordinate.setY(saved.getY());
        return "(" + this.robotcoordinate.getX() + "," +this.robotcoordinate.getY() + ")"
;
        //throw new UnsupportedOperationException();


    }

    private Command commandparser(String commandstring) {

        String direction;
        int distance;

        String[] values= commandstring.replace("[","").replace("]","").split(",");
        System.out.println(values[0]);
        System.out.println(values[1]);
        direction = values[0];
        distance = Integer.parseInt(values[1]);

        return new Command(direction,distance);
    }


    private class Obstacle{

        private Command command;
        private Coordinate anchor;

        Obstacle(){
            this.command = null;
            this.command = null;
        }

        Obstacle(Command command , Coordinate anchor){
            this.command = command;
            this.anchor = anchor;
        }

        public Coordinate endcord(){
            switch (this.command.getDirection()){
                case "static":
                    return this.anchor;
                case "no":
                    return new Coordinate(this.anchor.getX(),+this.anchor.getY()+this.command.distance);
                case "ea":
                    return new Coordinate(this.anchor.getX()+this.command.distance,this.anchor.getY());

                case "we":
                    return new Coordinate(this.anchor.getX()-this.command.distance,this.anchor.getY());

                case "so":
                    return new Coordinate(this.anchor.getX(),+this.anchor.getY()-this.command.distance);
                default:
                    return null;
            }
        }

        public Command getCommand() {
            return command;
        }

        public Coordinate getAnchor() {
            return anchor;
        }

        public void setAnchor(Coordinate anchor) {
            this.anchor = anchor;
        }

        public void setCommand(Command command) {
            this.command = command;
        }
    }





    private class Command {
        private String direction;
        private int distance;

        public int getDistance() {
            return distance;
        }

        public String getDirection() {
            return direction;
        }

        public Command(){

        }
        public Command(String direction , int distance){
            this.direction = direction;
            this.distance = distance;
        }
    }



    private class Line {

        private Coordinate start;
        private Coordinate end;
        public Line(){

        }

        public Line(Coordinate start , Coordinate end){
            this.start = start;
            this.end = end;
        }

        public Coordinate getStart() {
            return this.start;
        }

        public Coordinate getEnd() {
            return this.end;
        }

        @Override

        public String toString() {
            return this.start.toString() +"-"+this.end.toString();
        }



    }


    private class Coordinate {
        private int x;
        private int y;

        public Coordinate(){
            this.x = 0;
            this.y = 0;
        }

        public Coordinate(int x , int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }
        public int getY() {
            return this.y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }


    public void test(){

    }

}
