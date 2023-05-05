package thkoeln.st.st2praktikum.exercise.execution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;

import java.util.UUID;

@Component
public class TransportCommand {
    @Autowired
    CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    TransportCategoryRepository transportCategoryRepository;

    public Boolean transportingCommand(Order deviceCommand, UUID cleaningDeviceId) {
        CleaningDevice cleaningDevice=cleaningDeviceRepository.findById(cleaningDeviceId).get();
        if (cleaningDevice.getSpaceId()==null){
            return false;}
        else{
            UUID spaceId1=deviceCommand.getSpaceId();
            for (TransportCategory transportCategory: transportCategoryRepository.findAll()) {
                for (Connection connection : transportCategory.getConnections()) {
                    if (cleaningDevice.getSpaceId().equals(connection.getSourceSpaceId())) {
                        int coordinateSourceX = connection.getSourcePosition().getX();
                        int coordinateSourceY = connection.getSourcePosition().getY();
                        int coordinateDestinationX = connection.getDestinationPosition().getX();
                        int coordinateDestinationY = connection.getDestinationPosition().getY();

                        if (cleaningDevice.getCoordinate().getX() == coordinateSourceX && cleaningDevice.getCoordinate().getY() == coordinateSourceY && deviceCommand.getSpaceId().equals(connection.getDestinationSpaceID())) {
                            System.out.println("hahahahahahahahahahahahah");

                            cleaningDevice.setSpaceId(spaceId1);
                            cleaningDevice.setCoordinate(new Coordinate(coordinateDestinationX, coordinateDestinationY));

                            cleaningDeviceRepository.save(cleaningDevice);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}
