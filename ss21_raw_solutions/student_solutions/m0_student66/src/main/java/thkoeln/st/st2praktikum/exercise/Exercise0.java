package thkoeln.st.st2praktikum.exercise;


public class Exercise0 implements GoAble {

    private Room room;
    private Robot robot;

    public Exercise0(){
        room = new Room(12,9);
        room.addWall(new Wall(new Coordinate(4,1), new Coordinate(4,7)));
        room.addWall(new Wall(new Coordinate(6,2), new Coordinate(6,5)));
        room.addWall(new Wall(new Coordinate(6,2), new Coordinate(9,2)));
        room.addWall(new Wall(new Coordinate(6,5), new Coordinate(9,5)));
        robot = new Robot(new Coordinate(8,3));
    }


    @Override
    public String go(String goCommandString) {
        Command command = new Command(goCommandString);
        for(int i=0; i<command.getSteps(); i++) {
            Coordinate newCoordinate = new Coordinate(robot.getPosition().getX(), robot.getPosition().getY());
            switch (command.getDirection()) {
                case ("no"):
                    newCoordinate.setY(robot.getPosition().getY() + 1);
                    break;
                case ("so"):
                    newCoordinate.setY(newCoordinate.getY() - 1);
                    break;
                case ("ea"):
                    newCoordinate.setX(robot.getPosition().getX() + 1);
                    break;
                case ("we"):
                    newCoordinate.setX(robot.getPosition().getX() - 1);
                    break;
            }
            if (!robotHitsWall(command.getDirection(), newCoordinate) && robotStaysInRoom(newCoordinate)) {
                robot.move(command.getDirection());
            } else {
                break;
            }
        }
        return robot.getPosition().toString();
    }

    private boolean robotHitsWall(String direction, Coordinate newRobotPosition){
        switch (direction) {
            case("no"):
                for(Wall wall : room.getHorizontallyWalls()){
                    if(wall.getWholeWall().contains(newRobotPosition) && !wall.getTo().equals(newRobotPosition)){
                        return true;
                    }
                }
                break;
            case("so"):
                for(Wall wall : room.getHorizontallyWalls()){
                    if(wall.getWholeWall().contains(robot.getPosition()) && !wall.getTo().equals(robot.getPosition())){
                        return true;
                    }
                }
                break;
            case("ea"):
                for(Wall wall : room.getVerticallyWalls()){
                    if(wall.getWholeWall().contains(newRobotPosition) && !wall.getTo().equals(newRobotPosition)){
                        return true;
                    }
                }
                break;
            case("we"):
                for(Wall wall : room.getVerticallyWalls()){
                    if(wall.getWholeWall().contains(robot.getPosition()) && !wall.getTo().equals(robot.getPosition())){
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private boolean robotStaysInRoom(Coordinate newCoordinate){
        return newCoordinate.getX() >= 0 && newCoordinate.getX() < room.getLength() &&
                newCoordinate.getY() >= 0 && newCoordinate.getY() < room.getWidth();
    }
}
