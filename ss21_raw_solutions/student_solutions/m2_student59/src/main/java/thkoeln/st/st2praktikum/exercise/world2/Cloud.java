package thkoeln.st.st2praktikum.exercise.world2;

import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.world2.field.Field;
import thkoeln.st.st2praktikum.exercise.world2.miningMaschine.Miningmaschine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface Cloud {
    HashMap<UUID, Miningmaschine> getMiningmaschines();
    HashMap<UUID, Field> getFields();

    static boolean checkMiningmaschinesInDirection(UUID fieldId, Point point, Cloud world, OrderType orderType, UUID miningmschineId) throws IllegalStateException {

        for (Map.Entry<UUID, Miningmaschine> miningmaschineEntry : world.getMiningmaschines().entrySet()) {
            if(miningmaschineEntry.getValue().getSpawned() && !miningmaschineEntry.getValue().getMiningmaschineId().equals(miningmschineId)){
                switch (orderType){
                    case NORTH:
                        return miningmaschineEntry.getValue().getFieldId().equals(fieldId) && miningmaschineEntry.getValue().getPoint().equals(new Point(point.getX(), (point.getY())+1));
                    case EAST:
                        return miningmaschineEntry.getValue().getFieldId().equals(fieldId) && miningmaschineEntry.getValue().getPoint().equals(new Point((point.getX()+1), point.getY()));
                    case SOUTH:
                        return miningmaschineEntry.getValue().getFieldId().equals(fieldId) && miningmaschineEntry.getValue().getPoint().equals(new Point(point.getX(), (point.getY())-1));
                    case WEST:
                        return miningmaschineEntry.getValue().getFieldId().equals(fieldId) && miningmaschineEntry.getValue().getPoint().equals(new Point((point.getX()-1), point.getY()));
                }
            }
        }
        return false;
    }

}
