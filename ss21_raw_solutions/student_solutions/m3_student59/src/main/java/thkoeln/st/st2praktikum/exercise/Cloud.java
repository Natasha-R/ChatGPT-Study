package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public interface Cloud {
    MiningMachineRepository getMiningmaschines();
    FieldRepository getFields();

    static boolean checkMiningmaschinesInDirection(UUID fieldId, Point point, Cloud world, OrderType orderType, UUID miningmschineId) throws IllegalStateException {

        AtomicReference<Boolean> result = new AtomicReference<>(false);

        world.getMiningmaschines().findAll().forEach(miningmaschineEntry -> {
            if(miningmaschineEntry.getSpawned() && !miningmaschineEntry.getMiningmaschineId().equals(miningmschineId)){
                switch (orderType){
                    case NORTH:
                        result.set(miningmaschineEntry.getFieldId().equals(fieldId) && miningmaschineEntry.getPoint().equals(new Point(point.getX(), (point.getY()) + 1)));
                        break;
                    case EAST:
                        result.set(miningmaschineEntry.getFieldId().equals(fieldId) && miningmaschineEntry.getPoint().equals(new Point((point.getX()+1), point.getY())));
                        break;
                    case SOUTH:
                        result.set(miningmaschineEntry.getFieldId().equals(fieldId) && miningmaschineEntry.getPoint().equals(new Point(point.getX(), (point.getY())-1)));
                        break;
                    case WEST:
                        result.set(miningmaschineEntry.getFieldId().equals(fieldId) && miningmaschineEntry.getPoint().equals(new Point((point.getX()-1), point.getY())));
                        break;
                }
            }
        });
        return result.get();
    }

}
