package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MachineWalking implements walkable {
    private final MiningMachineService mms;
    private UUID machine;
    private UUID currentFieldID = UUID.randomUUID();
    private int currentFieldHeight = 0;
    private int currentFieldWidth = 0;
    private boolean barriersFilled = false;
    boolean commandSuccesfull = true;
    boolean checkOtherMachines = true;

    public MachineWalking(MiningMachineService machineService) {
        this.mms = machineService;
    }

    public void setMachineID(UUID machine) {
        this.machine = machine;
    }

    ArrayList<MiningMachineCurrentLocation> miningMachineCurrentLocations = new ArrayList<>();
    ArrayList<BarrierList> verticalBarrier = new ArrayList<>();
    ArrayList<BarrierList> horizontalBarrier = new ArrayList<>();

    @Override
    public void walkTo(Command command) {

        if (!barriersFilled)
            addToList();
        int machinePosition = 0;

        switch (command.getCommandType()) {

            case ENTER: {
                spawn(command);
                break;
            }

            case TRANSPORT: {
                transport(machinePosition);
                break;
            }

            case EAST: {
                east(command, machinePosition);
                break;
            }

            case WEST: {
                west(command, machinePosition);
                break;
            }

            case NORTH: {
                north(command, machinePosition);
                break;
            }

            case SOUTH: {
                south(command, machinePosition);


                break;
            }
            default:
                throw new InvalidParameterException("Keine gültige Eingabe");
        }
    }

    public void addToList() {
        for (int i = 0; i < mms.barriers.size(); i++) {
            if (mms.barriers.get(i).getSetXorY().equals("x")) {
                BarrierList newXBarrier = new BarrierList(mms.barriers.get(i).getFieldID(), mms.barriers.get(i).getBarrier());
                verticalBarrier.add(newXBarrier);
            }
        }
        for (int i = 0; i < mms.barriers.size(); i++) {
            if (mms.barriers.get(i).getSetXorY().equals("y")) {
                BarrierList newXBarrier = new BarrierList(mms.barriers.get(i).getFieldID(), mms.barriers.get(i).getBarrier());
                horizontalBarrier.add(newXBarrier);
            }
            barriersFilled = true;
        }
    }


    //Das ggf. in eigene Klasse für schöneren Code
    private int yAxisFirstBarrier(int machinePosition, String direction) {

        var yFirstBarrier = new AtomicInteger(-1);
        int currentX = miningMachineCurrentLocations.get(machinePosition).getPoint().getX();

        for (BarrierList barrier : verticalBarrier) {
            int startX = barrier.getBarrier().getStart().getX();
            int endX = barrier.getBarrier().getEnd().getX();
            int yPos = barrier.getBarrier().getStart().getY();

            if (barrier.getFieldID().equals(miningMachineCurrentLocations.get(machinePosition).getCurrentFieldID()) && yFirstBarrier.get() == -1 && currentX >= startX && currentX < endX) {
                yFirstBarrier.set(yPos);
            }

            if (direction.equals("no") && yFirstBarrier.get() != -1 && barrier.getFieldID().equals(miningMachineCurrentLocations.get(machinePosition).getCurrentFieldID())) {
                if (currentX >= startX && currentX < endX && yPos < yFirstBarrier.get())
                    yFirstBarrier.set(yPos);
            }

            if (direction.equals("so") && yFirstBarrier.get() != -1 && barrier.getFieldID().equals(miningMachineCurrentLocations.get(machinePosition).getCurrentFieldID())) {
                if (currentX >= startX && currentX < endX && yPos > yFirstBarrier.get())
                    yFirstBarrier.set(yPos);
            }
        }
        return yFirstBarrier.get();
    }

    private int xAxisFirstBarrier(int machinePosition, String direction) {
        var xFirstBarrier = new AtomicInteger(-1);
        int currentY = miningMachineCurrentLocations.get(machinePosition).getPoint().getY();

        for (BarrierList barrier : horizontalBarrier) {
            int startY = barrier.getBarrier().getStart().getY();
            int endY = barrier.getBarrier().getEnd().getY();
            int xPos = barrier.getBarrier().getStart().getX();

            if (barrier.getFieldID().equals(miningMachineCurrentLocations.get(machinePosition).getCurrentFieldID()) && xFirstBarrier.get() == -1 && currentY >= startY && currentY <= endY) {
                xFirstBarrier.set(xPos);
            }

            if (direction.equals("ea") && xFirstBarrier.get() != -1 && barrier.getFieldID().equals(miningMachineCurrentLocations.get(machinePosition).getCurrentFieldID())) {
                if (currentY >= startY && currentY <= endY && xPos < xFirstBarrier.get())
                    xFirstBarrier.set(xPos);
            }

            if (direction.equals("we") && xFirstBarrier.get() != -1 && barrier.getFieldID().equals(miningMachineCurrentLocations.get(machinePosition).getCurrentFieldID())) {
                if (currentY >= startY && currentY <= endY && xPos > xFirstBarrier.get())
                    xFirstBarrier.set(xPos);
            }
        }
        return xFirstBarrier.get();
    }

    private int getMachinePositionInList(int machinePosition) {
        for (int i = 0; i < miningMachineCurrentLocations.size(); i++) {
            if (miningMachineCurrentLocations.get(i).getMachineID() == machine) {
                machinePosition = i;
            }
        }
        return machinePosition;
    }

    private void spawn(Command command) {
        currentFieldID = command.getGridId();
        Point point = new Point(0, 0);
        MiningMachineCurrentLocation newMachineLocation = new MiningMachineCurrentLocation(machine, currentFieldID, point);
        for (MiningMachineCurrentLocation miningMachineCurrentLocation : miningMachineCurrentLocations) {
            if (miningMachineCurrentLocation.getMachineID() != machine && miningMachineCurrentLocation.getCurrentFieldID().equals(currentFieldID) && miningMachineCurrentLocation.getPoint().getX() == 0 && miningMachineCurrentLocation.getPoint().getY() == 0) {
                commandSuccesfull = false;
                break;
            }
        }
        if (commandSuccesfull) {
            miningMachineCurrentLocations.add(newMachineLocation);

        }
        for (int i = 0; i < mms.decks.size(); i++) {
            if (mms.decks.get(i).getFieldID().equals(currentFieldID)) {
                currentFieldHeight = mms.decks.get(i).getFieldHeight();
                currentFieldWidth = mms.decks.get(i).getFieldWidth();
            }
        }
    }

    private void transport(int machinePosition) {
        commandSuccesfull = false;
        checkOtherMachines = true;
        machinePosition = getMachinePositionInList(machinePosition);
        for (Connection c : mms.connections) {
            if (miningMachineCurrentLocations.get(machinePosition).getCurrentFieldID().equals(c.getStartID())) {
                if (mms.getPoint(miningMachineCurrentLocations.get(machinePosition).getMachineID()).equals(c.getStartLocation())) {
                    commandSuccesfull = true;
                    for(MiningMachineCurrentLocation m: miningMachineCurrentLocations){ //check for others
                        if (!m.getMachineID().equals(machine) && Objects.equals(m.getPoint(), c.getEndLocation())) {
                            checkOtherMachines = false;
                            break;
                        }
                            break;
                    }
                    if(checkOtherMachines){
                    miningMachineCurrentLocations.get(machinePosition).setCurrentFieldID(c.getEndID());
                    miningMachineCurrentLocations.get(machinePosition).setCoordinate(c.getEndLocation());
                    currentFieldID = c.getEndID();
                    break;
                    }
                }
            }

        }
        if(!commandSuccesfull)
            System.out.println("Transport nicht möglich. Zielfeld besetzt oder falsche Position.");
    }

    private void north(Command command, int machinePosition) {

        machinePosition = getMachinePositionInList(machinePosition);
        int barrierYPos = yAxisFirstBarrier(machinePosition, "no");

        for (int i = 0; i < command.getNumberOfSteps(); i++) {

            if (miningMachineCurrentLocations.get(machinePosition).getPoint().getY() == barrierYPos - 1)
                break;
            if (miningMachineCurrentLocations.get(machinePosition).getPoint().getY() == currentFieldHeight - 1)
                break;
            Point point = new Point(miningMachineCurrentLocations.get(machinePosition).getPoint().getX(), miningMachineCurrentLocations.get(machinePosition).getPoint().getY() + 1);
            miningMachineCurrentLocations.get(machinePosition).setCoordinate(point);
        }
    }

    private void south(Command command, int machinePosition) {
        machinePosition = getMachinePositionInList(machinePosition);
        int barrierYPos = yAxisFirstBarrier(machinePosition, "so");

        for (int i = 0; i < command.getNumberOfSteps(); i++) {
            if (miningMachineCurrentLocations.get(machinePosition).getPoint().getY() == barrierYPos)
                break;
            if (miningMachineCurrentLocations.get(machinePosition).getPoint().getY() == 0)
                break;
            Point point = new Point(miningMachineCurrentLocations.get(machinePosition).getPoint().getX(), miningMachineCurrentLocations.get(machinePosition).getPoint().getY() - 1);
            miningMachineCurrentLocations.get(machinePosition).setCoordinate(point);
        }
    }

    private void east(Command command, int machinePosition) {
        machinePosition = getMachinePositionInList(machinePosition);
        int barrierXPos = xAxisFirstBarrier(machinePosition, "ea");

        for (int i = 0; i < command.getNumberOfSteps(); i++) {
            if (miningMachineCurrentLocations.get(machinePosition).getPoint().getX() == barrierXPos)
                break;
            if (miningMachineCurrentLocations.get(machinePosition).getPoint().getX() == currentFieldWidth - 1)
                break;
            Point point = new Point(miningMachineCurrentLocations.get(machinePosition).getPoint().getX() + 1, miningMachineCurrentLocations.get(machinePosition).getPoint().getY());
            miningMachineCurrentLocations.get(machinePosition).setCoordinate(point);

        }
    }

    private void west(Command command, int machinePosition) {
        machinePosition = getMachinePositionInList(machinePosition);
        int barrierXPos = xAxisFirstBarrier(machinePosition, "we");

        for (int i = 0; i < command.getNumberOfSteps(); i++) {
            if (miningMachineCurrentLocations.get(machinePosition).getPoint().getX() == barrierXPos - 1)
                break;
            if (miningMachineCurrentLocations.get(machinePosition).getPoint().getX() == 0)
                break;
            Point point = new Point(miningMachineCurrentLocations.get(machinePosition).getPoint().getX() - 1, miningMachineCurrentLocations.get(machinePosition).getPoint().getY());
            miningMachineCurrentLocations.get(machinePosition).setCoordinate(point);
        }
    }
}
