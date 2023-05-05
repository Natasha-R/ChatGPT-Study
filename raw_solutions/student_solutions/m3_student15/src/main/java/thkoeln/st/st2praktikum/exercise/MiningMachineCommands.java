package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
public class MiningMachineCommands implements Moveable, Findable {

    private List<Field> fields = new ArrayList<Field>();
    private List<Connection> connections = new ArrayList<Connection>();
    private List<MiningMachine> miningMachines = new ArrayList<MiningMachine>();
    private List<TransportCategory> transportCategories = new ArrayList<TransportCategory>();

    public void walk(UUID miningMachineId,int steps, int moveX, int moveY, MiningMachineRepository m){
        Boolean wall;
        Boolean bounds;
        Boolean tileFree;
        Field act = findFieldByMiningMachineId(miningMachineId);
        int schritte = steps;
        Point pos = findMiningMachine(miningMachineId).getPoint();

        for(int i = 0; i < schritte; i++){
            tileFree = false;

            bounds = checkBounds(act, pos, moveX, moveY);
            wall = checkWalls(act, pos, moveX, moveY);
            if(findMiningMachine(miningMachineId).getPoint().getX()+moveX >= 0 && findMiningMachine(miningMachineId).getPoint().getY()+moveY >= 0){
                tileFree = isTileFree(findMiningMachine(miningMachineId).getFieldId(), new Point(findMiningMachine(miningMachineId).getPoint().getX()+moveX, findMiningMachine(miningMachineId).getPoint().getY()+moveY));
            }

            if(!wall && tileFree && !bounds){
                findMiningMachine(miningMachineId).getPoint().setX(pos.getX()+moveX);
                findMiningMachine(miningMachineId).getPoint().setY(pos.getY()+moveY);
                m.save(findMiningMachine(miningMachineId));
            }
        }
    }

    public Boolean spawnMiningMachine(UUID miningMachineId, UUID fieldId, MiningMachineRepository m){
        boolean fieldExists = isFieldExisting(fieldId);
        boolean tileFree = isTileFree(fieldId , new Point(0,0));
        if(fieldExists && tileFree){
            findMiningMachine(miningMachineId).setPoint(new Point(0,0));
            findMiningMachine(miningMachineId).setFieldId(fieldId);
            m.save(findMiningMachine(miningMachineId));
            return true;
        }else{
            return false;
        }
    }

    public Boolean transportMiningMachine(UUID miningMachineId, UUID fieldId, MiningMachineRepository m){
        for(int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getDestinationField().getId().equals(fieldId) && connections.get(i).getSourceField().getId().equals(findMiningMachine(miningMachineId).getFieldId()) && connections.get(i).getSourcePoint().getX() == findMiningMachine(miningMachineId).getPoint().getX() && connections.get(i).getSourcePoint().getY() == findMiningMachine(miningMachineId).getPoint().getY()) {
                findMiningMachine(miningMachineId).setPoint(connections.get(i).getConnection().getDestinationPoint());
                findMiningMachine(miningMachineId).setFieldId(connections.get(i).getConnection().getDestinationField().getId());
                m.save(findMiningMachine(miningMachineId));
                return true;
            }
        }
        return false;
    }

    private Boolean checkBounds(Field act, Point pos, int moveX, int moveY){
        if(pos.getX()+moveX >= 0 && pos.getY()+moveY >= 0 && pos.getX()+moveX < act.getWidth() && pos.getY()+moveY < act.getHeight()){
            return false;
        }else{
            return true;
        }
    }

    private Boolean checkWalls(Field act, Point pos, int moveX, int moveY){
        if(act.getWalls().size() > 0){
            for (int j = 0; j < act.getWalls().size(); j++) {

                if (act.getWalls().get(j).getStart().getX() == act.getWalls().get(j).getEnd().getX() && moveX != 0) {
                    if (act.getWalls().get(j).getStart().getY() <= pos.getY() && pos.getY() < act.getWalls().get(j).getEnd().getY()) {
                        double factor = moveX;
                        if (pos.getX()+(0.5+factor/2) == act.getWalls().get(j).getStart().getX()) {
                            return true;
                        }
                    }
                }

                if (act.getWalls().get(j).getStart().getY() == act.getWalls().get(j).getEnd().getY() && moveY != 0) {
                    if (act.getWalls().get(j).getStart().getX() <= pos.getX() && pos.getX() < act.getWalls().get(j).getEnd().getX()) {
                        double factor = moveY;
                        if(pos.getY()+(0.5+(factor/2)) == act.getWalls().get(j).getStart().getY()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private Boolean isFieldExisting(UUID fieldId){
        for(int i = 0; i < fields.size(); i++){

            if(fieldId.equals(fields.get(i).getId())){
                return true;
            }
        }
        return false;
    }

    private Boolean isTileFree(UUID fieldId, Point tile){
        for(int i = 0; i < miningMachines.size(); i++){
            if(miningMachines.get(i).getPoint() != null) {
                if (fieldId.equals(miningMachines.get(i).getFieldId()) && miningMachines.get(i).getPoint().getX() == tile.getX() && miningMachines.get(i).getPoint().getY() == tile.getY()) {
                    return false;
                }
            }
        }
        return true;
    }

    private Field findFieldByMiningMachineId(UUID miningMachineId){
        for(int i = 0; i < fields.size(); i++) {
            if (findMiningMachine(miningMachineId).getFieldId().equals(fields.get(i).getId())) {
                return fields.get(i).getField();
            }
        }
        throw new NullPointerException();
    }

    public TransportCategory findTransportCategory(UUID categoryId){
        for(int i = 0; i<transportCategories.size(); i++){
            if(transportCategories.get(i).getId().equals(categoryId)){
                return transportCategories.get(i);
            }
        }
        throw new NullPointerException();
    }

    public Field findField(UUID fieldId){
        for(int i = 0; i<fields.size(); i++){
            if(fields.get(i).getId().equals(fieldId)){
                return fields.get(i);
            }
        }
        throw new NullPointerException();
    }
    public MiningMachine findMiningMachine(UUID miningMachineId){
        for(int i = 0; i<miningMachines.size(); i++){
            if(miningMachines.get(i).getId() == miningMachineId){
                return miningMachines.get(i).getMiningMachine();
            }
        }
        throw new NullPointerException();
    }
}

