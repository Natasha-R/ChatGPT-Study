package thkoeln.st.st2praktikum.exercise;

import java.util.List;
import java.util.UUID;

public class RobotMovement {


    public Boolean executeCommand(UUID tidyUpRobotID, Command command, TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository, ConnectionRepository connectionRepository) {

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
            tidyUpRobotRepository.findById(tidyUpRobotID).get().setCoordinate(new Coordinate(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getX() , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getY() +1));

            if (!moveOutOfBoundaries( tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate(), roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get())||
                    !moveVertikalIntoBarrier(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate() ,roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get()) ||
                    !hitTidyUpRobot( tidyUpRobotID , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate() , roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get() , tidyUpRobotRepository)){

                tidyUpRobotRepository.findById(tidyUpRobotID).get().setCoordinate(new Coordinate(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getX(), tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getY() -1 ));
                break;
            }
        }

        return true;
    }

    public Boolean goEast  (  UUID tidyUpRobotID , Command command , TidyUpRobotRepository tidyUpRobotRepository , RoomRepository roomRepository ){

        for ( int  i = 0 ; i < command.getNumberOfSteps() ; i++ ){
            tidyUpRobotRepository.findById(tidyUpRobotID).get().setCoordinate(new Coordinate(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getX() + 1 , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getY() ));

            if (!moveOutOfBoundaries( tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate(), roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get()) ||
                    !moveHorizontalIntoBarrier(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate(), roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get()) ||
                    !hitTidyUpRobot( tidyUpRobotID , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate() , roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get() , tidyUpRobotRepository)){

                tidyUpRobotRepository.findById(tidyUpRobotID).get().setCoordinate(new Coordinate(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getX() -1 , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getY() ));
                break;
            }
        }

        return true;
    }

    public Boolean goSouth (  UUID tidyUpRobotID , Command command , TidyUpRobotRepository tidyUpRobotRepository , RoomRepository roomRepository){

        for ( int  i = 0 ; i < command.getNumberOfSteps() ; i++ ){
            if (tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getY() == 0){
                return true;
            }
            tidyUpRobotRepository.findById(tidyUpRobotID).get().setCoordinate(new Coordinate(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getX()  , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getY() - 1 ));

            if (!moveOutOfBoundaries( tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate(), roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get()) ||
                    !moveVertikalIntoBarrier(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate(), roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get()) ||
                    !hitTidyUpRobot( tidyUpRobotID , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate() , roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get() , tidyUpRobotRepository)){

                tidyUpRobotRepository.findById(tidyUpRobotID).get().setCoordinate(new Coordinate(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getX()  , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getY() + 1 ));
                break;
            }
        }

        return true;
    }

    public Boolean goWest  (  UUID tidyUpRobotID , Command command , TidyUpRobotRepository tidyUpRobotRepository , RoomRepository roomRepository){

        for ( int  i = 0 ; i < command.getNumberOfSteps() ; i++ ){
            if (tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getX() == 0){
                return true;
            }
            tidyUpRobotRepository.findById(tidyUpRobotID).get().setCoordinate(new Coordinate(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getX()  - 1 , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getY()  ));

            if (!moveOutOfBoundaries( tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate(), roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get()) ||
                    !moveHorizontalIntoBarrier(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate(), roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get()) ||
                    !hitTidyUpRobot( tidyUpRobotID , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate() , roomRepository.findById(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()).get() , tidyUpRobotRepository)){

                tidyUpRobotRepository.findById(tidyUpRobotID).get().setCoordinate(new Coordinate(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getX()  + 1 , tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate().getY()  ));
                break;
            }
        }

        return true;
    }

    public Boolean transport (UUID tidyUpRobotID , Command command , ConnectionRepository connectionRepository , TidyUpRobotRepository tidyUpRobotRepository) {


        List<Connection> connections = (List<Connection>) connectionRepository.findAll();
        List<TidyUpRobot> tidyUpRobots = (List<TidyUpRobot>) tidyUpRobotRepository.findAll();

        for (int i = 0; i < connections.size(); i++) {
            if ( connections.get(i).getSourceRoomID().equals(tidyUpRobotRepository.findById(tidyUpRobotID).get().getRoomId()) &&
                    connections.get(i).getSourceCoordinates().equals(tidyUpRobotRepository.findById(tidyUpRobotID).get().getCoordinate())){
                for ( int  j = 0 ; j < tidyUpRobots.size() ; j++ ){
                    if (tidyUpRobots.get(j).getCoordinate() == ( connections.get(i).getDestinationCoordinates()) &&
                            tidyUpRobots.get(j).getRoomId().equals(command.getGridId())) {
                        return false;
                    }
                }
                tidyUpRobotRepository.findById(tidyUpRobotID).get().setCoordinate(connections.get(i).getDestinationCoordinates());
                tidyUpRobotRepository.findById(tidyUpRobotID).get().setRoomID(connections.get(i).getDestinationRoomID());
                return true;
            }
        }
        return false;

    }


    public Boolean enter ( UUID tidyUpRobotID , Command command , TidyUpRobotRepository tidyUpRobotRepository){

        List<TidyUpRobot> tidyUpRobots = (List<TidyUpRobot>) tidyUpRobotRepository.findAll();

        for (int i = 0; i < tidyUpRobots.size() ; i++) {
            if (tidyUpRobots.get(i).getRoomId() != null) {
                if (tidyUpRobots.get(i).getRoomId().equals(command.getGridId()) &&
                        tidyUpRobots.get(i).getCoordinate().getX() == 0 && tidyUpRobots.get(i).getCoordinate().getY() == 0) {
                    return false;
                }
            }
        }
        tidyUpRobotRepository.findById(tidyUpRobotID).get().setRoomID(command.getGridId());
        tidyUpRobotRepository.findById(tidyUpRobotID).get().setCoordinate(new Coordinate(0,0));
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

        List<TidyUpRobot> tidyUpRobots = (List<TidyUpRobot>) tidyUpRobotRepository.findAll();

        for (int i = 0; i < tidyUpRobots.size(); i++) {
            if (tidyUpRobots.get(i).getRoomId() != null) {
                if (tidyUpRobotID != tidyUpRobots.get(i).getTidyUpRobotID() &&
                        coordinate.equals(tidyUpRobots.get(i).getCoordinate()) && tidyUpRobots.get(i).getRoomId().equals(room.getRoomID())) {
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


