package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

enum Direction
{
    NORTH, SOUTH, WEST, EAST,
}

public class Field {
    private final UUID fieldId;
    private Integer height;
    private Integer width;
    private ArrayList<Barrier> barriers = new ArrayList<>();;
    private ArrayList<Connection> connections = new ArrayList<>();

    public Field(UUID fieldID, Integer height, Integer width) {
        this.fieldId = fieldID;
        this.height = height;
        this.width = width;
    }

    public MiningMachine transportMiningMachine(MiningMachine miningMachine, UUID destinationFieldId, ArrayList<MiningMachine> otherMiningMachines){
        for (Connection connection : connections){
            if (connection.getDestinationFieldId().equals(destinationFieldId)){
                if (miningMachine.getXPos() == connection.getSourceCoordinateX() && miningMachine.getYPos() == connection.getSourceCoordinateY()){
                    //er steht auf tp feld
                    for (MiningMachine otherMiningMachine : otherMiningMachines){
                       if (destinationFieldId.equals(otherMiningMachine.getFieldId())){
                           if (otherMiningMachine.getXPos() != null) {
                               if ((connection.getDestinationCoordinateX() == otherMiningMachine.getXPos())
                                       && (connection.getDestinationCoordinateY() == otherMiningMachine.getYPos())) {
                                   return null;
                               }
                           }
                       }
                    }
                    miningMachine.setFieldId(destinationFieldId);
                    miningMachine.setXPos(connection.getDestinationCoordinateX());
                    System.out.println(connection.getDestinationCoordinateX());
                    miningMachine.setYPos(connection.getDestinationCoordinateY());
                    System.out.println(connection.getDestinationCoordinateY());
                    return miningMachine;
                }
            }
        }
        return null;
    }

    public MiningMachine moveMiningMachine (MiningMachine miningMachine, Direction direction, int steps, ArrayList<MiningMachine> otherMiningMachines){
        System.out.println("start="+miningMachine.getXPos()+":"+miningMachine.getYPos()+"|"+direction+"-->"+steps);
        for (int i = 0; i < steps; i++) {

            if (direction == Direction.NORTH) {
                if (checkNorth(miningMachine.getXPos(), miningMachine.getYPos(), otherMiningMachines)) {
                    miningMachine.moveNorth();
                } else return miningMachine;
            }

            if (direction == Direction.EAST) {
                if (checkEast(miningMachine.getXPos(), miningMachine.getYPos(), otherMiningMachines)) {
                    miningMachine.moveEast();
                } else return miningMachine;
            }

            if (direction == Direction.SOUTH) {
                if (checkSouth(miningMachine.getXPos(), miningMachine.getYPos(), otherMiningMachines)) {
                    miningMachine.moveSouth();
                } else return miningMachine;
            }

            if (direction == Direction.WEST) {
                if (checkWest(miningMachine.getXPos(), miningMachine.getYPos(), otherMiningMachines)) {
                    miningMachine.moveWest();
                } else return miningMachine;
            }
            System.out.println(miningMachine.getXPos()+":"+miningMachine.getYPos());
        }
        return miningMachine;
    }

    private boolean checkNorth(int xPos, int yPos, ArrayList<MiningMachine> miningMachines){
        if (yPos == height){
            return false;
        }
        for (Barrier barrier : barriers){
            if (barrier.getType() == Barrier.BarrierType.HORIZONTAL){
                if (yPos + 1 == barrier.getPosition()) {
                    if (barrier.getStart() <= xPos && barrier.getEnd() >= xPos + 1){
                        return false;
                    }
                }
            }
        }
        //------------
        for(MiningMachine miningMachine : miningMachines){
            if (yPos + 1 == miningMachine.getYPos() && xPos == miningMachine.getXPos()){
                return false;
            }
        }
        //------------
        return true;
    }

    private boolean checkSouth(int xPos, int yPos, ArrayList<MiningMachine> miningMachines){
        if (yPos == 0){
            return false;
        }
        for (Barrier barrier : barriers){
            if (barrier.getType() == Barrier.BarrierType.HORIZONTAL){
                if (yPos == barrier.getPosition()) {
                    if (barrier.getStart() <= xPos && barrier.getEnd() >= xPos + 1){
                        return false;
                    }
                }
            }
        }
        //------------
        for(MiningMachine miningMachine : miningMachines){
            if (yPos - 1 == miningMachine.getYPos() && xPos == miningMachine.getXPos()){
                return false;
            }
        }
        //------------
        return true;
    }

    private boolean checkEast(int xPos, int yPos, ArrayList<MiningMachine> miningMachines){
        if (xPos == width){
            return false;
        }
        for (Barrier barrier : barriers){
            if (barrier.getType() == Barrier.BarrierType.VERTICAL){
                if (xPos + 1 == barrier.getPosition()) {
                    if (barrier.getStart() <= yPos && barrier.getEnd() >= yPos + 1){
                        return false;
                    }
                }
            }
        }
        //------------
        for(MiningMachine miningMachine : miningMachines){
            if (xPos + 1 == miningMachine.getXPos() && yPos == miningMachine.getYPos()){
                return false;
            }
        }
        //------------
        return true;
    }

    private boolean checkWest(int xPos, int yPos, ArrayList<MiningMachine> miningMachines){
        if (xPos == 0){
            return false;
        }
        for (Barrier barrier : barriers){
            if (barrier.getType() == Barrier.BarrierType.VERTICAL){
                if (xPos == barrier.getPosition()) {
                    if (barrier.getStart() <= yPos && barrier.getEnd() >= yPos + 1){
                        return false;
                    }
                }
            }
        }
        //------------
        for(MiningMachine miningMachine : miningMachines){
            if (xPos - 1 == miningMachine.getXPos() && yPos == miningMachine.getYPos()){
                return false;
            }
        }
        //------------
        return true;
    }

    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate){
        UUID connectionId = UUID.randomUUID();
        connections.add(new Connection(connectionId, sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate));
        return connectionId;
    }

    public void addBarrier(String barrierString) {
        Integer[] barrierStart = new Integer[2];
        Integer[] barrierEnd = new Integer[2];

        String cleanedBarrierString =
                barrierString.replaceAll("\\(","").replaceAll("\\)","");

        String[] coordsString = cleanedBarrierString.split("-");

        String[] barrierStartString = coordsString[0].split(",");
        barrierStart[0] = Integer.parseInt(barrierStartString[0]);
        barrierStart[1] = Integer.parseInt(barrierStartString[1]);

        String[] barrierEndString = coordsString[1].split(",");
        barrierEnd[0] = Integer.parseInt(barrierEndString[0]);
        barrierEnd[1] = Integer.parseInt(barrierEndString[1]);

        //barrier is vertical
        if (barrierStart[0].equals(barrierEnd[0])){
            Barrier barrier = new Barrier(Barrier.BarrierType.VERTICAL, barrierStart[0], barrierStart[1], barrierEnd[1]);
            barriers.add(barrier);
        }

        //barrier is horizontal
        if (barrierStart[1].equals(barrierEnd[1])){
            Barrier barrier = new Barrier(Barrier.BarrierType.HORIZONTAL, barrierStart[1], barrierStart[0], barrierEnd[0]);
            barriers.add(barrier);
        }
    }

    public UUID getFieldId() {
        return fieldId;
    }
}
