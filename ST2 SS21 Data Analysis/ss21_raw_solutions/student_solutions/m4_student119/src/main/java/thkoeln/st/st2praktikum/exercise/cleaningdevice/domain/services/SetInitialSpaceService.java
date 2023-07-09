package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.services;

import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.device.IWalkable;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.space.ISpaceService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

public class SetInitialSpaceService {
//    boolean setSpace(UUID spaceId, SpaceRepository spaceRepository, UUID deviceId, CleaningDeviceRepository deviceRepository) {
//        final Optional<SpaceEntity> spaceEntityOptional = spaceRepository.getById(spaceId);
//        final Optional<CleaningDeviceEntity> deviceEntityOptional = deviceRepository.findById(deviceId);
//
//        if(spaceEntityOptional.isEmpty() || deviceEntityOptional.isEmpty()) {
//            throw new IllegalArgumentException("device or space not found!");
//        }
//
//        final CleaningDeviceEntity deviceEntity = deviceEntityOptional.get();
//        final SpaceEntity spaceEntity = spaceEntityOptional.get();
//
//        if(deviceEntity.getSpace() != null)
//            return false;
//
//        deviceEntity.setSpace(spaceEntity);
//        deviceEntity.setPoint(new Point(0, 0));
//        deviceRepository.save(deviceEntity);
//
//        spaceEntity.getCleaningDevices().add(deviceEntity);
//        spaceRepository.save(spaceEntity);
//
//        return true;
//    }

    public boolean setSpace(ISpaceService space, IWalkable walkable) {
        final boolean isFree = space.isFree(new Point(0, 0));
        if (walkable.getSpace() != null || !isFree)
            return false;

        space.addDevice(walkable.getUuid());
        walkable.setSpace(space.getUuid());
        walkable.jump(new Point(0, 0));
        return true;
    }
}
