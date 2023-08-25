package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class RobotMovement {



    public Boolean executeCommand(UUID tidyUpRobotID , Command command , TidyUpRobotRepository tidyUpRobotRepository , RoomRepository roomRepository , ConnectionRepository connectionRepository){

        switch (command.getCommandType()){

            case NORTH: return goNorth(tidyUpRobotID , command , tidyUpRobotRepository , roomRepository);

            case EAST:  return goEast (tidyUpRobotID , command , tidyUpRobotRepository , roomRepository);

            case WEST:  return goWest (tidyUpRobotID , command , tidyUpRobotRepository , roomRepository);

            case SOUTH: return goSouth(tidyUpRobotID , command , tidyUpRobotRepository , roomRepository);

            case ENTER:  return enter  (tidyUpRobotID , command , tidyUpRobotRepository);

            case TRANSPORT: return transport( tidyUpRobotID , command , connectionRepository , tidyUpRobotRepository);

        }
        return true;
    }

    public Boolean goNorth ( UUID tidyUpRobotID , Command command , TidyUpRobotRepository tidyUpRobotRepository , RoomRepository roomRepository){

        for ( int  i = 0 ; i < command.getNumberOfSteps() ; i++ ){
            tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setCoordinate(new Coordinate(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getX() , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getY() +1));
            if (!moveOutOfBoundaries( tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate(), roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID())) ||
                    !moveVertikalIntoBarrier(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate() ,roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID())) ||
                        !hitTidyUpRobot( tidyUpRobotID , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate() , roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID()) , tidyUpRobotRepository)){
                tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setCoordinate(new Coordinate(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getX(), tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getY() -1 ));
                break;
            }
        }

        return true;
    }

    public Boolean goEast  (  UUID tidyUpRobotID , Command command , TidyUpRobotRepository tidyUpRobotRepository , RoomRepository roomRepository ){

        for ( int  i = 0 ; i < command.getNumberOfSteps() ; i++ ){
            tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setCoordinate(new Coordinate(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getX() + 1 , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getY() ));
            if (!moveOutOfBoundaries( tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate(), roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID())) ||
                    !moveHorizontalIntoBarrier(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate(), roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID())) ||
                    !hitTidyUpRobot( tidyUpRobotID , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate() , roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID()) , tidyUpRobotRepository)){
                tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setCoordinate(new Coordinate(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getX() -1 , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getY() ));
                break;
            }
        }

        return true;
    }

    public Boolean goSouth (  UUID tidyUpRobotID , Command command , TidyUpRobotRepository tidyUpRobotRepository , RoomRepository roomRepository){

        for ( int  i = 0 ; i < command.getNumberOfSteps() ; i++ ){
            if (tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getY() == 0){
                return true;
            }
            tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setCoordinate(new Coordinate(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getX()  , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getY() - 1 ));
            if (!moveOutOfBoundaries( tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate(), roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID())) ||
                    !moveVertikalIntoBarrier(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate(), roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID())) ||
                        !hitTidyUpRobot( tidyUpRobotID , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate() , roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID()) , tidyUpRobotRepository)){
                tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setCoordinate(new Coordinate(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getX()  , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getY() + 1 ));
                break;
            }
        }

        return true;
    }

    public Boolean goWest  (  UUID tidyUpRobotID , Command command , TidyUpRobotRepository tidyUpRobotRepository , RoomRepository roomRepository){

        for ( int  i = 0 ; i < command.getNumberOfSteps() ; i++ ){
            if (tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getX() == 0){
                return true;
            }
            tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setCoordinate(new Coordinate(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getX()  - 1 , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getY()  ));
            if (!moveOutOfBoundaries( tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate(), roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID())) ||
                    !moveHorizontalIntoBarrier(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate(), roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID())) ||
                        !hitTidyUpRobot( tidyUpRobotID , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate() , roomRepository.getRoom(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID()) , tidyUpRobotRepository)){
                tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setCoordinate(new Coordinate(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getX()  + 1 , tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate().getY()  ));
                break;
            }
        }

        return true;
    }

    public Boolean transport ( UUID tidyUpRobotID , Command command ,  ConnectionRepository connectionRepository , TidyUpRobotRepository tidyUpRobotRepository) {

        for (int i = 0; i < connectionRepository.getConnections().size(); i++) {
            if ( connectionRepository.getConnections().get(i).getSourceRoomID().equals(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getRoomID()) &&
                    connectionRepository.getConnections().get(i).getSourceCoordinates().equals(tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).getCoordinate())){
                for ( int  j = 0 ; j < tidyUpRobotRepository.getTidyUpRobots().size() ; j++ ){
                    if (tidyUpRobotRepository.getTidyUpRobots().get(j).getCoordinate() == ( connectionRepository.getConnections().get(i).getDestinationCoordinates()) &&
                            tidyUpRobotRepository.getTidyUpRobots().get(j).getRoomID().equals(command.getGridId())) {
                        return false;
                    }
                }
                tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setCoordinate(connectionRepository.getConnections().get(i).getDestinationCoordinates());
                tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setRoomID(connectionRepository.getConnections().get(i).getDestinationRoomID());
                return true;
            }
        }
        return false;
    }


    public Boolean enter ( UUID tidyUpRobotID , Command command , TidyUpRobotRepository tidyUpRobotRepository){


        for (int i = 0; i < tidyUpRobotRepository.getTidyUpRobots().size() ; i++) {
            if (tidyUpRobotRepository.getTidyUpRobots().get(i).getRoomID() != null) {
                if (tidyUpRobotRepository.getTidyUpRobots().get(i).getRoomID().equals(command.getGridId()) &&
                        tidyUpRobotRepository.getTidyUpRobots().get(i).getCoordinate().getX() == 0 && tidyUpRobotRepository.getTidyUpRobots().get(i).getCoordinate().getY() == 0) {
                    return false;
                }
            }
        }
        tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setRoomID(command.getGridId());
        tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotID).setCoordinate(new Coordinate(0,0));
        return true;
    }

    public Boolean moveOutOfBoundaries ( Coordinate coordinate , Room room ){

        if (coordinate.getX() < 0 || coordinate.getX() > room.getWidth()){
            return false;
        }

        if (coordinate.getY() < 0 || coordinate.getY() > room.getHeight()){
            return false;
        }

        return true;
    }

    public Boolean hitTidyUpRobot (UUID tidyUpRobotID , Coordinate coordinate , Room room , TidyUpRobotRepository tidyUpRobotRepository) {

        for (int i = 0; i < tidyUpRobotRepository.getTidyUpRobots().size(); i++) {
            if (tidyUpRobotRepository.getTidyUpRobots().get(i).getRoomID() != null) {
                if (tidyUpRobotID != tidyUpRobotRepository.getTidyUpRobots().get(i).getTidyUpRobotID() &&
                        coordinate.equals(tidyUpRobotRepository.getTidyUpRobots().get(i).getCoordinate()) && tidyUpRobotRepository.getTidyUpRobots().get(i).getRoomID().equals(room.getRoomID())) {
                    return false;
                }
            }
        }
        return true;
    }

    public Boolean moveVertikalIntoBarrier ( Coordinate coordinate , Room room ){

        for (int i = 0; i < room.getHorizontalBarriers().size() ; i++){
            if ( coordinate.getY().equals(room.getHorizontalBarriers().get(i).getStart().getY()) && coordinate.getX() - room.getHorizontalBarriers().get(i).getStart().getX() + room.getHorizontalBarriers().get(i).getEnd().getX() - coordinate.getX()  == room.getHorizontalBarriers().get(i).getEnd().getX() - room.getHorizontalBarriers().get(i).getStart().getX() ){
               if(!coordinate.getX().equals(room.getHorizontalBarriers().get(i).getStart().getX()) && !coordinate.getX().equals(room.getHorizontalBarriers().get(i).getEnd().getX())){
                   return false;
               }
            }
        }
        return true;
    }

    public Boolean moveHorizontalIntoBarrier ( Coordinate coordinate , Room room ){

        for (int i = 0; i < room.getVertikalBarriers().size() ; i++ ){
            if ( coordinate.getX().equals(room.getVertikalBarriers().get(i).getStart().getX()) && room.getVertikalBarriers().get(i).getStart().getY() - coordinate.getY()   + coordinate.getY() - room.getVertikalBarriers().get(i).getEnd().getY() == room.getVertikalBarriers().get(i).getEnd().getY() - room.getVertikalBarriers().get(i).getStart().getY()){
                if(!coordinate.getY().equals(room.getHorizontalBarriers().get(i).getStart().getY()) && !coordinate.getY().equals(room.getHorizontalBarriers().get(i).getEnd().getY())) {
                    return false;
                }
            }
        }
        return true;
    }
}
