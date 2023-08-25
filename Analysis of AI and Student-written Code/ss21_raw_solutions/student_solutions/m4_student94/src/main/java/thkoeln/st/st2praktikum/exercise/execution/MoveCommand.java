package thkoeln.st.st2praktikum.exercise.execution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.space.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;

import java.util.UUID;

@Component
public class MoveCommand {

    @Autowired
    CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    ConnectionRepository connectionRepository;


    public void movingNorth(Order order, UUID cleaningDeviceId) {
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

    public void movingSouth( Order order, UUID cleaningDeviceId){
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
    public void movingEast(Order order, UUID cleaningDeviceId) {
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

    public void movingWest( Order order, UUID cleaningDeviceId) {
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
