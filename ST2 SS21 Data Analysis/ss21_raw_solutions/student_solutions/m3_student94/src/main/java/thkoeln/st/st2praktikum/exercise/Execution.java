package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Embedded;
import java.util.UUID;


@Component
public class Execution {
    @Autowired
    CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    ConnectionRepository connectionRepository;


    public Boolean InitialCommand( Order deviceMovement, UUID cleaningDeviceId) {
        CleaningDevice cleaningDevice= cleaningDeviceRepository.findById(cleaningDeviceId).get();
          if (!isInitialPositionFree1(deviceMovement.getSpaceId())){
              cleaningDevice.setSpaceId(deviceMovement.getSpaceId());
              cleaningDevice.setCoordinate(new Coordinate(0,0));
              cleaningDeviceRepository.save(cleaningDevice);
              return true;
          }
        return false;
    }

    public boolean isInitialPositionFree1(UUID spaceId1){
        for (CleaningDevice cleaningDevice1: cleaningDeviceRepository.findBySpaceId(spaceId1)){
            if (cleaningDevice1.getCoordinate().getX()==0 && cleaningDevice1.getCoordinate().getY()==0){
                System.out.println(cleaningDevice1.getCoordinate()+ cleaningDevice1.getName() );
                return true;}
        }
        return false;
    }


    public Boolean TransportCommand(Order deviceCommand, UUID cleaningDeviceId) {
        CleaningDevice cleaningDevice=cleaningDeviceRepository.findById(cleaningDeviceId).get();
        if (cleaningDevice.getSpaceId()==null){
            return false;}
        else{
            UUID spaceId1=deviceCommand.getSpaceId();
            for (Connection connection: connectionRepository.findAll()){
                if (cleaningDevice.getSpaceId().equals(connection.getSourceSpaceId())){
                    int coordinateSourceX = connection.getSourcePosition().getX();
                    int coordinateSourceY = connection.getSourcePosition().getY();
                    int coordinateDestinationX = connection.getDestinationPosition().getX();
                    int coordinateDestinationY = connection.getDestinationPosition().getY();

                   if (cleaningDevice.getCoordinate().getX()==coordinateSourceX && cleaningDevice.getCoordinate().getY()==coordinateSourceY && deviceCommand.getSpaceId().equals(connection.getDestinationSpaceID())){
                       System.out.println("hahahahahahahahahahahahah");

                        cleaningDevice.setSpaceId(spaceId1);
                        cleaningDevice.setCoordinate(new Coordinate(coordinateDestinationX,coordinateDestinationY));

                        cleaningDeviceRepository.save(cleaningDevice);
                        return true;
                    }
                }
            }
        return false;
    }
}


