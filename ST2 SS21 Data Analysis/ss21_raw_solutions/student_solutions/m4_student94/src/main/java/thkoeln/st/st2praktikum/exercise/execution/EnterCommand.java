package thkoeln.st.st2praktikum.exercise.execution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;

import java.util.UUID;

@Component
public class EnterCommand {
    @Autowired
    CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    SpaceRepository spaceRepository;


    public Boolean initialisingCommand(Order deviceMovement, UUID cleaningDeviceId) {
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

}