    public void calculateBlockPositionNorth(Order order, UUID cleaningDeviceId) {
        CleaningDevice cleaningDevice=cleaningDeviceRepository.findById(cleaningDeviceId).get();
        Obstacle newObstacle = new Obstacle();
        int value = order.getNumberOfSteps();

        for (Obstacle obstacle : spaceRepository.findById(cleaningDevice.getSpaceId()).get().getObstacleList()) {
            if (obstacle.getStart().getY().equals(obstacle.getEnd().getY())){
                 newObstacle= obstacle;}
        }
        if (newObstacle.getStart() == null) {
            cleaningDevice.getCoordinate().setY( cleaningDevice.getCoordinate().getY() + value ) ;
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );
        }
        else if (cleaningDevice.getCoordinate().getY()< newObstacle.getStart().getY() && isCleaningDeviceInInterval(cleaningDevice.getCoordinate().getX(), newObstacle.getStart().getX(), newObstacle.getEnd().getX())) {
            if ((cleaningDevice.getCoordinate().getY() + value) >= newObstacle.getStart().getY()) {
                cleaningDevice.getCoordinate().setY( newObstacle.getStart().getY() - 1 );
            }
            else {
                cleaningDevice.getCoordinate().setY( cleaningDevice.getCoordinate().getY() + value );
            }
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );

        } else {
            if ((cleaningDevice.getCoordinate().getY() + value) >= getSpaceMaxValue(cleaningDevice.getSpaceId())) {
                cleaningDevice.getCoordinate().setY( getSpaceMaxValue(cleaningDevice.getSpaceId() ) - 1 );
            } else {
                cleaningDevice.getCoordinate().setY( cleaningDevice.getCoordinate().getY() + value );
            }
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );
        }
    }

    public void calculateBlockPositionSouth( Order order, UUID cleaningDeviceId){
        CleaningDevice cleaningDevice=cleaningDeviceRepository.findById(cleaningDeviceId).get();
        Obstacle newObstacle = new Obstacle();
        int value = order.getNumberOfSteps();

        for (Obstacle obstacle : spaceRepository.findById(cleaningDevice.getSpaceId()).get().getObstacleList()) {
            if (obstacle.getStart().getY().equals(obstacle.getEnd().getY())){
                newObstacle= obstacle;}
        }

        if (newObstacle.getStart() == null) {
            cleaningDevice.getCoordinate().setY( cleaningDevice.getCoordinate().getY() - value ) ;
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );
        }
        else if (cleaningDevice.getCoordinate().getY() > newObstacle.getStart().getY() && isCleaningDeviceInInterval(cleaningDevice.getCoordinate().getX(), newObstacle.getStart().getX(), newObstacle.getEnd().getX())) {
            cleaningDevice.getCoordinate().setY( Math.max((cleaningDevice.getCoordinate().getY() - value), newObstacle.getStart().getY()) );
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );
        } else {
            cleaningDevice.getCoordinate().setY( Math.max((cleaningDevice.getCoordinate().getY()- value), 0) );
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );
        }

    }
    public void calculateBlockPositionEast(Order order, UUID cleaningDeviceId) {
        CleaningDevice cleaningDevice=cleaningDeviceRepository.findById(cleaningDeviceId).get();
        Obstacle newObstacle = new Obstacle();
        int value = order.getNumberOfSteps();

        for (Obstacle obstacle : spaceRepository.findById(cleaningDevice.getSpaceId()).get().getObstacleList()) {
            if (obstacle.getStart().getX().equals(obstacle.getEnd().getX())){
                newObstacle= obstacle;}
        }
        if (newObstacle.getStart() == null) {
            cleaningDevice.getCoordinate().setX(cleaningDevice.getCoordinate().getX() + value);
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );
        }
         else if (cleaningDevice.getCoordinate().getX() < newObstacle.getStart().getX()
                && isCleaningDeviceInInterval(cleaningDevice.getCoordinate().getY(), newObstacle.getStart().getY(), newObstacle.getEnd().getY())) {

            if ((cleaningDevice.getCoordinate().getX() + value) < newObstacle.getStart().getX()) {
                cleaningDevice.getCoordinate().setX(cleaningDevice.getCoordinate().getX() + value);
            }
            else {
                cleaningDevice.getCoordinate().setX( newObstacle.getStart().getX() - 1 );
            }
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );
        } else {
            if ((cleaningDevice.getCoordinate().getX() + value) > getSpaceMaxValue(cleaningDevice.getSpaceId())) {
                cleaningDevice.getCoordinate().setX( getSpaceMaxValue( cleaningDevice.getSpaceId() ) - 1 );
            }
            else {
                cleaningDevice.getCoordinate().setX( cleaningDevice.getCoordinate().getX()+ value );
            }
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate());
        }
    }

    public void calculateBlockPositionWest( Order order, UUID cleaningDeviceId) {
        CleaningDevice cleaningDevice=cleaningDeviceRepository.findById(cleaningDeviceId).get();
        Obstacle newObstacle = new Obstacle();
        int value = order.getNumberOfSteps();

        for (Obstacle obstacle : spaceRepository.findById(cleaningDevice.getSpaceId()).get().getObstacleList()) {
            if (obstacle.getStart().getX().equals(obstacle.getEnd().getX()))
                newObstacle= obstacle;
        }
        if (newObstacle.getStart() == null) {
            cleaningDevice.getCoordinate().setX  ( Math.max( cleaningDevice.getCoordinate().getX()- value, 0));
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );
        }
        else if (cleaningDevice.getCoordinate().getX() >= newObstacle.getStart().getX() && isCleaningDeviceInInterval(cleaningDevice.getCoordinate().getY(), newObstacle.getStart().getY(),newObstacle.getEnd().getY())) {
            cleaningDevice.getCoordinate().setX  (  Math.max((cleaningDevice.getCoordinate().getX()- value), newObstacle.getStart().getX()));
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );
        } else {
            cleaningDevice.getCoordinate().setX  ( Math.max(cleaningDevice.getCoordinate().getX() - value, 0));
            cleaningDeviceRepository.save(cleaningDevice);
            System.out.println(cleaningDevice.getCoordinate()+ cleaningDevice.getName() );
        }
    }



    public Boolean isCleaningDeviceInInterval(int cleaningDevicePositionToBeChanged, int obstacleStart, int obstacleEnd) {
        if (obstacleStart <= cleaningDevicePositionToBeChanged && cleaningDevicePositionToBeChanged < obstacleEnd)
            return true;
        return obstacleEnd <= cleaningDevicePositionToBeChanged && cleaningDevicePositionToBeChanged < obstacleStart;
    }

    public int getSpaceMaxValue(UUID spaceId) {
        return spaceRepository.findById(spaceId).get().getHeight();
    }




















}
